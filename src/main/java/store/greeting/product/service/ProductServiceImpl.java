package store.greeting.product.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.greeting.product.dto.MainProductDto;
import store.greeting.product.dto.ProductFormDto;
import store.greeting.product.dto.ProductImageDto;
import store.greeting.product.dto.ProductSearchDto;
import store.greeting.product.entity.Product;
import store.greeting.product.entity.ProductImage;
import store.greeting.product.repository.ProductImageRepository;
import store.greeting.product.repository.ProductRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductImageServiceImpl productImageService;
  private final ProductImageRepository productImageRepository;


  public Long saveProduct(ProductFormDto productFormDto, ProductImageDto productImageDto, List<MultipartFile> productImageFileList) throws Exception {
    // 상품 등록
    Product product= productFormDto.createProduct();
    productRepository.save(product);

    //이미지 등록
    for (int i = 0; i < productImageFileList.size(); i++) {
      ProductImage productImage =  ProductImage.builder()
          .imageName(productImageFileList.get(i).getOriginalFilename())
          .originImage(productImageDto.getOriginImageName())
          .imageUrl(productImageDto.getImageUrl())
          .mainImageYn(i == 0 ? "Y" : "N")
          .product(product)
          .build();


      productImageService.saveProductImage(productImage, productImageFileList.get(i));
    }
    return product.getId();
  }

  @Transactional(readOnly = true)
  public ProductFormDto getProductDetail(Long productId){
    List<ProductImage> productImageList = productImageRepository.findByProductIdOrderByIdAsc(productId);
    List<ProductImageDto> productImageDtoList = new ArrayList<>();

    for (ProductImage productImage : productImageList) {
      ProductImageDto productImageDto = ProductImageDto.of(productImage);
      productImageDtoList.add(productImageDto);
    }

    Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
    ProductFormDto productFormDto = ProductFormDto.of(product);
    productFormDto.setProductImageDtoList(productImageDtoList);
    return productFormDto;
  }

  // 수정 만들기
  public Long updateProduct(ProductFormDto productFormDto, List<MultipartFile> productImageFileList) throws Exception {
    Product product = productRepository.findById(productFormDto.getId()).orElseThrow(
        EntityExistsException::new);
    product.updateProduct(productFormDto); // save 따로 부르지 않아도 변경이 됨.

    List<Long> productImageIds = productFormDto.getProductImageIds();

    for (int i =0; i<productImageFileList.size();i++) {
      productImageService.updateProductImage(productImageIds.get(i), productImageFileList.get(i));
    }
    return product.getId();
  }

  @Transactional(readOnly = true)
  public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
    return productRepository.getAdminProductPage(productSearchDto, pageable);
  }

  @Transactional(readOnly = true)
  public Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
    return productRepository.getMainProductPage(productSearchDto, pageable);
  }

}

