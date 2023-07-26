package store.greeting.cart.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import store.greeting.cart.dto.CartDetailDto;
import store.greeting.cart.dto.CartOrderDto;
import store.greeting.cart.dto.CartProductDto;
import store.greeting.cart.entity.Cart;
import store.greeting.cart.entity.CartProduct;
import store.greeting.cart.repository.CartProductRepository;
import store.greeting.cart.repository.CartRepository;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;
import store.greeting.order.dto.OrderDto;
import store.greeting.order.service.OrderServiceImpl;
import store.greeting.product.entity.Product;
import store.greeting.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{

  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  private final CartRepository cartRepository;
  private final CartProductRepository cartProductRepository;
  private final OrderServiceImpl orderService;

  public Long addCart(CartProductDto cartProductDto, String email) {
    Product product = productRepository.findById(cartProductDto.getProductId()).orElseThrow(
        EntityNotFoundException::new);
    Member member = memberRepository.findByEmail(email);

    Cart cart = cartRepository.findByMemberId(member.getId());
    if (cart == null) {
      cart = Cart.createCart(member);
      cartRepository.save(cart);
    }

    CartProduct savedCartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId());

    if (savedCartProduct != null) {
      savedCartProduct.addCount(cartProductDto.getCount());
      return savedCartProduct.getId();
    }
    else { // 카트에 아이템이 없는 경우
      CartProduct cartProduct = CartProduct.createCartProduct(cart, product, cartProductDto.getCount());
      cartProductRepository.save(cartProduct); // 카트아이템 저장
      return cartProduct.getId();
    }
  }


  // 장바구니 아이템 조회
  @Transactional(readOnly = true)
  public List<CartDetailDto> getCartList(String email) {

    Member member = memberRepository.findByEmail(email);

    Cart cart = cartRepository.findByMemberId(member.getId());

    if (cart == null) {
      return new ArrayList<>();
    }
    return cartProductRepository.findCartDetailDtoList(cart.getId());
  }

  // 현재 로그인한 회원과 해당 장바구니를 저장한 회원이 같은지 검사
  @Transactional(readOnly = true)
  public boolean validateCartProduct(Long cartProductId, String email) {
    Member currentMember = memberRepository.findByEmail(email);
    CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(EntityNotFoundException::new);
    Member savedMember = cartProduct.getCart().getMember();

    if (!StringUtils.equals(currentMember.getEmail(), savedMember.getEmail())) {
      return false;
    }
    return true;
  }


  // 장바구니 수량 업데이트 로직
  public void updateCartProductCount(Long cartProductId, int count) {
    CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(EntityNotFoundException::new);

    cartProduct.updateCount(count);
  }

  public void deleteCartProduct(Long cartProductId) {
    CartProduct cartProduct = cartProductRepository.findById(cartProductId)
        .orElseThrow(EntityNotFoundException::new);
    cartProductRepository.delete(cartProduct);
  }


  public Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String email){
    List<OrderDto> orderDtoList = new ArrayList<>();

    for (CartOrderDto cartOrderDto : cartOrderDtoList) {
      CartProduct cartProduct = cartProductRepository.findById(cartOrderDto.getCartProductId()).orElseThrow(EntityNotFoundException::new);

      OrderDto orderDto = new OrderDto();
      orderDto.setProductId(cartProduct.getProduct().getId());
      orderDto.setCount(cartProduct.getCount());
      orderDtoList.add(orderDto);
    }

    Long orderId = orderService.orders(orderDtoList, email);

    for (CartOrderDto cartOrderDto : cartOrderDtoList) {
      CartProduct cartProduct = cartProductRepository
          .findById(cartOrderDto.getCartProductId())
          .orElseThrow(EntityNotFoundException::new);
      cartProductRepository.delete(cartProduct);
    }
    return orderId;
  }

}