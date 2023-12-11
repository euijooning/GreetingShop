package store.greeting.mail;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String title;
    private String address;
    private String message;
}