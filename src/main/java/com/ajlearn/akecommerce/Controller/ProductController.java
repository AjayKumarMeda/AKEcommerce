package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.ProductModel.Product;
import com.ajlearn.akecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/findproduct")
    public Product findProductById(@RequestParam int id){
        return productService.getProductById(id);
    }

    @PostMapping("/create")
    public String productCreation(@RequestBody Product product){
        int row = productService.creation(product);
        if (row > 0)
            return "Product "+product.getName()+" successfully created...";
        else
            return "Something went wrong, please try again";
    }

    @PutMapping("/update")
    public String productUpdation(@RequestBody Product product){
        int row = productService.update(product);
        if (row > 0)
            return "Product "+product.getName()+" successfully Updated...";
        else
            return "Something went wrong, please try again";
    }

}
