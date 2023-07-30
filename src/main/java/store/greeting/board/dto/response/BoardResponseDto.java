package store.greeting.board.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.greeting.board.entity.Board;

@NoArgsConstructor
@Getter
public class BoardResponseDto {

  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdAt;

  public BoardResponseDto(Board board) {
    this.id = board.getId();
    this.title = board.getTitle();
    this.content = board.getContent();
    this.createdAt = board.getCreatedAt();
  }
}
