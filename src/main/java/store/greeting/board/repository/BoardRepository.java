package store.greeting.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.greeting.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}