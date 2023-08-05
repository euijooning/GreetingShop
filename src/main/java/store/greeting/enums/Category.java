package store.greeting.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {
  ALBUM(0),
  GOODS(1),
  PHOTO_CARD(2),
  ;

  private final int id;

  Category(int id) {
    this.id = id;
  }

  public static Category from(int id) {
    return Arrays.stream(Category.values()).filter(category -> category.id == id).findFirst().orElseThrow();
  }

  public static Category from(String name) {
    return Arrays.stream(Category.values()).filter(category -> category.name().equals(name)).findFirst().orElseThrow();
  }

}
