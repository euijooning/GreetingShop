package store.greeting.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import store.greeting.comment.dto.CommentRequestDto;
import store.greeting.comment.service.CommentServiceImpl;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentServiceImpl commentService;


    // 댓글 등록
    @PostMapping("/boards/{id}/comment")
    public String saveComment(@PathVariable Long id,
                              CommentRequestDto commentRequestDto,
                              Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        commentService.saveComment(commentRequestDto, id, userDetails.getUsername());

        return "redirect:/boards/" + id;
    }


    // 댓글 수정
    @ResponseBody
    @PostMapping("/boards/{id}/comment/{commentId}/update")
    public String updateComment(@PathVariable Long id,
                                @PathVariable Long commentId,
                                CommentRequestDto commentRequestDto) {

        commentService.updateComment(commentRequestDto, commentId);
        return "/boards/" + id;
    }


    // 댓글 삭제
    @GetMapping("/boards/{id}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long id,
                                @PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return "redirect:/boards/" + id;
    }

}
