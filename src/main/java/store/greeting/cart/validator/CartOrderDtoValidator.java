package store.greeting.cart.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import store.greeting.cart.dto.CartOrderDto;
import store.greeting.cart.service.CartService;
import store.greeting.exception.PermissionDeniedException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartOrderDtoValidator implements Validator {

    private final CartService cartService;

    // supports()를 통과하는 대상에 대해서만 검증을 수행
    @Override
    public boolean supports(Class<?> clazz) {
        return CartOrderDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartOrderDto dto = (CartOrderDto) target;
        List<CartOrderDto> cartOrderDtoList = dto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.isEmpty()) {
            throw new RuntimeException("주문할 상품을 선택해주세요");
        }

        for (CartOrderDto cartOrder : cartOrderDtoList) {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!cartService.validateCartProduct(cartOrder.getCartProductId(), name)) {
                throw new PermissionDeniedException("주문 권한이 없습니다.");
            }
        }
    }

}