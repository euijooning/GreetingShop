package store.greeting.order.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.greeting.product.entity.Product;

class OrderProductTest {

  @Test
  @DisplayName("주문 상품 생성 테스트")
  void createOrderProduct() {
    // given
    Product product = mock(Product.class);
    int orderCount = 3;
    when(product.getPrice()).thenReturn(1000);

    // when
    OrderProduct orderProduct = OrderProduct.createOrderProduct(product, null, orderCount);

    // then
    assertEquals(product, orderProduct.getProduct());
    assertEquals(orderCount, orderProduct.getCount());
    assertEquals(1000, orderProduct.getOrderPrice());

    verify(product, times(1)).removeStock(orderCount);
  }


  @Test
  @DisplayName("주문 상품의 총 가격 계산")
  void getTotalPrice() {
    // given
    OrderProduct orderProduct = OrderProduct.builder()
        .orderPrice(20000)
        .count(3)
        .build();
    // when
    int totalPrice = orderProduct.getTotalPrice();

    // then
    assertEquals(60000, totalPrice);
  }


  @Test
  @DisplayName("주문 상품 취소 메서드 테스트")
  void cancel() {
    // given
    Product product = mock(Product.class);
    int orderCount = 3;
    OrderProduct orderProduct = OrderProduct.builder()
        .product(product)
        .count(orderCount)
        .build();

    // when
    orderProduct.cancel();

    // then
    verify(product, times(1)).addStock(orderCount);
  }
}