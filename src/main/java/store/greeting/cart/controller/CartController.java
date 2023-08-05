package store.greeting.cart.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import store.greeting.cart.dto.CartDetailDto;
import store.greeting.cart.dto.CartOrderDto;
import store.greeting.cart.dto.CartProductDto;
import store.greeting.cart.service.CartServiceImpl;
import store.greeting.cart.validator.CartOrderDtoValidator;

@Controller
@RequiredArgsConstructor
public class CartController {

  private final CartServiceImpl cartService;
  private final CartOrderDtoValidator cartOrderDtoValidator;

  // 주문하기
  @PostMapping(value = "/cart")
  public @ResponseBody ResponseEntity order(@RequestBody @Valid CartProductDto cartProductDto,
      BindingResult bindingResult, Principal principal) {
    if (bindingResult.hasErrors()) {
      StringBuilder sb = new StringBuilder();
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      for (FieldError fieldError : fieldErrors) {
        sb.append(fieldError.getDefaultMessage());
      }
      return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
    String email = principal.getName();
    Long cartProductId;
    try {
      cartProductId = cartService.addCart(cartProductDto, email);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
  }

  // 주문 내역 조회
  @GetMapping(value = "/cart")
  public String orderHistory(Principal principal, Model model) {
    List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
    model.addAttribute("cartProducts", cartDetailList);
    return "cart/cartList";
  }

  // 수량 업데이트 로직
  @PatchMapping(value = "/cartProduct/{cartProductId}")
  public @ResponseBody ResponseEntity updateCartProduct(@PathVariable("cartProductId") Long cartProductId,
      int count, Principal principal) {

    if (count <=0) {
      return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
    } else if (!cartService.validateCartProduct(cartProductId, principal.getName())) {
      return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    cartService.updateCartProductCount(cartProductId, count);
    return new ResponseEntity(HttpStatus.OK);
  }


  // 장바구니 상품 추가
  @PostMapping(value = "/cart")
  @ResponseBody
  public ResponseEntity addCartProduct(@RequestBody @Valid CartProductDto cartProductDto,
      BindingResult bindingResult, Principal principal) {
    if (bindingResult.hasErrors()) {
      List<String> messages = bindingResult.getFieldErrors().stream().map(
          DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
      return new ResponseEntity<>(messages.toString(), HttpStatus.BAD_REQUEST);
    }

    String email = principal.getName();
    Long cartProductId = cartService.addCart(cartProductDto, email);
    return new ResponseEntity<>(cartProductId, HttpStatus.OK);
  }


  // 장바구니 상품 삭제
  @DeleteMapping("/cartProduct/{cartProductId}")
  public @ResponseBody ResponseEntity deleteCartProduct(@PathVariable("cartProductId") Long cartProductId, Principal principal) {

    if (!cartService.validateCartProduct(cartProductId, principal.getName())) {
      return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
    cartService.deleteCartProduct(cartProductId);
    return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
  }


  // 장바구니 상품 주문하기
  @PostMapping(value = "/cart/orders")
  public @ResponseBody ResponseEntity orderCartProduct(@RequestBody CartOrderDto cartOrderDto, Principal principal){

    List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
    Long orderId = cartService.orderCartProduct(cartOrderDtoList, principal.getName());
    return new ResponseEntity<Long>(orderId, HttpStatus.OK);
  }

  @InitBinder("cartOrderDto")
  void init(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(cartOrderDtoValidator);
  }

}

