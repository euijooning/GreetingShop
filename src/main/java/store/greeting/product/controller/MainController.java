package store.greeting.product.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.greeting.product.dto.MainProductDto;
import store.greeting.product.dto.ProductSearchDto;
import store.greeting.product.service.ProductServiceImpl;

@Controller
@RequiredArgsConstructor
public class MainController {

  @GetMapping(value = "/")
  public String main() {
    return "main";
  }

}
