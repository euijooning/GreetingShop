package store.greeting.product.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import store.greeting.common.BaseEntity;
import store.greeting.enums.SellStatus;
import store.greeting.exception.OutOfStockException;
import store.greeting.member.entity.Member;
import store.greeting.product.dto.ProductFormDto;

@Entity
@Table(name="product")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseEntity {
  @Id
  @Column(name="product_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // 상품코드

  @Column(nullable = false, length = 50)
  private String productName; // 상품명

  @Column(name="price",nullable = false)
  private int price; // 가격

  @Column(nullable = false)
  private int stockNumber; //재고 수량

  @Lob
  @Column(nullable = false)
  private String productDetail; // 상품 상세 설명

  @Enumerated(EnumType.STRING)
  private SellStatus sellStatus; // 상품 판매 상태


  @ManyToMany
  @JoinTable(
      name = "member_product",
      joinColumns = @JoinColumn(name = "member_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Member> member;

  private String requestDetail;

  public void updateProduct(ProductFormDto formDto) {
    this.productName = formDto.getProductName();
    this.price = formDto.getPrice();
    this.stockNumber = formDto.getStockNumber();
    this.productDetail = formDto.getProductDetail();
    this.requestDetail = formDto.getRequestDetail();
    this.sellStatus = formDto.getSellStatus();
  }

  // 팔린 상품 제거하기
  public void removeStock(int stockNumber) {
    int restStock = this.stockNumber - stockNumber;
    if (restStock < 0) {
      throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: "+ this.stockNumber +")");
    }
    this.stockNumber = restStock;
  }


  public void addStock(int stockNumber) {
    this.stockNumber += stockNumber;
  }
}