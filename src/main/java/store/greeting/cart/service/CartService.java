package store.greeting.cart.service;

import java.util.List;
import store.greeting.cart.dto.CartDetailDto;
import store.greeting.cart.dto.CartOrderDto;
import store.greeting.cart.dto.CartProductDto;

public interface CartService {

  Long addCart(CartProductDto cartProductDto, String email);
  List<CartDetailDto> getCartList(String email);
  boolean validateCartProduct(Long cartProductId, String email);
  void updateCartProductCount(Long cartProductId, int count);
  void deleteCartProduct(Long cartProductId);
  Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String email);

}

