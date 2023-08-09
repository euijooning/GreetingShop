package store.greeting.order.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;
import store.greeting.order.dto.OrderDto;
import store.greeting.order.dto.OrderHistoryDto;
import store.greeting.order.dto.OrderProductDto;
import store.greeting.order.entity.Order;
import store.greeting.order.entity.OrderProduct;
import store.greeting.order.repository.OrderRepository;
import store.greeting.product.entity.Product;
import store.greeting.product.entity.ProductImage;
import store.greeting.product.repository.ProductImageRepository;
import store.greeting.product.repository.ProductRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final ProductImageRepository productImageRepository;


  //주문
  public Long order(OrderDto orderDto, String email) {

    // 아이템 추출
    Product product = productRepository.findById(orderDto.getProductId())
        .orElseThrow(EntityNotFoundException::new);
    // 이메일로 멤버 객체 추출
    Member member = memberRepository.findByEmail(email);

    List<OrderProduct> orderProductList = new ArrayList<>();
    OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
    orderProductList.add(orderProduct);

    Order order = Order.createOrder(member, orderProductList);
    orderRepository.save(order);

    return order.getId();
  }

  // 주문 목록 조회
  @Transactional(readOnly = true)
  public Page<OrderHistoryDto> getOrderList(String email, Pageable pageable) {

    List<Order> orders = orderRepository.findOrders(email, pageable);
    Long totalCount = orderRepository.countOrder(email);
    List<OrderHistoryDto> historyDtos = new ArrayList<>();

    for (Order order : orders) {
      OrderHistoryDto historyDto = new OrderHistoryDto(order);
      List<OrderProduct> orderProducts = order.getOrderProducts();
      for (OrderProduct orderProduct : orderProducts) {
        ProductImage productImage = productImageRepository.findByProductIdAndMainImageYn(orderProduct.getProduct().getId(),"Y");
        OrderProductDto orderProductDto = new OrderProductDto(orderProduct, productImage.getImageUrl());
        historyDto.addOrderProductDto(orderProductDto);
      }
      historyDtos.add(historyDto);
    }
    return new PageImpl<OrderHistoryDto>(historyDtos, pageable, totalCount);
  }

  // 주문 유효성 검사
  @Transactional(readOnly = true)
  public boolean validateOrder(Long orderId, String email) {
    Member currentMember = memberRepository.findByEmail(email);
    Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    Member savedMember = order.getMember();

    return StringUtils.equals(currentMember.getEmail(), savedMember.getEmail());
  }

  //주문 취소
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    order.cancelOrder();
  }


  //주문
  public Long orders(List<OrderDto> orderDtoList, String email) {
    Member member = memberRepository.findByEmail(email);

    List<OrderProduct> orderProductList = new ArrayList<>();

    for (OrderDto orderDto : orderDtoList) {
      Product product = productRepository.findById(orderDto.getProductId()).orElseThrow(EntityNotFoundException::new);
      OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
      orderProductList.add(orderProduct);
    }
    Order order = Order.createOrder(member, orderProductList);
    orderRepository.save(order);

    return order.getId();
  }

}