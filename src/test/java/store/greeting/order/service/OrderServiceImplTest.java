package store.greeting.order.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import store.greeting.enums.OrderStatus;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;
import store.greeting.order.dto.OrderDto;
import store.greeting.order.dto.OrderHistoryDto;
import store.greeting.order.entity.Order;
import store.greeting.order.repository.OrderRepository;
import store.greeting.product.entity.Product;
import store.greeting.product.repository.ProductImageRepository;
import store.greeting.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private ProductImageRepository productImageRepository;

  @InjectMocks
  private OrderServiceImpl orderService;


  @Test
  @DisplayName("주문 생성 테스트")
  void order() {
    // given
    String email = "newjeans@ador.world";
    OrderDto orderDto = new OrderDto();
    orderDto.setProductId(1L);
    orderDto.setCount(2);

    Product product = Product.builder()
        .id(1L)
        .stockNumber(20)
        .build();
    Member member = mock(Member.class);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(memberRepository.findByEmail(email)).thenReturn(member);
    when(orderRepository.save(any(Order.class))).thenReturn(Order.builder().id(1L).build());


    // when
    orderService.order(orderDto, email, member.getLoginType());

    // then
    verify(orderRepository, times(1)).save(any(Order.class));
  }


//  @Test
//  @DisplayName("주문 목록 조회 테스트")
//  void getOrderList() {
//    // given
//    String email = "newjeanst@ador.world";
//    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("orderDate")));
//
//    List<Order> orderList = new ArrayList<>();
//    Order order = mock(Order.class);
//    when(order.getId()).thenReturn(1L);
//    when(order.getOrderDate()).thenReturn(LocalDateTime.now());
//    when(order.getOrderStatus()).thenReturn(OrderStatus.ORDER);
//    orderList.add(order);
//
//    when(orderRepository.findOrders(email, pageable)).thenReturn(orderList);
//    when(orderRepository.countOrder(email)).thenReturn(1L);
//
//    // when
//    Page<OrderHistoryDto> orderHistoryPage = orderService.getOrderList(email, pageable);
//
//    // then
//    assertNotNull(orderHistoryPage);
//    assertEquals(1, orderHistoryPage.getContent().size());
//  }


//  @Test
//  @DisplayName("주문 유효성 검사 테스트")
//  void validateOrder() {
//    // given
//    Long orderId = 1L;
//    String email = "newjeans@ador.world";
//    Order order = mock(Order.class);
//    Member member = mock(Member.class);
//
//    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//    when(memberRepository.findByEmail(email)).thenReturn(member);
//    when(order.getMember()).thenReturn(member);
//
//    // when
//    boolean isValidated = orderService.validateOrder(orderId, email);
//
//    // then
//    assertTrue(isValidated);
//  }


  @Test
  @DisplayName("주문 취소 테스트")
  void cancelOrder() {
    // given
    Long orderId = 1L;
    Order order = mock(Order.class);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    // when
    orderService.cancelOrder(orderId);

    // then
    verify(order, times(1)).cancelOrder();
  }


//  @Test
//  @DisplayName("다수의 상품 주문 테스트")
//  void orders() {
//    // given
//    String email = "newjeans@ador.world";
//    OrderDto orderDto1 = new OrderDto();
//    orderDto1.setProductId(1L);
//    orderDto1.setCount(2);
//
//    OrderDto orderDto2 = new OrderDto();
//    orderDto2.setProductId(2L);
//    orderDto2.setCount(3);
//
//    List<OrderDto> orderDtoList = new ArrayList<>();
//    orderDtoList.add(orderDto1);
//    orderDtoList.add(orderDto2);
//
//    Product product1 = mock(Product.class);
//    Product product2 = mock(Product.class);
//    Member member = mock(Member.class);
//
//    when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
//    when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
//    when(memberRepository.findByEmail(email)).thenReturn(member);
//
//    // When
//    orderService.orders(orderDtoList, email);
//
//    // Then
//    verify(orderRepository, times(1)).save(any(Order.class));
//  }
}