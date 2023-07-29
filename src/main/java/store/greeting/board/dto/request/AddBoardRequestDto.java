package store.greeting.board.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.greeting.board.entity.Board;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddBoardRequestDto {

  @NotBlank(message = "제목은 필수 입력 사항입니다.")
  private String title;

  @NotBlank(message = "내용을 입력해주세요.")
  private String content;

  public Board toEntity() {
    return Board.builder()
        .title(title)
        .content(content)
        .build();
  }
}
