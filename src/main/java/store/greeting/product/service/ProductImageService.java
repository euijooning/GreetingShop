package store.greeting.product.service;

import org.springframework.web.multipart.MultipartFile;
import store.greeting.product.entity.ProductImage;

public interface ProductImageService {

  void saveProductImage(ProductImage productImage, MultipartFile productImageFile) throws Exception;

  void updateProductImage(Long productImageId, MultipartFile productImageFile) throws Exception;

}
