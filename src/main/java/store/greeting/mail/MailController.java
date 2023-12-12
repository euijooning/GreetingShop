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

    @GetMapping("/sample")
    public String mailSample(){
        return "mailSample/sample";
    }

    @ResponseBody
    @PostMapping("/mail")
    public String sendMail(String mail){

        int number = mailService.sendMail(mail);

        return String.valueOf(number);
    }

}