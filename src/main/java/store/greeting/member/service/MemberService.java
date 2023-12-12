package store.greeting.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import store.greeting.member.dto.PasswordUpdateDto;
import store.greeting.member.entity.Member;

public interface MemberService {

  Member saveMember(Member member);
  UserDetails loadUserByUsername(String email);
  Long updateMemberPassword(PasswordUpdateDto passwordUpdateDto, String email);
}
