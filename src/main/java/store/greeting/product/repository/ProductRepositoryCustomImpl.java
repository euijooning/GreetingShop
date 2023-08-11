package store.greeting.product.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import store.greeting.enums.Category;
import store.greeting.enums.SellStatus;
import store.greeting.product.dto.ProductDto;
import store.greeting.product.dto.ProductSearchDto;
import store.greeting.product.dto.QProductDto;
import store.greeting.product.entity.Product;
import store.greeting.product.entity.QProduct;
import store.greeting.product.entity.QProductImage;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

  private final JPAQueryFactory queryFactory; // 동적 쿼리 사용하기 위해 JPAQueryFactory 변수 선언

  public ProductRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em); // JPAQueryFactory 실질적인 객체가 만들어진다.
  }

  private BooleanExpression searchSellStatusEq(SellStatus searchSellStatus) {
    return searchSellStatus == null ? null : QProduct.product.sellStatus.eq(searchSellStatus);
  }

  private BooleanExpression createdDatesAfter(String searchDateType) {
    LocalDateTime dateTime = LocalDateTime.now(); //현재시간을 추출해서 변수에 대입

    if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
      return null;
    } else if (StringUtils.equals("1d", searchDateType) || searchDateType == null) {
      dateTime = dateTime.minusDays(1);
    } else if (StringUtils.equals("1w", searchDateType) || searchDateType == null) {
      dateTime = dateTime.minusWeeks(1);
    } else if (StringUtils.equals("1m", searchDateType) || searchDateType == null) {
      dateTime = dateTime.minusMonths(1);
    } else if (StringUtils.equals("6m", searchDateType) || searchDateType == null) {
      dateTime = dateTime.minusMonths(6);
    }
    return QProduct.product.createTime.after(dateTime);
  }

  private BooleanExpression searchByLike(String searchBy, String searchQuery) {
    if (StringUtils.equals("productName", searchBy)) {
      return QProduct.product.productName.like("%" + searchQuery + "%");
    } else if (StringUtils.equals("createdBy", searchBy)) {
      return QProduct.product.createdBy.like("%" + searchQuery + "%");
    }
    return null;
  }

  @Override
  public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
    QueryResults<Product> results = queryFactory.selectFrom(QProduct.product)
        .where(createdDatesAfter(productSearchDto.getSearchDateType()),
            searchSellStatusEq(productSearchDto.getSearchSellStatus()),
            searchByLike(productSearchDto.getSearchBy(), productSearchDto.getSearchQuery()))
        .orderBy(QProduct.product.id.desc())
        .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
    List<Product> content = results.getResults();
    Long total = results.getTotal();
    return new PageImpl<>(content, pageable, total);
  }

  private BooleanExpression productNameLike(String searchQuery) {
    return StringUtils.isEmpty(searchQuery) ? null
        : QProduct.product.productName.like("%" + searchQuery + "%");
  }

  private BooleanExpression categoryEqual(Category category) {
    return category == null ? null : QProduct.product.category.eq(category);
  }

  @Override
  public Page<ProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
    QProduct product = QProduct.product;
    QProductImage productImage = QProductImage.productImage;

    QueryResults<ProductDto> results = queryFactory.select(new QProductDto(product.id,
            product.productName,
            product.productDetail,
            productImage.imageUrl,
            product.price))

        .from(productImage)
        .join(productImage.product, product)
        .where(productImage.mainImageYn.eq("Y"))
        .where(productNameLike(productSearchDto.getSearchQuery()))
        .where(categoryEqual(productSearchDto.getSearchCategory()))
        .orderBy(product.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
        .fetchResults();

    List<ProductDto> content = results.getResults();
    long total = results.getTotal();
    return new PageImpl<>(content, pageable, total);
  }
}
