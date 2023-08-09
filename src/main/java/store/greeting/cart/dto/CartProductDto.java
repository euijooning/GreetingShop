package store.greeting.cart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductDto {

  @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
  private Long productId;

  @Min(value = 1, message = "수량을 최소 1개 이상 입력해주세요")
  private int count;

}