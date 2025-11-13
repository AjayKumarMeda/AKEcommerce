package com.ajlearn.akecommerce.Repo;

import com.ajlearn.akecommerce.Modal.ProductModel.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Product> findAllProducts() {
        String sql = "select * from products";
        return namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Product.class));
    }
}
