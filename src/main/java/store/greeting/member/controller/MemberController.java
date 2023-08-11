package store.greeting.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import store.greeting.member.dto.MemberFormDto;
import store.greeting.member.entity.Member;
import store.greeting.member.service.MemberServiceImpl;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberServiceImpl memberService;
  private final PasswordEncoder passwordEncoder;


  @GetMapping(value = "/new")
  public String memberForm(Model model) {
    model.addAttribute("memberFormDto", new MemberFormDto());
    return "member/memberForm";
  }


  @PostMapping(value = "/new")
  public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      return "member/memberForm";
    }
    try {
      Member member = Member.createMember(memberFormDto, passwordEncoder);
      memberService.saveMember(member);
    } catch (IllegalStateException e) {
      model.addAttribute("errorMessage", e.getMessage());
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
}