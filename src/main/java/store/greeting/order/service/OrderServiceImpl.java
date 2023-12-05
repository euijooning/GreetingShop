package store.greeting.order.service;

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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final ProductImageRepository productImageRepository;

  //주문
  @Override
  public Long order(OrderDto orderDto, String email, String loginType) {
    // 아이템 추출
    Product product = productRepository.findById(orderDto.getProductId())
        .orElseThrow(EntityNotFoundException::new);
    // 이메일로 멤버 객체 추출
    Member member = memberRepository.findByEmailAndLoginType(email, loginType);

    List<OrderProduct> orderProductList = new ArrayList<>();

    Order order = Order.createOrder(member, orderProductList);

    OrderProduct orderProduct = OrderProduct.createOrderProduct(product, order, orderDto.getCount());
    orderProductList.add(orderProduct);

    orderRepository.save(order);

    return order.getId();
  }

  // 주문 목록 조회
  @Override
  @Transactional(readOnly = true)
  public Page<OrderHistoryDto> getOrderList(String email, String loginType, Pageable pageable) {
    List<Order> orders = orderRepository.findOrdersByEmailAndLoginType(email, loginType, pageable);
    Long totalCount = orderRepository.countOrder(email);
    List<OrderHistoryDto> historyDtos = new ArrayList<>();

    for (Order order : orders) {
      OrderHistoryDto historyDto = new OrderHistoryDto(order);
      List<OrderProduct> orderProducts = order.getOrderProducts();

      for (OrderProduct orderProduct : orderProducts) {
        ProductImage productImage = productImageRepository.findByProductIdAndMainImageYn(orderProduct.getProduct().getId(), "Y");

//        if (productImage == null){ // productId로 productImage를 못찾으면 -> customProductId로 찾는다
//          productImage = productImageRepository.findByCustomProductIdAndMainImageYn(orderProduct.getProduct().getId(), "Y");
//        }

        OrderProductDto orderProductDto = new OrderProductDto(orderProduct, productImage.getImageUrl());
        historyDto.addOrderProductDto(orderProductDto);
      }
      historyDtos.add(historyDto);
    }
    return new PageImpl<>(historyDtos, pageable, totalCount);
  }

  // 주문 유효성 검사
  @Override
  @Transactional(readOnly = true)
  public boolean validateOrder(Long orderId, String email, String loginType) {
    Member currentMember = memberRepository.findByEmailAndLoginType(email, loginType);
    Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    Member savedMember = order.getMember();

    return StringUtils.equals(currentMember.getEmail(), savedMember.getEmail());
  }

  //주문 취소
  @Override
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    order.cancelOrder();
  }


  //주문
  @Override
  public Long orders(List<OrderDto> orderDtoList, String email, String loginType) {
    Member member = memberRepository.findByEmailAndLoginType(email, loginType);

    List<OrderProduct> orderProductList = new ArrayList<>();
    Order order = Order.createOrder(member, orderProductList);

    for (OrderDto orderDto : orderDtoList) {
      Product product = productRepository.findById(orderDto.getProductId()).orElseThrow(EntityNotFoundException::new);
      OrderProduct orderProduct = OrderProduct.createOrderProduct(product, order, orderDto.getCount());
      orderProductList.add(orderProduct);
    }

    orderRepository.save(order);

    return order.getId();
  }

}