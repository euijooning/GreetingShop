package store.greeting.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import store.greeting.board.dto.response.BoardResponseDto;
import store.greeting.social.SessionUser;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * <p>
 * 메인 컨트롤러
 * </p>
 * javadoc 추가 위한 작업입니다.
 */
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

  /**
   * 게시글 정보를 열람한다.
   *
   * @param id 게시글 id
   * @return  게시글 응답
   */
  @ResponseBody
  @GetMapping("/rest")
  public BoardResponseDto confirm(Long id) {
    return BoardResponseDto.of(
            id,
            "test",
            "test-content",
            LocalDateTime.now()
    );
  }
}
