package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.ProductModel.Product;
import com.ajlearn.akecommerce.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProduct() {
        return productRepo.findAllProducts();
    }

    public Product getProductById(int id) {
        return productRepo.findProductById(id);
    }

    public int creation(Product product) {
        return productRepo.create(product);
    }

    public int update(Product product) {
        return productRepo.update(product);
    }
}
