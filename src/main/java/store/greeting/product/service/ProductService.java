package store.greeting.product.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import store.greeting.product.dto.ProductDto;
import store.greeting.product.dto.ProductFormDto;
import store.greeting.product.dto.ProductSearchDto;
import store.greeting.product.entity.Product;

public interface ProductService {

  Long saveProduct(ProductFormDto productFormDto, List<MultipartFile> productImageFileList) throws Exception;
  ProductFormDto getProductDetail(Long productId);
  Long updateProduct(ProductFormDto productFormDto, List<MultipartFile> productImageFileList) throws Exception;
  Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable);
  Page<ProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);

  Page<ProductDto> getProducts(ProductSearchDto productSearchDto, Pageable pageable);

}
