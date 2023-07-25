package store.greeting.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.greeting.order.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
