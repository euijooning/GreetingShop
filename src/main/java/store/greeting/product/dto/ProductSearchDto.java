package store.greeting.product.dto;

import lombok.Getter;
import lombok.Setter;
import store.greeting.enums.Category;
import store.greeting.enums.SellStatus;

@Getter
@Setter
public class ProductSearchDto {

  private String searchDateType;
  private SellStatus searchSellStatus;
  private String searchBy;
  private String searchQuery = "";
  private Category searchCategory;

}
