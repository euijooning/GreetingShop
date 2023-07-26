package store.greeting.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.greeting.common.BaseEntity;
import store.greeting.enums.UserType;
import store.greeting.member.dto.MemberFormDto;

@Entity
@Table(name = "member")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

  @Id
  @Column(name = "member_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String tel;

  @Column(unique = true)
  private String email;

  private String password;

  private String address;

  @Enumerated(EnumType.STRING)
  private UserType role;


  public static Member createMember(MemberFormDto memberFormDto,
      PasswordEncoder passwordEncoder) {
    String password = passwordEncoder.encode(memberFormDto.getPassword());
    return Member.builder()
        .name(memberFormDto.getName())
        .tel(memberFormDto.getTel())
        .email(memberFormDto.getEmail())
        .address(memberFormDto.getAddress())
        .password(password)
        .role(UserType.from(memberFormDto.getType()))
        .build();
  }
}