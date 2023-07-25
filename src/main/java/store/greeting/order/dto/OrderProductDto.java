package store.greeting.order.dto;

import lombok.Getter;
import lombok.Setter;
import store.greeting.order.entity.OrderProduct;

@Getter
@Setter
public class OrderProductDto {

  private String productName;
  private int count;
  private int orderPrice;
  private String imageUrl;

  public OrderProductDto(OrderProduct orderProduct, String imageUrl) {
    this.productName = orderProduct.getProduct().getProductName();
    this.count = orderProduct.getCount();
    this.orderPrice = orderProduct.getOrderPrice();
    this.imageUrl = imageUrl;
  }

}
