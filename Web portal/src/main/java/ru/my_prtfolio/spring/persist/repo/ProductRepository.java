package ru.my_prtfolio.spring.persist.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.my_prtfolio.spring.persist.entity.Product;
import ru.my_prtfolio.spring.persist.entity.User;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    Optional<Product> findById(long id);
    Page<Product> findAllByTitleContains(String tittle, Pageable pageable);
    Page<Product> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<Product> findAllByPriceLessThanEqual(BigDecimal maxPrice, Pageable pageable);
    Page<Product> findAllByPriceGreaterThanEqual(BigDecimal minPrice, Pageable pageable);

    Page<Product> findAllByTitleContainsAndPriceBetween(String title, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<Product> findAllByTitleContainsAndPriceGreaterThanEqual(String title, BigDecimal minPrice, Pageable pageable);
    Page<Product> findAllByTitleContainsAndPriceLessThanEqual(String title, BigDecimal maxPrice, Pageable pageable);
    Product getById(Long id);

}
