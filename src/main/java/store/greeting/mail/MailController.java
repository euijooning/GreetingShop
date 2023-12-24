package store.greeting.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;


    // 이메일 인증 연습 샘플 페이지
    @GetMapping("/sample")
    public String mailSample(){
        return "mailSample/sample";
    }

    /**
     * 인증번호가 담긴 메일을 보낸다.
     * @param mail
     * @return 인증번호
     */
    @ResponseBody
    @PostMapping("/mail")
    public String sendMail(String mail){

        int number = mailService.sendMail(mail);

        return String.valueOf(number);
    }

}