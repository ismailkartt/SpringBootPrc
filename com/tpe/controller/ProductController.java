package com.tpe.controller;

import com.tpe.domain.Product;
import com.tpe.dto.ProductDTO;
import com.tpe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //1-product oluşturma/kaydetme->http://localhost:8080/products/save
    @PostMapping("/save")
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDTO productDTO){
        productService.saveProduct(productDTO);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);//201
    }
    //ödevler:
    //2-Tüm productları getirme->http://localhost:8080/products

    @GetMapping//response entity
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList =productService.getAllProducts();
        return ResponseEntity.ok(productList);//200:OK
    }


    //3-Id ile product getirme->http://localhost:8080/products/5
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product =productService.getProductById(id);
        return ResponseEntity.ok(product);//200:OK
    }

}
