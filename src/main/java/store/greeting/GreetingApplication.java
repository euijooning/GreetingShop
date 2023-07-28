package store.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.greeting.enums.SellStatus;
import store.greeting.enums.UserType;
import store.greeting.member.entity.Member;
import store.greeting.member.repository.MemberRepository;
import store.greeting.product.entity.Product;
import store.greeting.product.entity.ProductImage;
import store.greeting.product.repository.ProductImageRepository;
import store.greeting.product.repository.ProductRepository;

@SpringBootApplication
public class GreetingApplication {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;

  @Autowired
  public GreetingApplication(MemberRepository memberRepository,
      PasswordEncoder passwordEncoder, ProductRepository productRepository ,ProductImageRepository productImageRepository) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
    this.productRepository = productRepository;
    this.productImageRepository = productImageRepository;
  }

  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {

      Member userMember = Member.builder()
          .email("user@gmail.com")
          .name("사용자")
          .password(passwordEncoder.encode("12345678"))
          .tel("01012345678")
          .role(UserType.USER)
          .build();

      memberRepository.save(userMember);

      Member adminMember = Member.builder()
          .email("admin@gmail.com")
          .name("관리자")
          .password(passwordEncoder.encode("12345678"))
          .tel("01012345678")
          .role(UserType.ADMIN)
          .build();

      memberRepository.save(adminMember);

      for (int i=1; i<=5; i++) {
        Product product = Product.builder()
            .productName("상품 " + i)
            .price(10000 + 1000*i)
            .sellStatus(SellStatus.SELL)
            .stockNumber(10 + 2*i)
            .productDetail(i+ "번 상품입니다.")
            .build();
        productRepository.save(product);

        ProductImage productImage = ProductImage.builder()
            .product(product)
            .imageUrl("/img/image" + i +".jpg")
            .mainImageYn("Y")
            .build();
        productImageRepository.save(productImage);
      }
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(GreetingApplication.class, args);
  }

}
