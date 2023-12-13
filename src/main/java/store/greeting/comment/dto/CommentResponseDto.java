package store.greeting.comment.dto;

import lombok.*;
import store.greeting.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String name;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    @Builder
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.name = comment.getMember().getName();
        this.email = comment.getMember().getEmail();
        this.createTime = comment.getCreateTime();
        this.updateTime = comment.getUpdateTime();
    }
}
