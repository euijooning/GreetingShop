package store.greeting.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.greeting.common.BaseEntity;
import store.greeting.product.entity.Product;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "order_product_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice;

  private int count;


  public static OrderProduct createOrderProduct(Product product, int count) {
    OrderProduct orderProduct = OrderProduct.builder()
        .product(product)
        .count(count)
        .orderPrice(product.getPrice())
        .build();
    product.removeStock(count);
    return orderProduct;
  }

  public int getTotalPrice() {
    return orderPrice*count;
  }

  // 주문취소
  public void cancel() {
    this.getProduct().addStock(count);
  }
}
