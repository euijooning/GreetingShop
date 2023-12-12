package store.greeting.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.greeting.member.dto.PasswordUpdateDto;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements UserDetailsService, MemberService {

  private final MemberRepository memberRepository;

  private final PasswordEncoder passwordEncoder; // 추가 및 Lazy로 순환참조 문제 해결
  @Autowired
  public MemberServiceImpl(MemberRepository memberRepository, @Lazy PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public Member saveMember(Member member) {
    validateDuplicateMember(member);
    return memberRepository.save(member);
  }

  // 중복 회원 검사
  private void validateDuplicateMember(Member member) {
    Member findMember = memberRepository.findByEmail(member.getEmail());
    if (findMember != null) {
      throw new IllegalStateException("이미 가입된 회원입니다.");
    }
  }

  // 상세정보 로딩
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email);
    if (member == null) {
      throw new UsernameNotFoundException(email);
    }

    return User.builder().username(member.getEmail())
        .password(member.getPassword())
        .roles(member.getRole().toString())
        .build();
  }

  // 비밀번호 변경
  @Override
  public Long updateMemberPassword(PasswordUpdateDto passwordUpdateDto, String email) {
    Member member = memberRepository.findByEmail(email);

    if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), member.getPassword())) {
      return null;
    } else {
      passwordUpdateDto.setNewPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
      member.updatePassword(passwordUpdateDto.getNewPassword());
      memberRepository.save(member);
    }
    return member.getId();
  }
}