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


  // 게시글 저장
  @Override
  public Board saveBoard(AddBoardRequestDto addBoardRequestDto) {
    return boardRepository.save(addBoardRequestDto.toEntity());
  }

  // 게시글 목록 조회
  @Override
  public List<Board> findAll() {
    return boardRepository.findAll();
  }

  // 게시글 단건 조회
  @Override
  public Board findById(long id) {
    return boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("찾는 아이디가 없습니다. : " + id));
  }


  // 게시글 수정
  @Transactional
  @Override
  public Board updateBoard(long id, UpdateBoardRequestDto updateBoardRequestDto) {
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("찾는 아이디가 없습니다. : " + id));

    board.update(updateBoardRequestDto.getTitle(), updateBoardRequestDto.getContent());

    return board;
  }

  // 게시글 삭제
  @Override
  public void deleteBoard(long id) {
    boardRepository.deleteById(id);
  }


}