package store.greeting.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.greeting.plus.SessionUser;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final HttpSession httpSession;

  @GetMapping(value = "/")
  public String main(Model model) {
    SessionUser user = (SessionUser) httpSession.getAttribute("user");

    if(user == null){
      model.addAttribute("nickname", "");
    }
    else {
      model.addAttribute("nickname", user.getName());
    }
    return "main";
  }

}
