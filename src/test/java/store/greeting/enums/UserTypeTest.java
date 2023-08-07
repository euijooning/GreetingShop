package store.greeting.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTypeTest {

  @DisplayName("type이 'ADMIN'이면 UserType.ADMIN 이어야 한다")
  @Test
  void t1() {
    assertThat(UserType.from("ADMIN")).isEqualTo(UserType.ADMIN);
  }

  @DisplayName("type이 'USER'이면 UserType.USER 이어야 한다")
  @Test
  void t2() {
    assertThat(UserType.from("USER")).isEqualTo(UserType.USER);
  }

  @DisplayName("type이 'ADMIN'도 아니고 'USER'도 아니면 예외가 발생해야 한다")
  @Test
  void t3() {
    Assertions.assertThatThrownBy(
            () -> UserType.from("NEW"))
        .isInstanceOf(IllegalStateException.class);
  }

}