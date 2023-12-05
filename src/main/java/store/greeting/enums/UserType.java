package store.greeting.enums;

public enum UserType {
  USER,
  ADMIN,
  NAVER,
  GOOGLE,
  KAKAO
  ;

  public static UserType from(String type) {
    if (USER.name().equals(type) || NAVER.name().equals(type) || GOOGLE.name().equals(type) || KAKAO.name().equals(type)) {
      return USER;
    } else if (ADMIN.name().equals(type)) {
      return ADMIN;
    } else {
      throw new IllegalStateException();
    }
  }
}
