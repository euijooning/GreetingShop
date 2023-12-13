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

  @Column
  private String name;

  @Column
  private String tel;

  @Column(unique = true)
  private String email;

  @Column
  private String password;

  @Column
  private String address;

  @Column
  private String addressDetail;

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
            .addressDetail(memberFormDto.getAddressDetail())
            .password(password)
            .role(UserType.from(memberFormDto.getType()))
            .loginType("normal")
            .build();
  }

  @Builder
  public Member(String name, String email, String picture, UserType role, String loginType) {
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
    this.loginType = loginType;
  }

  public Member update(String name, String picture) {
    this.name = name;
    this.picture = picture;
    return this;
  }

  public String getRoleKey() {
    return this.role.toString();
  }

  public void updateTemporalPassword(String newPassword) {
    this.password = newPassword;
  }

  public void updatePassword(String password) {
    this.password = password;
  }


  public void updateProfile(String name, String tel, String address, String addressDetail) {
    this.name = name;
    this.tel = tel;
    this.address = address;
    this.addressDetail = addressDetail;
  }

}