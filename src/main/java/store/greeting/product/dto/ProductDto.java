package store.greeting.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

  private Long id;
  private String productName;
  private String productDetail;
  private String imageUrl;
  private Integer price;

  @QueryProjection
  public ProductDto(Long id, String productName, String productDetail, String imageUrl, Integer price) {
    this.id = id;
    this.productName = productName;
    this.productDetail = productDetail;
    this.imageUrl = imageUrl;
    this.price = price;
  }
}
