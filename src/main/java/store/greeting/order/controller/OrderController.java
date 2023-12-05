package store.greeting.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import store.greeting.order.dto.OrderDto;
import store.greeting.order.dto.OrderHistoryDto;
import store.greeting.order.service.OrderServiceImpl;
import store.greeting.config.AuthTokenParser;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final OrderServiceImpl orderService;

  // 주문하기
  @PostMapping(value = "/order")
  public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal) {

    String[] userInfo = AuthTokenParser.getParseToken(principal);

    if (bindingResult.hasErrors()) {
      StringBuilder sb = new StringBuilder();
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();

      for (FieldError fieldError : fieldErrors) {
        sb.append(fieldError.getDefaultMessage());
      }
      return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }

//    String email = principal.getName();
    Long orderId;
    try {
      orderId = orderService.order(orderDto, userInfo[0], userInfo[1]);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(orderId, HttpStatus.OK);
  }


  // 주문 이력 조회
  @GetMapping(value = {"/orders", "/orders/{page}"})
  public String orderHistory(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
    String[] userInfo = AuthTokenParser.getParseToken(principal);
    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
    Page<OrderHistoryDto> historyDtoList = orderService.getOrderList(userInfo[0], userInfo[1], pageable);

    model.addAttribute("orders", historyDtoList);
    model.addAttribute("page", pageable.getPageNumber());
    model.addAttribute("maxPage", 5);

    return "order/orderHistory";
  }


  // 주문 취소
  @PostMapping("/order/{orderId}/cancel")
  public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
    String email = AuthTokenParser.getParseToken(principal)[0];
    String loginType = AuthTokenParser.getParseToken(principal)[1];
    if (!orderService.validateOrder(orderId, email, loginType)) {
      return new ResponseEntity<>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    orderService.cancelOrder(orderId);
    return new ResponseEntity<>(orderId, HttpStatus.OK);
  }

}
