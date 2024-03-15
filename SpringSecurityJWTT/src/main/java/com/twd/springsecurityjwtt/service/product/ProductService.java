package com.twd.springsecurityjwtt.service.product;

import com.twd.springsecurityjwtt.entity.Product;
import com.twd.springsecurityjwtt.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    public List<Product> searchByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return  productRepository.findAll(pageable);
    }
}