package store.greeting.cart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import store.greeting.cart.dto.CartDetailDto;
import store.greeting.cart.entity.CartProduct;


public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

  CartProduct findByCartIdAndProductId(Long cartId, Long productId);

  @Query(
      "select new store.greeting.cart.dto.CartDetailDto(cp.id, p.productName, p.price, cp.count, pim.imageUrl)"
          +
          "from CartProduct cp, ProductImage pim " +
          "join cp.product p " +
          "where cp.cart.id = :cartId " +
          "and pim.product.id = cp.product.id " +
          "and pim.mainImageYn = 'Y' " +
          "order by cp.createTime desc")
  List<CartDetailDto> findCartDetailDtoList(Long cartId);

}