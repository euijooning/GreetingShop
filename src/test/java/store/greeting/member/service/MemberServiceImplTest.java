package store.greeting.member.service;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.greeting.enums.UserType;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

  private MemberServiceImpl memberService;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    memberService = new MemberServiceImpl(memberRepository, passwordEncoder);
  }


  String email = "newjeans123@abc.com";
  String password = "password456";
  UserType userRole = UserType.USER;

  @Test
  @DisplayName("회원정보 저장 - 성공한 경우")
  void saveMember_success() {
    // given
    Member member = Member.builder()
        .email(email)
        .password(password)
        .role(userRole)
        .build();

    // when
    when(memberRepository.findByEmail(anyString())).thenReturn(null); // 중복회원 x
    when(memberRepository.save(member)).thenReturn(member); // 저장

    Member savedMember = memberService.saveMember(member);

    // Then
    assertEquals(member.getEmail(), savedMember.getEmail());
    assertEquals(member.getPassword(), savedMember.getPassword());
    assertEquals(member.getRole(), savedMember.getRole());
  }

  @Test
  @DisplayName("회원정보 저장 - 실패 : 중복 회원인 경우")
  void saveMember_fails_duplicateMember() {
    // given
    Member member = Member.builder()
        .email(email)
        .password(password)
        .role(userRole)
        .build();

    // when
    when(memberRepository.findByEmail(anyString())).thenReturn(member);

    // then
    assertThrows(IllegalStateException.class, () -> memberService.saveMember(member));
  }



  @Test
  @DisplayName("회원정보 조회 - 성공한 경우")
  void loadUserByUsername() {
    // Given
    String email = "test@example.com";
    Member member = Member.builder()
        .email(email)
        .password("password")
        .role(UserType.ADMIN)
        .build();

    // when
    when(memberRepository.findByEmail(email)).thenReturn(member);
    UserDetails userDetails = memberService.loadUserByUsername(email);

    // then
    assertNotNull(userDetails);
    assertEquals(email, userDetails.getUsername());
    assertEquals("password", userDetails.getPassword());

  }

  @Test
  @DisplayName("회원정보 조회 - 실패 : 이미 존재하는 회원")
  void loadUserByUsername_fails_alreadyExisting() {
    // given
    Member member = Member.builder()
        .email(email)
        .password(password)
        .role(userRole)
        .build();

    // when
    when(memberRepository.findByEmail(email)).thenReturn(member);
    UserDetails userDetails = memberService.loadUserByUsername(email);

    // then
    assertNotNull(userDetails);
    assertEquals(email, userDetails.getUsername());
    assertEquals(password, userDetails.getPassword());
  }

  @Test
  @DisplayName("회원정보 조회 - 실패 : 정보가 없음")
  void loadUserByUsername_NonExistingMember() {
    // given
    String email = "newjeans456@abc.com";

    // when
    when(memberRepository.findByEmail(email)).thenReturn(null);

    // then
    assertThrows(UsernameNotFoundException.class, () -> memberService.loadUserByUsername(email));
  }
}