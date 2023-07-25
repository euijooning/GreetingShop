package store.greeting.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.greeting.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Cart findByMemberId(Long memberId);

}
