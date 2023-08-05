package store.greeting.cart.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.greeting.product.entity.Product;

class CartProductTest {

  @Test
  @DisplayName("장바구니 상품 생성 테스트")
  public void createCartProduct() {
    // given
    Cart cart = new Cart();
    Product product = new Product();
    int count = 3;

    // when
    CartProduct cartProduct = CartProduct.createCartProduct(cart, product, count);

    // Then
    assertEquals(cart, cartProduct.getCart());
    assertEquals(product, cartProduct.getProduct());
    assertEquals(count, cartProduct.getCount());
  }

  @Test
  @DisplayName("장바구니 상품 수량 추가 테스트")
  public void addCount() {
    // given
    Cart cart = new Cart();
    Product product = new Product();
    int initialCount = 3;
    CartProduct cartProduct = CartProduct.builder()
        .cart(cart)
        .product(product)
        .count(initialCount)
        .build();

    // when
    int additionalCount = 2;
    cartProduct.addCount(additionalCount);

    // then
    assertEquals(initialCount + additionalCount, cartProduct.getCount());
  }

  @Test
  @DisplayName("장바구니의 상품 수량 업데이트 테스트")
  public void testUpdateCount() {
    // given
    Cart cart = new Cart();
    Product product = new Product();
    int initialCount = 3;
    CartProduct cartProduct = CartProduct.builder()
        .cart(cart)
        .product(product)
        .count(initialCount)
        .build();

    // when
    int newCount = 5;
    cartProduct.updateCount(newCount);

    // Then
    assertEquals(newCount, cartProduct.getCount());
  }
}