package store.greeting.comment.service;

import store.greeting.comment.dto.CommentRequestDto;
import store.greeting.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    Long saveComment(CommentRequestDto commentRequestDto, Long boardId, String email);

    List<CommentResponseDto> commentList(Long id);

    void updateComment(CommentRequestDto commentRequestDto, Long commentId);

    void deleteComment(Long commentId);
}
