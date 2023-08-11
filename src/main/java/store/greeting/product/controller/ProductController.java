package store.greeting.product.controller;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import store.greeting.enums.Category;
import store.greeting.product.dto.ProductDto;
import store.greeting.product.dto.ProductFormDto;
import store.greeting.product.dto.ProductSearchDto;
import store.greeting.product.entity.Product;
import store.greeting.product.repository.ProductRepository;
import store.greeting.product.service.ProductServiceImpl;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

  private final ProductServiceImpl productService;
  private final ProductRepository productRepository;

  // 상품 등록 폼
  @GetMapping(value = "/admin/product/new")
  public String productForm(Model model) {
    model.addAttribute("productFormDto", new ProductFormDto());
    return "product/productForm";
  }

  // 새로운 상품 등록
  @PostMapping(value = "/admin/product/new")
  public String productNew(@Valid ProductFormDto productFormDto,
      BindingResult bindingResult,
      Model model,
      @RequestParam("productImageFile") List<MultipartFile> productImageFileList) {
    if (bindingResult.hasErrors()) {
      return "product/productForm";
    }
    if (productImageFileList.get(0).isEmpty() && productFormDto.getId() == null) {
      model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
      return "product/productForm";
    }
    try {
      productService.saveProduct(productFormDto, productImageFileList);
    } catch (Exception e) {
      model.addAttribute("errorMessage", "상품 등록 중 오류가 발생하였습니다.");
      return "product/productForm";
    }
    return "redirect:/";
  }


  //상품 상세 정보
  @GetMapping(value = "/admin/product/{productId}")
  public String productDetail(@PathVariable("productId") Long productId, Model model) {
    try {
      ProductFormDto productFormDto = productService.getProductDetail(productId);
      model.addAttribute("productFormDto", productFormDto);
    } catch (EntityNotFoundException e) {
      model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
      model.addAttribute("productFormDto", new ProductFormDto());
      return "product/productForm";
    }
    return "product/productForm";
  }


  //상품 업데이트(관리자)
  @PostMapping(value = "/admin/product/{productId}")
  public String updateProduct(@Valid ProductFormDto productFormDto,
      BindingResult bindingResult,
      @RequestParam("productImageFile") List<MultipartFile> productImageFileList,
      Model model) {

    if (bindingResult.hasErrors()) {
      return "product/productForm";
    }

    if (productImageFileList.get(0).isEmpty() && productFormDto.getId() == null) {
      model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
      return "product/productForm";
    }
    try {
      productService.updateProduct(productFormDto, productImageFileList);
    } catch (Exception e) {
      model.addAttribute("errorMessage", "상품 수정 중 에러 발생하였습니다.");
      return "product/productForm";
    }
    return "redirect:/";
  }


  //상품 관리(관리자)
  @GetMapping(value = {"/admin/products", "/admin/products/{page}"})
  public String productManage(ProductSearchDto productSearchDto,
      @PathVariable("page") Optional<Integer> page, Model model) {

    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
    Page<Product> products = productService.getAdminProductPage(productSearchDto, pageable);

    model.addAttribute("products", products);
    model.addAttribute("productSearchDto", productSearchDto);
    model.addAttribute("maxPage", 5);

    return "product/productManage";
  }


  // 상품 상세 페이지
  @GetMapping(value = "/product/{productId}")
  public String productDetail(Model model, @PathVariable("productId") Long productId) {
    ProductFormDto productFormDto = productService.getProductDetail(productId);
    model.addAttribute("product", productFormDto);
    return "product/productDetail";
  }


  //상품 목록 조회
  @GetMapping(value = "/product")
  public String getProduct(ProductSearchDto productSearchDto,
      Optional<Integer> page,
      Model model,
      @RequestParam(name = "category", required = false) String categoryString) {
    Pageable pageable = PageRequest.of(page.orElse(0), 6);

    if (productSearchDto.getSearchQuery() == null) {
      productSearchDto.setSearchQuery("");
    }

    if (categoryString != null && !categoryString.isEmpty()) {
      Category category = Category.from(categoryString);
      productSearchDto.setSearchCategory(category);
    }

    Page<ProductDto> products = productService.getProducts(productSearchDto, pageable);

    model.addAttribute("products", products);
    model.addAttribute("productSearchDto", productSearchDto);
    model.addAttribute("maxPage", 5);

    if (Category.PHOTO_CARD.name().equals(categoryString)) {
      return "product/albumList";
    } else if (Category.GOODS.name().equals(categoryString)) {
      return "product/goodsList";
    } else if (Category.ALBUM.name().equals(categoryString)) {
      return "product/productList";
    } else {
      return "product/searchList";
    }
  }

  // 상품 검색
  @GetMapping("/product/search")
  public String searchProduct(@RequestParam String keyword,
      Model model) {
    Page<ProductDto> products = productRepository.getProductByProductNameOrProductDetailLike(
        keyword, PageRequest.of(0, 6));
    model.addAttribute("products", products);
    model.addAttribute("maxPage", 5);

    return "product/searchList";
  }

}