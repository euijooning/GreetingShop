package store.greeting.product.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductImageTest {

  @Test
  @DisplayName("상품 이미지 업데이트 테스트")
  public void updateProductImage() {
    // given
    String originImage = "유재석.jpg";
    String imageName = "유재석대상.jpg";
    String imageUrl = "/images/유재석대상.jpg";
    ProductImage productImage = ProductImage.builder().build();

    // when
    productImage.updateProductImage(originImage, imageName, imageUrl);

    // then
    assertEquals(originImage, productImage.getOriginImage());
    assertEquals(imageName, productImage.getImageName());
    assertEquals(imageUrl, productImage.getImageUrl());
  }
}