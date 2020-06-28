package ru.my_prtfolio.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.my_prtfolio.spring.persist.entity.Product;
import ru.my_prtfolio.spring.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RequestMapping("/product")
@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String productList(Model model,
                              @RequestParam (defaultValue = "", required=false) String title,
                              @RequestParam (defaultValue = "0", required=false) BigDecimal minPrice,
                              @RequestParam (defaultValue = "0", required=false) BigDecimal maxPrice,
                              @RequestParam (name = "page", defaultValue = "1", required = false) Integer page,
                              @RequestParam (name = "size", defaultValue = "5", required = false) Integer size
                              ) {
        logger.info("Product list");
        Page<Product> productsPage = productService.filterAll(title, minPrice, maxPrice,
                PageRequest.of(page- 1, size)
        );
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("prevPageNumber", productsPage.hasPrevious() ? productsPage.previousPageable().getPageNumber() + 1 : -1);
        model.addAttribute("nextPageNumber", productsPage.hasNext() ? productsPage.nextPageable().getPageNumber() + 1 : -1);
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        logger.info("Create product form");
        model.addAttribute("product", new Product());
        return "product";
    }

    @GetMapping(path="/update/{id}")
    public String updateById(Model model, @PathVariable(value = "id") Long productId) {
        Optional<Product> product = productService.findById(productId);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping(path="/remove/{id}")
    public String removeById(@PathVariable(value = "id") Long productId) {
        productService.delete(productId);
        return "redirect:/product/list";
    }



    @PostMapping
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "product";
        }
        if(product.getPrice().doubleValue()<0){
            bindingResult.rejectValue("price", "", "Цена не может быть отрицательной");
            return "product";
        }

        logger.info("Save product method");
        productService.save(product);
        return "redirect:/product/list";
    }
}
