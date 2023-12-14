package store.greeting.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String > {
  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userId = "";
    if (authentication != null) {
      if (authentication instanceof AnonymousAuthenticationToken) { // 익명 사용자일 경우 (ex. 회원가입 시 member의 created_by는 익명이다)
        // 기존 코드
        userId = authentication.getName();
      } else {
        String[] auditor = AuthTokenParser.getParseToken(authentication);
        userId = auditor[0]+"/"+auditor[1]; // "이메일/type" 형식
      }
    }
    return Optional.of(userId);
  }

}