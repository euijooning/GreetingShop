package store.greeting.product.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import store.greeting.enums.Category;
import store.greeting.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>,
    QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {

  List<Product> findByProductName(String productName);

  List<Product> findByProductNameOrProductDetail(String productName, String productDetail);

  List<Product> findByPriceLessThan(Integer price);

  List<Product> findByPriceLessThanOrderByPriceDesc(Integer price);

  @Query("select p from Product p where p.productDetail like %:productDetail% order by "
      + "p.price desc")
  List<Product> findByProductDetail(@Param("productDetail") String productDetail);

  @Query(value = "select * from Product p where p.product_detail like %:productDetail% " +
      "order by p.price desc", nativeQuery = true)
  List<Product> findByProductDetailByNative(@Param("productDetail") String productDetail);

  Page<Product> findByCategory(Category category, Pageable pageable);

}
