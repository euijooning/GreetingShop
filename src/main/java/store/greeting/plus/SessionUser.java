package store.greeting.plus;

import lombok.Getter;
import store.greeting.member.entity.Member;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private final String name;
    private final String email;
    private final String picture;
    private final String loginType;

    public SessionUser(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
        this.loginType = member.getLoginType();
    }
}
