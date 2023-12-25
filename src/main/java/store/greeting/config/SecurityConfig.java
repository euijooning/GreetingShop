package store.greeting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import store.greeting.member.service.MemberServiceImpl;
import store.greeting.social.CustomOAuth2UserService;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final MemberServiceImpl memberService;
  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()
            .loginPage("/members/login")
            .defaultSuccessUrl("/")
            .usernameParameter("email")
            .failureUrl("/members/login/error")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))

            .logoutSuccessHandler((request, response, authentication) -> {
              // 인증 정보 확인
              if (authentication != null && authentication.isAuthenticated()) {
                String redirectUrl = determineRedirectUrl(authentication);

                // 로그아웃 후에 리다이렉트
                response.sendRedirect(redirectUrl);
              } else {
                // 인증되지 않았거나 인증 정보가 없는 경우
                response.sendRedirect("/");
              }
            })

            //.logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .userInfoEndpoint().userService(customOAuth2UserService);
    ;

    http.authorizeHttpRequests()
        .mvcMatchers("/","/members/**", "/member/**","/product/**","/images/**", "/image/**", "/favicon.ico/**", "/boards/**", "/mail/**", "/mainpage/**").permitAll()
        .mvcMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .csrf()
        .ignoringAntMatchers("/mail/**")
        .ignoringAntMatchers("/boards/**")
        .ignoringAntMatchers("/mainpage/**")
        .ignoringAntMatchers("/members/findId")
        .ignoringAntMatchers("/members/my/**")
    ;

    http.exceptionHandling()
        .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(memberService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  public void configure(WebSecurity web) throws Exception{
    web.ignoring().antMatchers("/css/**", "/js/**","/img/**");
  }

  private String determineRedirectUrl(Authentication authentication) throws IOException {


    if (authentication instanceof UsernamePasswordAuthenticationToken) { // 일반 로그인의 경우
      return "/";

    } else { // 소셜로그인의 경우
      OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
      String[] parsedToken = AuthTokenParser.getParseToken(authToken);

      if (parsedToken[1].equals("google")){
        return "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=https://localhost/members/logout";

      } else if (parsedToken[1].equals("naver")) {
        return "https://nid.naver.com/nidlogin.logout?";

      } else if (parsedToken[1].equals("kakao")) {
        return "https://kauth.kakao.com/oauth/logout/?client_id=eca4b47472b8c54c7b56f803de88c27b&logout_redirect_uri=localhost/members/logout";
      }

    }
    return "/";
  }
}