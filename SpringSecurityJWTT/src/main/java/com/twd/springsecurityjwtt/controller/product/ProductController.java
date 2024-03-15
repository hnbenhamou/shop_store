package com.twd.springsecurityjwtt.controller.product;

import com.twd.springsecurityjwtt.entity.Product;
import com.twd.springsecurityjwtt.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/adminuser")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getproducts")
    public List<Product> getProduct(Pageable pageable) {
        System.out.println(pageable);

    return productService.getAllProducts(pageable).getContent();
    }
    @PostMapping("/products/searchByCategory")
    public List<Product> searchByCategory(@RequestParam String category) {
        return productService.searchByCategory(category);
    }
    @GetMapping("/products/searchByName")
    public List<Product> searchByName(@RequestParam String name) {
        return productService.searchByName(name);
    }
}
