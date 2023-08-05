package store.greeting.config;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private String additionalInfo; // 추가적인 사용자 정보를 가지고 있는 필드

  public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String additionalInfo) {
    this.username = username;
    this.password = password;
    this.authorities = authorities;
    this.additionalInfo = additionalInfo;
  }

  // 사용자의 추가적인 정보를 반환하는 메서드
  public String getAdditionalInfo() {
    return additionalInfo;
  }

  // UserDetails 인터페이스의 메서드 구현
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
