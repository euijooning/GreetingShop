package store.greeting.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.greeting.product.dto.ProductDto;
import store.greeting.product.dto.ProductSearchDto;
import store.greeting.product.entity.Product;

public interface ProductRepositoryCustom {

  Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pagable);

  Page<ProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);

  Page<ProductDto> getProductByProductNameOrProductDetailLike(String keyword, Pageable pageable);
}
