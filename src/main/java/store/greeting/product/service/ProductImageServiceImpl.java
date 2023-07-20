package store.greeting.product.service;

import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import store.greeting.product.entity.ProductImage;
import store.greeting.product.repository.ProductImageRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductImageServiceImpl implements ProductImageService{

  @Value("${productImageLocation}")
  private String productImageLocation;

  private final ProductImageRepository productImageRepository;
  private final FileService fileService;

  public void saveProductImage(ProductImage productImage, MultipartFile productImageFile) throws Exception {

    String originImageName = productImageFile.getOriginalFilename(); // 오리지널 이미지 경로
    String imageName ="";
    String imageUrl = "";
    log.info(originImageName); // 뉴진스.jpg

    //파일 업로드
    if(!StringUtils.isEmpty(originImageName)) { //oriImgName이 문자열로 비어 있지 않으면 실행
      System.out.println("******");
      imageName = fileService.uploadFile(productImageLocation, originImageName, productImageFile.getBytes());
      System.out.println(imageName);
      imageUrl = "/images/product/" + imageName;
    }

    productImage.updateProductImage(originImageName, imageName, imageUrl);
    productImageRepository.save(productImage);
  }

  public void updateProductImage(Long productImageId, MultipartFile productImageFile) throws Exception {
    if(!productImageFile.isEmpty()) { // 상품의 이미지를 수정한 경우 상품 이미지 업데이트
      ProductImage savedProductImage = productImageRepository.findById(productImageId).orElseThrow(
          EntityExistsException::new); // 기존 엔티티 조회

      if (!StringUtils.isEmpty(savedProductImage.getImageName())) {
        fileService.deleteFile(productImageLocation + "/" +savedProductImage.getImageName());
      }
      String originImageName = productImageFile.getOriginalFilename();
      String imgName = fileService.uploadFile(productImageLocation, originImageName, productImageFile.getBytes()); // 파일 업로드
      String imgUrl = "/images/product" + imgName;

      savedProductImage.updateProductImage(originImageName, imgName,imgUrl); // 이것 역시 save 부르지 않고 변경감지로 변경만 됨.

    }
  }
}