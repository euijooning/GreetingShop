package store.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.greeting.board.entity.Board;
import store.greeting.board.repository.BoardRepository;
import store.greeting.enums.Category;
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

  @Autowired
  ProductRepository productRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  ProductImageRepository productImageRepository;

  @Autowired
  BoardRepository boardRepository;

  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {
      Board board1 = Board.builder().title("배송 문의드립니다.").content("안녕하세요. 이틀 전 주문한 상품 배송추적 하고 싶습니다.").build();
      Board board2 = Board.builder().title("휴무일").content("이번 명절에 쉬시나요?").build();

      boardRepository.save(board1);
      boardRepository.save(board2);

      Member userMember = Member.builder()
          .email("user@naver.com")
          .name("사용자")
          .password(passwordEncoder.encode("12341234"))
          .tel("01012341234")
          .role(UserType.USER)
          .build();
      memberRepository.save(userMember);

      Member adminMember = Member.builder()
          .email("admin@naver.com")
          .name("관리자")
          .password(passwordEncoder.encode("12341234"))
          .tel("01012341234")
          .role(UserType.ADMIN)
          .build();
      memberRepository.save(adminMember);



      Product goods1 = Product.builder()
          .productName("뉴진스 빙키봉")
          .price(49000)
          .sellStatus(SellStatus.SELL)
          .stockNumber(140)
          .productDetail("그룹 newjeans의 공식 응원도구 '빙키봉' 단품입니다.")
          .category(Category.GOODS)
          .build();
      productRepository.save(goods1);

      ProductImage goodsImage1 = ProductImage.builder()
          .product(goods1)
          .imageUrl("/img/image" + 1 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(goodsImage1);



      Product goods2 = Product.builder()
          .productName("아이브 응원봉")
          .price(42000)
          .sellStatus(SellStatus.SELL)
          .stockNumber(110)
          .productDetail("그룹 IVE의 공식 응원봉입니다.")
          .category(Category.GOODS)
          .build();
      productRepository.save(goods2);

      ProductImage goodsImage2 = ProductImage.builder()
          .product(goods2)
          .imageUrl("/img/image" + 2 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(goodsImage2);



      Product goods3 = Product.builder()
          .productName("BTS 에어팟 케이스")
          .price(24000)
          .sellStatus(SellStatus.SELL)
          .stockNumber(30)
          .productDetail("그룹 BTS의 에어팟 케이스입니다.")
          .category(Category.GOODS)
          .build();
      productRepository.save(goods3);

      ProductImage goodsImage3 = ProductImage.builder()
          .product(goods3)
          .imageUrl("/img/image" + 3 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(goodsImage3);



      Product photoCard1 = Product.builder()
          .productName("르세라핌 포토카드 세트")
          .price(20000)
          .sellStatus(SellStatus.SELL)
          .stockNumber(10)
          .productDetail("그룹 르세라핌 포토카드입니다.")
          .category(Category.PHOTO_CARD)
          .build();
      productRepository.save(photoCard1);

      ProductImage pcImage1 = ProductImage.builder()
          .product(photoCard1)
          .imageUrl("/img/image" + 4 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(pcImage1);



      Product photoCard2 = Product.builder()
          .productName("IVE 포토카드 세트")
          .price(23000)
          .sellStatus(SellStatus.SELL)
          .stockNumber(30)
          .productDetail("그룹 IVE 포토카드입니다.")
          .category(Category.PHOTO_CARD)
          .build();
      productRepository.save(photoCard2);

      ProductImage pcImage2 = ProductImage.builder()
          .product(photoCard2)
          .imageUrl("/img/image" + 5 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(pcImage2);



      Product photoCard3 = Product.builder()
          .productName("NewJeans 포토카드 세트")
          .price(25000)
          .sellStatus(SellStatus.SELL)
          .stockNumber(10)
          .productDetail("그룹 newjeans 포토카드입니다.")
          .category(Category.PHOTO_CARD)
          .build();
      productRepository.save(photoCard3);

      ProductImage pcImage3 = ProductImage.builder()
          .product(photoCard3)
          .imageUrl("/img/image" + 6 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(pcImage3);


      Product album1 = Product.builder()
          .productName("뉴진스 (NewJeans) - 1st EP 'New Jeans' Weverse Albums")
          .price(11000)
          .sellStatus(SellStatus.SELL)
          .stockNumber(30)
          .productDetail("1st EP \"New Jeans\"는 NewJeans가 추구하는 '좋은 음악'에 대한 질문을 던진다.\n"
              + "정형화된 K-POP의 익숙한 공식을 따르지 않았고, POP에 기반을 두고 있지만 특정 스타일만을 고수하지 않았다. "
              + "어디서든 편하게 들을 수 있는 세련된 이지리스닝 팝을 추구하는 동시에 과장 없는 자연스러운 사운드 엔지니어링으로 "
              + "NewJeans 멤버들 본연의 목소리를 살리는 프로듀싱을 진행했다.")
          .category(Category.ALBUM)
          .build();
      productRepository.save(album1);

      ProductImage albumImage1 = ProductImage.builder()
          .product(album1)
          .imageUrl("/img/image" + 7 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(albumImage1);


      Product album2 = Product.builder()
          .productName("엑소 (EXO) 7집 - EXIST [Digipack Ver.]")
          .price(14700)
          .sellStatus(SellStatus.SELL)
          .stockNumber(30)
          .productDetail("이번 앨범에는 총 9곡이 수록되어 있으며, "
              + "4월 엑소 데뷔 11주년 기념 팬미팅에서 무대로 보여줘 화제를 모은 "
              + "선공개곡 ‘Let Me In’을 비롯한 다채로운 분위기의 음악을 만날 수 있어 "
              + "글로벌 팬들의 관심이 집중될 것으로 보인다.")
          .category(Category.ALBUM)
          .build();
      productRepository.save(album2);

      ProductImage albumImage2 = ProductImage.builder()
          .product(album2)
          .imageUrl("/img/image" + 8 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(albumImage2);


      Product album3 = Product.builder()
          .productName("오마이걸 (OH MY GIRL) - 미니앨범 9집 : Golden Hourglass")
          .price(23800)
          .sellStatus(SellStatus.SELL)
          .stockNumber(20)
          .productDetail("그룹 '오마이걸'의 미니 9집 앨범 [Golden Hourglass]가 7월 24일 오후 6시에 발매된다.")
          .category(Category.ALBUM)
          .build();
      productRepository.save(album3);

      ProductImage albumImage3 = ProductImage.builder()
          .product(album3)
          .imageUrl("/img/image" + 9 + ".jpg")
          .mainImageYn("Y")
          .build();
      productImageRepository.save(albumImage3);
    };
  }


  public static void main(String[] args) {
    SpringApplication.run(GreetingApplication.class, args);
  }

}
