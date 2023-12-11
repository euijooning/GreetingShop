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

    @GetMapping("/mainpage")
    public String MailPage(){
        return "mailSample/mainpage";
    }

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){

        int number = mailService.sendMail(mail);

        String num = "" + number;

        return num;
    }

}