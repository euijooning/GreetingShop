package store.greeting.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.greeting.member.dto.MemberFormDto;
import store.greeting.member.entity.Member;
import store.greeting.member.service.MemberServiceImpl;
import store.greeting.mail.MailService;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberServiceImpl memberService;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;

  String confirm =""; //인증코드를 내가 미리 가지고 있다.
  boolean confirmCheck = false;



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

  // 이메일 인증 관련
  @PostMapping("/{email}/emailConfirm")
  public @ResponseBody ResponseEntity emailConfirm(@PathVariable("email") String email) throws Exception{
    confirm = mailService.sendSimpleMessage(email);
    return new ResponseEntity<String> ("인증 메일을 보냈습니다.", HttpStatus.OK);
  }

  @PostMapping("/{code}/codeCheck")
  public @ResponseBody ResponseEntity codeConfirm(@PathVariable("code") String code) throws Exception{
    if(code.equals(confirm)){
      confirmCheck=true;
      return new ResponseEntity<String> ("인증 성공하였습니다.", HttpStatus.OK);
    }
    return new ResponseEntity<String> ("인증 코드를 올바르게 입력해주세요.", HttpStatus.BAD_REQUEST);
  }
}