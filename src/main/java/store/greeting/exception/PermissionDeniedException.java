package store.greeting.exception;

public class PermissionDeniedException extends RuntimeException {

  public PermissionDeniedException(String message) {
    super(message);
  }
}