package store.greeting.product.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.greeting.enums.SellStatus;
import store.greeting.exception.OutOfStockException;
import store.greeting.product.dto.ProductFormDto;

class ProductTest {


  @Test
  @DisplayName("상품 업데이트 테스트")
  public void updateProduct() {
    // given
    ProductFormDto formDto = new ProductFormDto();
    formDto.setProductName("PhotoCard");
    formDto.setPrice(5000);
    formDto.setStockNumber(20);
    formDto.setProductDetail("포토카드 상세 설명");
    formDto.setRequestDetail("배송 전 연락주세요");
    formDto.setSellStatus(SellStatus.SELL);

    Product product = Product.builder().build();

    // when
    product.updateProduct(formDto);

    // then
    assertEquals("PhotoCard", product.getProductName());
    assertEquals(5000, product.getPrice());
    assertEquals(20, product.getStockNumber());
    assertEquals("포토카드 상세 설명", product.getProductDetail());
    assertEquals("배송 전 연락주세요", product.getRequestDetail());
    assertEquals(SellStatus.SELL, product.getSellStatus());
  }



  @Test
  @DisplayName("상품 재고 제거 성공 태스트")
  public void removeStock_success() {
    // given
    Product product = Product.builder().stockNumber(20).build();

    // when
    product.removeStock(10);

    // then
    assertEquals(10, product.getStockNumber());
  }


  @Test
  @DisplayName("상품 재고 제거 실패 - 재고 부족")
  public void removeStock_fails() {
    // given
    Product product = Product.builder().stockNumber(20).build();

    // when, then
    assertThrows(OutOfStockException.class, () -> product.removeStock(25));
  }


  @Test
  @DisplayName("상품 재고 추가 테스트")
  public void addStock() {
    // given
    Product product = Product.builder().stockNumber(10).build();

    // when
    product.addStock(20);

    // then
    assertEquals(30, product.getStockNumber());
  }

}