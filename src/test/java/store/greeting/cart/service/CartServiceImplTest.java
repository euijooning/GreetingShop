package store.greeting.cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.greeting.cart.entity.CartProduct;
import store.greeting.cart.repository.CartProductRepository;
import store.greeting.cart.repository.CartRepository;
import store.greeting.member.repository.MemberRepository;
import store.greeting.order.service.OrderServiceImpl;
import store.greeting.product.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

  @Mock
  private ProductRepository productRepository;
  @Mock
  private MemberRepository memberRepository;
  @Mock
  private CartRepository cartRepository;
  @Mock
  private CartProductRepository cartProductRepository;
  @Mock
  private OrderServiceImpl orderService;

  @InjectMocks
  private CartServiceImpl cartService;


//  @DisplayName("장바구니 상품 추가 테스트")
//  @Test
//  void addNewProduct() {
//    // given
//    CartProductDto cartProductDto = new CartProductDto();
//    cartProductDto.setProductId(1L);
//    cartProductDto.setCount(2);
//    String email = "newjeans@example.com";
//    Member member = Member.builder()
//        .email(email)
//        .build();
//    Product product = Product.builder()
//        .id(1L)
//        .build();
//
//    when(memberRepository.findByEmail(email)).thenReturn(member);
//    when(productRepository.findById(1L)).thenReturn(Optional.of(product)); // Product ID를 1L로 고정
//
//    // when
//    cartService.addCart(cartProductDto, email);
//
//    // then
//    verify(cartProductRepository, times(1)).save(any(CartProduct.class));
//  }


//  @Test
//  @DisplayName("장바구니가 없는 경우, 빈 리스트를 반환해야 함")
//  void getCartList_ShouldReturnEmptyListWhenCartIsNull() {
//    // Given
//    String email = "newjeans@example.com";
//    Member member = Member.builder().email(email).build();
//    when(memberRepository.findByEmail(email)).thenReturn(member);
//    when(cartRepository.findByMemberId(member.getId())).thenReturn(null);
//
//    // When
//    List<CartDetailDto> result = cartService.getCartList(email);
//
//    // Then
//    assertTrue(result.isEmpty());
//  }

//  @Test
//  @DisplayName("장바구니가 있는 경우, 해당 장바구니의 상품 목록을 반환해야 함")
//  void getCartList_ShouldReturnCartDetailDtoList() {
//    // Given
//    String email = "newjeans@example.com";
//    Member member = Member.builder()
//        .email(email)
//        .build();
//    Cart cart = Cart.builder()
//        .id(1L)
//        .member(member)
//        .build();
//
//    List<CartDetailDto> cartDetailDtoList = new ArrayList<>(); // 테스트용 임시 리스트
//
//    when(memberRepository.findByEmail(email)).thenReturn(member);
//    when(cartRepository.findByMemberId(member.getId())).thenReturn(cart);
//    when(cartProductRepository.findCartDetailDtoList(cart.getId())).thenReturn(cartDetailDtoList);
//
//    // when
//    List<CartDetailDto> result = cartService.getCartList(email);
//
//    // then
//    assertEquals(cartDetailDtoList, result);
//  }

//  @Test
//  @DisplayName("현재 회원과 해당 장바구니를 저장한 회원의 이메일이 일치하는 경우, true를 반환해야 함")
//  void validateCartProduct_ShouldReturnTrueForMatchingEmails() {
//    // given
//    String email = "newjeans@example.com";
//    Long cartProductId = 1L;
//    Member currentMember = Member.builder()
//        .email(email)
//        .build();
//    Cart cart = Cart.builder()
//        .member(currentMember)
//        .build();
//    CartProduct cartProduct = CartProduct.builder()
//        .id(cartProductId)
//        .cart(cart)
//        .build();
//
//    when(memberRepository.findByEmail(email)).thenReturn(currentMember);
//    when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.of(cartProduct));
//
//    // when
//    boolean result = cartService.validateCartProduct(cartProductId, email);
//
//    // then
//    assertTrue(result);
//  }

  @Test
  @DisplayName("장바구니 상품 수량이 성공적으로 업데이트되어야 함")
  void updateCartProductCount_ShouldUpdateProductCountInCart() {
    // given
    Long cartProductId = 1L;
    int newCount = 3;
    CartProduct cartProduct = CartProduct.builder()
        .id(cartProductId)
        .build();

    when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.of(cartProduct));

    // when
    cartService.updateCartProductCount(cartProductId, newCount);

    // then
    assertEquals(newCount, cartProduct.getCount());
  }

  @Test
  @DisplayName("존재하지 않는 장바구니 상품 ID로 호출할 경우, EntityNotFoundException 발생")
  void updateCartProductCount_ShouldThrowEntityNotFoundExceptionForNonExistentProduct() {
    // given
    Long nonExistentCartProductId = 999L;
    when(cartProductRepository.findById(nonExistentCartProductId)).thenReturn(Optional.empty());

    // when & then
    assertThrows(EntityNotFoundException.class,
        () -> cartService.updateCartProductCount(nonExistentCartProductId, 2));
  }

  @Test
  @DisplayName("장바구니 상품이 삭제되어야 함")
  void deleteCartProduct() {
    // given
    Long cartProductId = 1L;
    CartProduct cartProduct = CartProduct.builder()
        .id(cartProductId)
        .build();

    when(cartProductRepository.findById(cartProductId)).thenReturn(Optional.of(cartProduct));

    // when
    cartService.deleteCartProduct(cartProductId);

    // then
    verify(cartProductRepository, times(1)).delete(cartProduct);
  }

  @Test
  @DisplayName("존재하지 않는 장바구니 상품 ID로 삭제할 경우, EntityNotFoundException 발생")
  void deleteCartProduct_ShouldThrowEntityNotFoundExceptionForNonExistentProduct() {
    // given
    Long nonExistentCartProductId = 999L;

    when(cartProductRepository.findById(nonExistentCartProductId)).thenReturn(Optional.empty());

    // when & then
    assertThrows(EntityNotFoundException.class,
        () -> cartService.deleteCartProduct(nonExistentCartProductId));
  }
}