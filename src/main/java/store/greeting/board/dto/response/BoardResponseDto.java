package store.greeting.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.greeting.board.entity.Board;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
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

  public static BoardResponseDto of(Long id, String title, String content, LocalDateTime createdAt) {
    return new BoardResponseDto(id, title, content, createdAt);

  }
}
