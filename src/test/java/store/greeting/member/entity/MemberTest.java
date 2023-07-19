package store.greeting.member.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.greeting.enums.UserType;
import store.greeting.member.dto.MemberFormDto;

class MemberTest {

  @Test
  @DisplayName("회원 생성 테스트")
  void createMemberTest() {
    // given
    MemberFormDto formDto = new MemberFormDto();
        formDto.setName("newjeans");
        formDto.setTel("010-1234-1234");
        formDto.setEmail("newjeans123@abc.com");
        formDto.setAddress("seoul");
        formDto.setPassword("12345678");

    // when
    Member member = Member.createMember(formDto, new BCryptPasswordEncoder());

    // then
    assertEquals(formDto.getName(), member.getName());
    assertEquals(formDto.getTel(), member.getTel());
    assertEquals(formDto.getEmail(), member.getEmail());
    assertEquals(formDto.getAddress(), member.getAddress());
    assertTrue(new BCryptPasswordEncoder().matches(formDto.getPassword(), member.getPassword()));
    assertEquals(UserType.ADMIN, member.getRole());
  }
}