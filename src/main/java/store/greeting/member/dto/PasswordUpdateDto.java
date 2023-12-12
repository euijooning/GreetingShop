package store.greeting.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDto {

    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private String oldPassword;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "다시 한번 입력해주세요.")
    private String confirmPassword;
}