package store.greeting.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.greeting.config.AuthTokenParser;
import store.greeting.mail.MailDto;
import store.greeting.mail.MailService;
import store.greeting.member.dto.MemberFormDto;
import store.greeting.member.dto.PasswordUpdateDto;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;
import store.greeting.member.service.MemberServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberServiceImpl memberService;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;
  private final MemberRepository memberRepository;


  @GetMapping(value = "/new")
  public String memberForm(Model model){
    model.addAttribute("memberFormDto",new MemberFormDto());
    return "member/memberForm";
  }


  @PostMapping(value = "/new")
  public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "member/memberForm";
    }
    try {
      Member member = Member.createMember(memberFormDto,passwordEncoder);
      memberService.saveMember(member);
    }
    catch (IllegalStateException e){
      model.addAttribute("errorMessage",e.getMessage());
      return "member/memberForm";
    }

    return "redirect:/";
  }


  @GetMapping(value = "/login")
  public String loginMember() {
    return "member/memberLoginForm";
  }


  @GetMapping(value = "/login/error")
  public String loginError(Model model) {
    model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인하세요");
    return "member/memberLoginForm";
  }


  // 프로필 정보
  @GetMapping("/my")
  public String memberInfo(Principal principal, Model model){
    String[] userInfo = AuthTokenParser.getParseToken(principal);
    Member member = memberRepository.findByEmailAndLoginType(userInfo[0], userInfo[1]);
    model.addAttribute("member", member);

    return "member/my";
  }


  // 회원 아이디(이메일) 가입 여부 판단
  @PostMapping("/findId")
  @ResponseBody
  public String findId(@RequestParam("email") String email) {
    String foundEmail = String.valueOf(memberRepository.findByEmail(email));
    System.out.println("회원 이메일 : " + foundEmail);

    if (email == null) {
      return "해당 이메일로 등록된 회원이 없습니다.";
    } else {
      return foundEmail;
    }
  }


 // 회원 비밀번호 찾기
  @GetMapping(value = "/findMember")
  public String findMember(Model model) {
    return "member/findMemberForm";
  }


  // 비밀번호 찾을 때, 임시 비밀번호 담긴 이메일 보내기
  @Transactional
  @PostMapping("/sendEmail")
  public String sendEmail(@RequestParam("email") String email) {
    MailDto dto = mailService.createMailContentAndChangePassword(email);
    mailService.mailSend(dto);

    return "member/memberLoginForm";
  }


  // 비밀번호 변경
  @GetMapping("/my/password")
  public String updatePasswordForm() {
    return "member/passwordUpdateForm";
  }

  @PostMapping("/my/password")
  public String updatePassword(@Valid PasswordUpdateDto passwordUpdateDto, Model model, Authentication authentication) {
      // new password 비교
      if (!Objects.equals(passwordUpdateDto.getNewPassword(), passwordUpdateDto.getConfirmPassword())) {
          model.addAttribute("dto", passwordUpdateDto);
          model.addAttribute("resultMessage", "비밀번호가 일치하지 않습니다.");
          return "member/passwordUpdateForm";
      }

      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      Long result = memberService.updateMemberPassword(passwordUpdateDto, userDetails.getUsername());

      // 현재 비밀번호가 불일치할 경우
      if (result == null) {
          model.addAttribute("dto", passwordUpdateDto);
          model.addAttribute("resultMessage", "비밀번호가 일치하지 않습니다.");
          return "member/passwordUpdateForm";
      }

      model.addAttribute("resultMessage", "비밀번호가 성공적으로 변경되었습니다.");
      return "redirect:/members/my";
  }

}