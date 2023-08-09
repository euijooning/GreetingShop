package store.greeting.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@Slf4j
public class ExceptionController {

  // 마지막 핸들링
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handle(HttpServletRequest req, Exception ex) {
    log.warn(ex.getMessage());
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PermissionDeniedException.class)
  public ResponseEntity<String> handleNoPermissionException(HttpServletRequest req, Exception ex) {
    log.warn(ex.getMessage());
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }
}