package store.greeting.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import store.greeting.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>,
    QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {
}
