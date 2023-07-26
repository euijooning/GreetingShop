package store.greeting.enums;

public enum UserType {
  USER,
  ADMIN,
  ;

  public static UserType from(String type) {
    if (USER.name().equals(type)) {
      return USER;
    } else if (ADMIN.name().equals(type)) {
      return ADMIN;
    } else {
      throw new IllegalStateException();
    }
  }
}
