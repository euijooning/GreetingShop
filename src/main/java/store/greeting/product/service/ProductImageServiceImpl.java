package store.greeting.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import store.greeting.product.entity.ProductImage;
import store.greeting.product.repository.ProductImageRepository;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {

    @Value("${productImageLocation}")
    private String productImageLocation;

    private final ProductImageRepository productImageRepository;
    private final FileService fileService;

    @Override
    public void saveProductImage(ProductImage productImage, MultipartFile productImageFile) throws Exception {

        String originImageName = productImageFile.getOriginalFilename(); // 오리지널 이미지 경로
        String imageName = "";
        String imageUrl = "";
        log.info(originImageName); // 뉴진스.jpg

        //파일 업로드
        if (!StringUtils.isEmpty(originImageName)) { //orinalImageName이 문자열로 비어 있지 않으면 실행
            System.out.println("******");
            imageName = fileService.uploadFile(originImageName, productImageFile.getBytes());
            System.out.println(imageName);
            imageUrl = "/img/" + imageName;
        }

        // 상품 이미지 정보 저장
        // originImageName : 상품 이미지 파일의 오리지널 이름.
        // imageName : 실제 로컬에 저장된 상품 이미지 파일의 이름.
        // imageUrl : 로컬에 저장된 상품 이미지 파일을 불러오는 경로.

        productImage.updateProductImage(originImageName, imageName, imageUrl);
        productImageRepository.save(productImage);
    }

    @Override
    public void updateProductImage(Long productImageId, MultipartFile productImageFile) throws Exception {
        if (!productImageFile.isEmpty()) { // 상품의 이미지를 수정한 경우 상품 이미지가 업데이트
            ProductImage savedProductImage = productImageRepository.findById(productImageId).orElseThrow(
                    EntityExistsException::new); // 기존 엔티티 조회

            if (!StringUtils.isEmpty(savedProductImage.getImageName())) {
                fileService.deleteFile(productImageLocation + "/" + savedProductImage.getImageName());
            }
            String originImageName = productImageFile.getOriginalFilename();
            String imgName = fileService.uploadFile(productImageLocation, productImageFile.getBytes()); // 파일 업로드
            String imgUrl = "/img/" + imgName;

            savedProductImage.updateProductImage(originImageName, imgName, imgUrl); // 이것 역시 save 부르지 않고 변경감지로 변경만 됨.

        }
    }
}
