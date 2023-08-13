package store.greeting.order.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

  @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
  private Long productId;

  @Min(value = 1, message = "최소 주문수량은 1개입니다.")
  @Max(value = 10, message = "최대 주문수량은 10개입니다.")
  private int count;
}

