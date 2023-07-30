package store.greeting.board.service;

import java.util.List;
import store.greeting.board.dto.request.AddBoardRequestDto;
import store.greeting.board.dto.request.UpdateBoardRequestDto;
import store.greeting.board.entity.Board;

public interface BoardService {

  Board saveBoard(AddBoardRequestDto addBoardRequestDto);

  List<Board> findAll();

  Board findById(long id);

  void deleteBoard(long id);

  Board updateBoard(long id, UpdateBoardRequestDto updateBoardRequestDto);

}
