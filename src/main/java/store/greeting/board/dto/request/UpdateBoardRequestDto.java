package store.greeting.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.greeting.board.entity.Board;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateBoardRequestDto {

  private Long id;
  private String title;
  private String content;

  public UpdateBoardRequestDto(Board board) {
    this.id = board.getId();
    this.title = board.getTitle();
    this.content = board.getContent();
  }

}
