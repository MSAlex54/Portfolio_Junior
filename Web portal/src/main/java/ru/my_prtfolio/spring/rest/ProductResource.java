package ru.my_prtfolio.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.my_prtfolio.spring.persist.entity.Product;
import ru.my_prtfolio.spring.service.ProductService;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    private ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path="/all", produces = "application/json")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping(path = "/{id}/id")
    public Product findById(@PathVariable("id") long id){
        return productService.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public void createProduct (@RequestBody Product product){
        if (product.getId()!=null){
            throw new IllegalArgumentException("Id field in create request");
        }
        productService.save(product);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser (@RequestBody Product product){
        productService.save(product);
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteProduct (@PathVariable ("id") long id){
        productService.delete(id);
    }


    @ExceptionHandler
    public ResponseEntity<illegalArgumentErrorResponse> illegalArgumentExceptionHandler (IllegalArgumentException exc) {
        illegalArgumentErrorResponse response = new illegalArgumentErrorResponse(HttpStatus.BAD_REQUEST.value(),exc.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<NotFoundErrorResponse> notFoundExceptionHandler (NotFoundException exc) {
        NotFoundErrorResponse response = new NotFoundErrorResponse(HttpStatus.NOT_FOUND.value(),Product.class.getSimpleName() + " object not found",System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
