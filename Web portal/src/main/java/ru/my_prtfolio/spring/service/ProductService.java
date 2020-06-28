package ru.my_prtfolio.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.my_prtfolio.spring.persist.entity.Product;
import ru.my_prtfolio.spring.persist.repo.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(Product product) {
        repository.save(product);
    }

    @Transactional(readOnly = true)
    public Page<Product> filterAll(String title, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (title.equals("") && minPrice.intValue() == 0 && maxPrice.intValue() == 0) {
            return repository.findAll(pageable);
        } else if (title.equals("") && minPrice.intValue() == 0 && maxPrice.intValue() != 0) {
            return repository.findAllByPriceLessThanEqual(maxPrice,pageable);
        } else if (title.equals("") && minPrice.intValue() != 0 && maxPrice.intValue() == 0) {
            return repository.findAllByPriceGreaterThanEqual(minPrice,pageable);
        } else if (title.equals("") && minPrice.intValue() != 0 && maxPrice.intValue() != 0) {
            return repository.findAllByPriceBetween(minPrice, maxPrice,pageable);
        } else if (!title.equals("") && minPrice.intValue() == 0 && maxPrice.intValue() == 0) {
            return repository.findAllByTitleContains(title,pageable);
        } else if (!title.equals("") && minPrice.intValue() == 0 && maxPrice.intValue() != 0) {
            return repository.findAllByTitleContainsAndPriceLessThanEqual(title, maxPrice,pageable);
        } else if (!title.equals("") && minPrice.intValue() != 0 && maxPrice.intValue() == 0) {
            return repository.findAllByTitleContainsAndPriceGreaterThanEqual(title, minPrice,pageable);
        } else {
            return repository.findAllByTitleContainsAndPriceBetween(title, minPrice, maxPrice,pageable);
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAll();
    }


    @Transactional(readOnly = true)
    public Optional<Product> findById(long id) {
        return repository.findById(id);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
