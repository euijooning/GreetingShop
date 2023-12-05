package store.greeting.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    final String loginPageUrl = "/members/login"; // 리다이렉트할 로그인 페이지의 URL
    final String message = "로그인이 필요합니다"; // 로그인 필요시 메세지

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}