package com.ajlearn.akecommerce.Modal.Product;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Product {

    private Long id;

    private String name;
    private String description;
    private String brand;

    private double price;
    private int stockQuantity;

    private int categoryId;
    private String imageUrl;
    private String status;


}
