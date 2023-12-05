package store.greeting.member.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.greeting.common.BaseEntity;
import store.greeting.enums.UserType;
import store.greeting.member.dto.MemberFormDto;

import javax.persistence.*;

@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "loginType"}))
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


  @Column
  private String picture;

  @Column(nullable = false)
  private String loginType;


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
        .loginType("normal")
        .build();
  }

  @Builder
  public Member(String name, String email, String picture, UserType role, String loginType){
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
    this.loginType = loginType;
  }

  public Member update(String name, String picture){
    this.name = name;
    this.picture = picture;
    return this;
  }

  public String getRoleKey(){
    return this.role.toString();
  }
}