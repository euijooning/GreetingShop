package store.greeting.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import store.greeting.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
  List<ProductImage> findByProductIdOrderByIdAsc(Long productId);

  ProductImage findByProductIdAndMainImageYn(Long productId, String mainImageYn);
}
