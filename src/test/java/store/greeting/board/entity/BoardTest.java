package store.greeting.board.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  @DisplayName("게시물 업데이트 테스트")
  void update() {

    // given
    Board board = Board.builder()
        .title("title")
        .content("content")
        .build();

    String newTitle = "title1";
    String newContent = "title2";

    // when
    board.update(newTitle, newContent);

    // then
    assertEquals(newTitle, board.getTitle());
    assertEquals(newContent, board.getContent());
  }
}