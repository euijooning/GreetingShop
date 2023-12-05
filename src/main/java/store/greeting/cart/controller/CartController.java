package store.greeting.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import store.greeting.cart.dto.CartDetailDto;
import store.greeting.cart.dto.CartOrderDto;
import store.greeting.cart.dto.CartProductDto;
import store.greeting.cart.service.CartServiceImpl;
import store.greeting.cart.validator.CartOrderDtoValidator;
import store.greeting.cart.validator.PrincipalContext;
import store.greeting.plus.AuthTokenParser;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {

  private final CartServiceImpl cartService;
  private final CartOrderDtoValidator cartOrderDtoValidator;


  // 주문 내역 조회
  @GetMapping(value = "/cart")
  public String orderHistory(Principal principal, Model model) {
    String[] userInfo = AuthTokenParser.getParseToken(principal);
//    List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
    List<CartDetailDto> cartDetailList = cartService.getCartList(userInfo[0],userInfo[1]);
    model.addAttribute("cartProducts", cartDetailList);
    return "cart/cartList";
  }

  // 수량 업데이트 로직
  @PatchMapping(value = "/cartProduct/{cartProductId}")
  public @ResponseBody ResponseEntity updateCartProduct(@PathVariable("cartProductId") Long cartProductId,
                                                        int count,
                                                        Principal principal) {
    String[] userInfo = AuthTokenParser.getParseToken(principal);

    if (count <= 0) {
      return new ResponseEntity<>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
    } else if (!cartService.validateCartProduct(cartProductId,  userInfo[0], userInfo[1])) {
      return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    cartService.updateCartProductCount(cartProductId, count);
    return new ResponseEntity(HttpStatus.OK);
  }

  // 장바구니 상품 추가
  @PostMapping(value = "/cart")
  @ResponseBody
  public ResponseEntity addCartProduct(@RequestBody @Valid CartProductDto cartProductDto,
                                       BindingResult bindingResult,
                                       Principal principal) {
    String[] userInfo = AuthTokenParser.getParseToken(principal);

    if (bindingResult.hasErrors()) {
      List<String> messages = bindingResult.getFieldErrors()
          .stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .collect(Collectors.toList());
      return new ResponseEntity<>(messages.toString(), HttpStatus.BAD_REQUEST);
    }

//    String email = principal.getName();
    Long cartProductId = cartService.addCart(cartProductDto, userInfo[0], userInfo[1]);
    return new ResponseEntity<>(cartProductId, HttpStatus.OK);
  }


  // 장바구니 상품 삭제
  @DeleteMapping("/cartProduct/{cartProductId}")
  public @ResponseBody ResponseEntity deleteCartProduct(@PathVariable("cartProductId") Long cartProductId,
                                                        Principal principal) {
    String[] userInfo = AuthTokenParser.getParseToken(principal);

    if (!cartService.validateCartProduct(cartProductId, userInfo[0], userInfo[1])) {
      return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    cartService.deleteCartProduct(cartProductId);
    return new ResponseEntity<>(cartProductId, HttpStatus.OK);
  }


  // 장바구니 상품 주문하기
  @PostMapping(value = "/cart/orders")
  public @ResponseBody ResponseEntity orderCartProduct(@RequestBody CartOrderDto cartOrderDto,
                                                       Principal principal) {
    String[] userInfo = AuthTokenParser.getParseToken(principal);
//    List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
//    Long orderId = cartService.orderCartProduct(cartOrderDtoList, principal.getName());
//    return new ResponseEntity<>(orderId, HttpStatus.OK);
//  }
    PrincipalContext.setPrincipal(principal);
    try {
      List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
      if (cartOrderDtoList == null || cartOrderDtoList.isEmpty()) {
        return new ResponseEntity<String>("주문할 상품을 선택해주세요.", HttpStatus.FORBIDDEN);
      }

      for (CartOrderDto cartOrder : cartOrderDtoList) {
        if (!cartService.validateCartProduct(cartOrder.getCartProductId(), userInfo[0], userInfo[1])) {
          return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
      }

      Long orderId = cartService.orderCartProduct(cartOrderDtoList, userInfo[0], userInfo[1]);
      return new ResponseEntity<>(orderId, HttpStatus.OK);
    } finally {
      PrincipalContext.clearPrincipal();
    }
  }



  @InitBinder("cartOrderDto")
  void init(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(cartOrderDtoValidator);
  }

}