package com.twd.springsecurityjwtt.repository;

import com.twd.springsecurityjwtt.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryIgnoreCase(String category);
    List<Product> findByNameContainingIgnoreCase(String name);


}
