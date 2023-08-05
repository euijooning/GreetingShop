package store.greeting.cart.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.greeting.enums.UserType;
import store.greeting.member.entity.Member;

class CartTest {

  @Test
  @DisplayName("장바구니 생성 테스트")
  public void testCreateCart() {
    // given
    Member member = Member.builder()
        .name("minji")
        .tel("01012345670")
        .email("newjeans@ador.com")
        .password("password1234")
        .role(UserType.USER)
        .build();


    // when
    Cart cart = Cart.createCart(member);

    // then
    assertEquals(member, cart.getMember());
  }
}