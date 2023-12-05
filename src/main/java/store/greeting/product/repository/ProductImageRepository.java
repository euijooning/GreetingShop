package store.greeting.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.greeting.product.entity.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

  List<ProductImage> findByProductIdOrderByIdAsc(Long productId);

  ProductImage findByProductIdAndMainImageYn(Long productId, String mainImageYn);
}
