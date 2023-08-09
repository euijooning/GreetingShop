package store.greeting.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.greeting.common.BaseEntity;
import store.greeting.product.entity.Product;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_product")
public class CartProduct extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "cart_product_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  Product product;

  private int count;


  public static CartProduct createCartProduct(Cart cart, Product product, int count) {
    CartProduct cartProduct = CartProduct.builder()
        .cart(cart)
        .product(product)
        .count(count)
        .build();

    return cartProduct;
  }

  // 수량 증가
  public void addCount(int count) {
    this.count += count;
  }

  // 장바구니 상품 수량 변경하기
  public void updateCount(int count) {
    this.count = count;
  }
}

