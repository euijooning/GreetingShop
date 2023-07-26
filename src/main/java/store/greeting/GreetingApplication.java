package store.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.greeting.enums.UserType;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;

@SpringBootApplication
public class GreetingApplication {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public GreetingApplication(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {

      Member userMember = Member.builder()
          .email("user@gmail.com")
          .name("사용자")
          .password(passwordEncoder.encode("12345678"))
          .tel("01012345678")
          .role(UserType.USER)
          .build();

      memberRepository.save(userMember);

      Member adminMember = Member.builder()
          .email("admin@gmail.com")
          .name("관리자")
          .password(passwordEncoder.encode("12345678"))
          .tel("01012345678")
          .role(UserType.ADMIN)
          .build();

      memberRepository.save(adminMember);

    };
  }

  public static void main(String[] args) {
    SpringApplication.run(GreetingApplication.class, args);
  }

}
