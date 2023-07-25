package store.greeting.cart.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartOrderDto {

  Long cartProductId;
  List<CartOrderDto> cartDetailDtoList;

}