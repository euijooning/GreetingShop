package store.greeting.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.greeting.enums.OrderStatus;
import store.greeting.member.entity.Member;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrderProduct> orderProducts = new ArrayList<>();

  private LocalDateTime createTime;

  private LocalDateTime updateTime;


  // 상품 주문하기
  public void addOrderProduct(OrderProduct orderProduct) {
    orderProducts.add(orderProduct);
    OrderProduct.builder().order(this).build();
  }

  public static Order createOrder(Member member, List<OrderProduct> orderProductList) {
    Order order = Order.builder()
        .member(member)
        .orderStatus(OrderStatus.ORDER)
        .orderDate(LocalDateTime.now())
        .orderProducts(orderProductList)
        .createTime(LocalDateTime.now())
        .updateTime(LocalDateTime.now())
        .build();
    return order;
  }


  public int getTotalPrice() {
    int totalPrice = 0;
    for (OrderProduct orderProduct : orderProducts) {
      totalPrice += orderProduct.getTotalPrice();
    }
    return totalPrice;
  }

  public void cancelOrder() {
    this.orderStatus = OrderStatus.CANCEL;

    for(OrderProduct orderProduct : orderProducts) {
      orderProduct.cancel();
    }

  }
}
