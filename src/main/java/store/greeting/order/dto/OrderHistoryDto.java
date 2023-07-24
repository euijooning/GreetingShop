package store.greeting.order.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import store.greeting.enums.OrderStatus;
import store.greeting.order.entity.Order;

@Getter
@Setter
public class OrderHistoryDto {

  private Long orderId;
  private String orderDate;
  private OrderStatus orderStatus;
  private List<OrderProductDto> orderProductDtoList = new ArrayList<>();

  public OrderHistoryDto(Order order) {
    this.orderId = order.getId();
    this.orderDate = order.getOrderDate()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    this.orderStatus = order.getOrderStatus();
  }
  public void addOrderProductDto(OrderProductDto orderProductDto) {
    orderProductDtoList.add(orderProductDto);
  }
}

