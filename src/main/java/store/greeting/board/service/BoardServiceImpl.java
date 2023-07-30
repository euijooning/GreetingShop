package store.greeting.board.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.greeting.board.dto.request.AddBoardRequestDto;
import store.greeting.board.dto.request.UpdateBoardRequestDto;
import store.greeting.board.entity.Board;
import store.greeting.board.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;

  public Board saveBoard(AddBoardRequestDto addBoardRequestDto) {
    return boardRepository.save(addBoardRequestDto.toEntity());
  }

  public List<Board> findAll() {
    return boardRepository.findAll();
  }

  public Board findById(long id) {
    return boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("찾는 아이디가 없습니다. : " + id));
  }

  public void deleteBoard(long id) {
    boardRepository.deleteById(id);
  }

  @Transactional
  public Board updateBoard(long id, UpdateBoardRequestDto updateBoardRequestDto) {
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("찾는 아이디가 없습니다. : " + id));

    board.update(updateBoardRequestDto.getTitle(), updateBoardRequestDto.getContent());

    return board;
  }

}