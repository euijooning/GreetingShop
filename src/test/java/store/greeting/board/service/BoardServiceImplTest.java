package store.greeting.board.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.greeting.board.dto.request.AddBoardRequestDto;
import store.greeting.board.dto.request.UpdateBoardRequestDto;
import store.greeting.board.entity.Board;
import store.greeting.board.repository.BoardRepository;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

  @Mock
  BoardRepository boardRepository;

  @InjectMocks
  private BoardServiceImpl boardService;


  @Test
  @DisplayName("게시물 저장 테스트")
  public void saveBoard() {
    // given
    AddBoardRequestDto requestDto = new AddBoardRequestDto("title1", "content1");
    Board board = Board.builder()
        .title("title1")
        .content("content1")
        .build();
    when(boardRepository.save(any(Board.class))).thenReturn(board);

    // when
    Board savedBoard = boardService.saveBoard(requestDto);

    // then
    assertEquals("title1", savedBoard.getTitle());
    assertEquals("content1", savedBoard.getContent());
  }

  @Test
  @DisplayName("글 전체 조회")
  public void findAll() {
    // Given
    List<Board> boards = new ArrayList<>();
    Board board1 = Board.builder()
        .title("title1")
        .content("content2")
        .build();
    Board board2 = Board.builder()
        .title("title1")
        .content("content2")
        .build();
    boards.add(board1);
    boards.add(board2);
    when(boardRepository.findAll()).thenReturn(boards);

    // when
    List<Board> result = boardService.findAll();

    // then
    assertFalse(result.isEmpty());
    assertEquals(2, result.size());
  }


  @Test
  @DisplayName("게시물 업데이트 테스트")
  public void updateBoard() {
    // Given
    long existingId = 1L;
    UpdateBoardRequestDto updateDto = new UpdateBoardRequestDto(1L,"updatedTitle", "updatedContent");
    Board existingBoard = Board.builder()
        .title("tilte")
        .content("content")
        .build();
    when(boardRepository.findById(existingId)).thenReturn(Optional.of(existingBoard));

    // When
    Board updatedBoard = boardService.updateBoard(existingId, updateDto);

    // Then
    assertNotNull(updatedBoard);
    assertEquals("updatedTitle", updatedBoard.getTitle());
    assertEquals("updatedContent", updatedBoard.getContent());
  }


  @Test
  @DisplayName("게시물 삭제 테스트")
  public void deleteBoard() {
    // given
    long existingId = 1L;

    // when
    boardService.deleteBoard(existingId);

    // then
    verify(boardRepository, times(1)).deleteById(existingId);
  }

}