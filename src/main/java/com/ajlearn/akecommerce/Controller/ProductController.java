package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.ProductModel.Product;
import com.ajlearn.akecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/allproducts")
    public List<Product> findAllProducts(){
        return productService.getAllProduct();
    }
}
