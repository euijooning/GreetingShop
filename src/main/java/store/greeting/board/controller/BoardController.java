package store.greeting.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import store.greeting.board.dto.request.AddBoardRequestDto;
import store.greeting.board.dto.request.UpdateBoardRequestDto;
import store.greeting.board.dto.response.BoardResponseDto;
import store.greeting.board.entity.Board;
import store.greeting.board.repository.BoardRepository;
import store.greeting.board.service.BoardServiceImpl;

@RequiredArgsConstructor
@Controller
public class BoardController {

  private final BoardRepository boardRepository;
  private final BoardServiceImpl boardService;

  // 게시글 목록 조회
  @GetMapping("/boards")
  public String getBoards(Model model) {
    List<BoardResponseDto> boards = boardService.findAll()
            .stream()
            .map(BoardResponseDto::new)
            .collect(Collectors.toList());

//    List<Board> boards = boardService.findAll();
//    List<BoardResponseDto> responseDtos = new ArrayList<>();
//    for (Board board : boards) {
//      BoardResponseDto responseDto = new BoardResponseDto(board);
//      responseDtos.add(responseDto);
//    }


    model.addAttribute("boards", boards);
    model.addAttribute("dto", new AddBoardRequestDto());

    return "board/boardList";
  }

  // 게시글 하나 조회
  @GetMapping("/boards/{id}")
  public String getBoard(@PathVariable Long id, Model model) {
    Board board = boardService.findById(id);
    model.addAttribute("board", new BoardResponseDto(board));

    return "board/board";
  }

  // 수정 화면으로 보내주는 핸들러
  @GetMapping("/boards/update/{id}")
  public String updateBoard(@PathVariable Long id, Model model) {
    Board board = boardService.findById(id);
    model.addAttribute("dto", new UpdateBoardRequestDto(board));

    return "board/updateBoard";
  }

  @PatchMapping("/boards/{id}")
  public ResponseEntity updateBoard(@PathVariable Long id, @RequestBody UpdateBoardRequestDto dto) {
    boardService.updateBoard(id, dto);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/boards/{id}")
  public ResponseEntity deleteBoard(@PathVariable Long id) {
    boardService.deleteBoard(id);

    return ResponseEntity.ok().build();
  }

  // 게시글 등록 화면
  @GetMapping("/board")
  public String newBoard(@RequestParam(required = false) Long id, Model model) {
    if (id == null) {
      model.addAttribute("board", new BoardResponseDto());
    } else {
      Board board = boardService.findById(id);
      model.addAttribute("board", new BoardResponseDto(board));
    }
    return "board/board";
  }

  // 게시글 등록
  @PostMapping("/board")
  public String saveBoard(@Valid AddBoardRequestDto dto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "redirect:board/board";
    }

    Board board = dto.toEntity();
    boardRepository.save(board);
    return "redirect:boards";
  }
}
