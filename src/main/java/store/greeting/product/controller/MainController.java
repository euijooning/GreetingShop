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

  private final ProductServiceImpl productService;

  @GetMapping(value = "/")
  public String main(ProductSearchDto productSearchDto, Optional<Integer> page, Model model) {
    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

    if (productSearchDto.getSearchQuery() == null) {
      productSearchDto.setSearchQuery("");
    }
    Page<MainProductDto> products = productService.getMainProductPage(productSearchDto, pageable);

    System.out.println(products.getNumber() + "!!!!!");
    System.out.println(products.getTotalPages() + "#####");

    model.addAttribute("products", products);
    model.addAttribute("productSearchDto", productSearchDto);
    model.addAttribute("maxPage", 5);
    return "main";
  }

}
