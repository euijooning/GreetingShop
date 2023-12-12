package store.greeting.mail;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String title;
    private String address;
    private String message;
}