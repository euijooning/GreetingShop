package store.greeting.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import store.greeting.common.BaseEntity;
import store.greeting.member.entity.Member;

@Entity
@Table(name = "cart")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Cart extends BaseEntity {

  @Id
  @Column(name = "cart_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;


  public static Cart createCart(Member member) {
    Cart cart = Cart.builder()
        .member(member)
        .build();
    return cart;
  }

}
