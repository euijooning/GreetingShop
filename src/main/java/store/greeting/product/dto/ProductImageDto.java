package store.greeting.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import store.greeting.product.entity.ProductImage;

@Getter
@Setter
public class ProductImageDto {

  private Long id;

  private String imageName;

  private String originImageName;

  private String imageUrl;

  private String mainImageYn;

  private static ModelMapper modelMapper = new ModelMapper();

  public static ProductImageDto of(ProductImage productImage){
    return modelMapper.map(productImage, ProductImageDto.class);
  }
}
