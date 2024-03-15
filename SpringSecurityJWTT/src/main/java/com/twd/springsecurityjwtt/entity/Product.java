package com.twd.springsecurityjwtt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;
    private String price ;
    private String description;
    private String Availability;

}
