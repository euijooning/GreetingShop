package store.greeting.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.greeting.common.BaseEntity;

@Entity
@Table(name = "product_img")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage extends BaseEntity {

  @Id
  @Column(name = "product_img_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String imageName;

  private String originImage;

  private String imageUrl;

  private String mainImageYn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  public void updateProductImage(String originImage, String imgName, String imgUrl){
    this.originImage = originImage;
    this.imageName = imgName;
    this.imageUrl = imgUrl;
  }
}