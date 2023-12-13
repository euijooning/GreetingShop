package store.greeting.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.greeting.board.entity.Board;
import store.greeting.board.repository.BoardRepository;
import store.greeting.comment.dto.CommentRequestDto;
import store.greeting.comment.dto.CommentResponseDto;
import store.greeting.comment.entity.Comment;
import store.greeting.comment.repository.CommentRepository;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;



    // 댓글 생성
    @Override
    public Long saveComment(CommentRequestDto commentRequestDto, Long boardId, String email) {
        Member member = memberRepository.findByEmail(email);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .board(board)
                .member(member)
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }


    // 댓글 조회
    @Override
    public List<CommentResponseDto> commentList(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        List<Comment> commentList = commentRepository.findByBoard(board);

        return commentList.stream()
                .map(comment -> CommentResponseDto.builder()
                        .comment(comment)
                        .build())
                .collect(Collectors.toList());
    }


    // 댓글 수정
    @Override
    public void updateComment(CommentRequestDto commentRequestDTO, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        comment.update(commentRequestDTO.getContent());
        commentRepository.save(comment);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
