package store.greeting.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.greeting.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
  Member findByEmail(String email);

}
