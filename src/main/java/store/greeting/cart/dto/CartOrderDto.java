package store.greeting.cart.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartOrderDto {

  private Long cartProductId;
  private List<CartDetailDto> cartDetailDtoList;

}