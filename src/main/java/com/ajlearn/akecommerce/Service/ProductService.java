package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.Product.Product;
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

    public int deleteProductById(int id) {
        return productRepo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.search(keyword);
    }

    public List<Product> sortProducts(String sortBy, String order) {
        return productRepo.sort(sortBy,order);
    }

    public List<Product> getProductsByFilter(String fkeyword) {
        return productRepo.fliter(fkeyword);
    }
}
