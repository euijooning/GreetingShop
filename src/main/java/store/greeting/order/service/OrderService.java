package store.greeting.order.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.greeting.order.dto.OrderDto;
import store.greeting.order.dto.OrderHistoryDto;

public interface OrderService {

  Long order(OrderDto orderDto, String email, String loginType);
  Page<OrderHistoryDto> getOrderList(String email, String loginType, Pageable pageable);
  boolean validateOrder(Long orderId, String email, String loginType);
  void cancelOrder(Long orderId);
  Long orders(List<OrderDto> orderDtoList, String email, String loginType);

}
