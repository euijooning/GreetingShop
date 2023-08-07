package store.greeting.order.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.greeting.enums.OrderStatus;
import store.greeting.member.entity.Member;

@ExtendWith(MockitoExtension.class)
class OrderTest {

  @Mock
  private Member member;

  @Mock
  private OrderProduct orderProduct;


  @Test
  @DisplayName("주문 설정 되는지 확인")
  void createOrder() {
    // given
    List<OrderProduct> orderProductList = new ArrayList<>();
    orderProductList.add(orderProduct);

    // when
    Order order = Order.createOrder(member, orderProductList);

    // then
    assertEquals(OrderStatus.ORDER, order.getOrderStatus());
  }


  @Test
  @DisplayName("주문 취소")
  void cancelOrder() {
    // given
    List<OrderProduct> orderProductList = new ArrayList<>();
    orderProductList.add(orderProduct);

    Order order = Order.createOrder(member, orderProductList);

    // when
    order.cancelOrder();

    // then
    assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
  }


  @Test
  @DisplayName("주문한 상품들의 가격을 올바르게 합산하는지 확인")
  void getTotalPrice_right() {
    // given
    List<OrderProduct> orderProductList = new ArrayList<>();
    OrderProduct orderProduct1 = mock(OrderProduct.class);
    when(orderProduct1.getTotalPrice()).thenReturn(10000);
    orderProductList.add(orderProduct1);

    OrderProduct orderProduct2 = mock(OrderProduct.class);
    when(orderProduct2.getTotalPrice()).thenReturn(20000);
    orderProductList.add(orderProduct2);

    // when
    Order order = Order.createOrder(mock(Member.class), orderProductList);
    int totalPrice = order.getTotalPrice();

    // then
    assertEquals(30000, totalPrice);
  }
}