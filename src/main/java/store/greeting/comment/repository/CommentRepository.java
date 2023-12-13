package store.greeting.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.greeting.board.entity.Board;
import store.greeting.comment.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoard(Board board);
}
