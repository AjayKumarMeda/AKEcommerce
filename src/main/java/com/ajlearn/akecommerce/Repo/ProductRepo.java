package com.ajlearn.akecommerce.Repo;

import com.ajlearn.akecommerce.Modal.ProductModel.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    public Product findProductById(int id) {
        String sql = "select * from products where id=:id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id",id);
        return namedParameterJdbcTemplate.queryForObject(sql,param,new BeanPropertyRowMapper<>(Product.class));
    }

    public int create(Product product){
        String sql = "insert into products (" +
                "name,description,brand,price,stock_quantity,category_id,image_url) values (" +
                ":name,:description,:brand,:price,:stockQuantity,:categoryId,:imageUrl)";
        return namedParameterJdbcTemplate.update(sql,new BeanPropertySqlParameterSource(product));
    }

    public int update(Product product) {
        String sql = "update products set " +
                "name=:name,description=:description,brand=:brand,price=:price,stock_quantity=:stockQuantity," +
                "category_id=:categoryId,image_url=:imageUrl,status=:status where id =:id";

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id",product.getId())
                .addValue("name",product.getName())
                .addValue("description",product.getDescription())
                .addValue("brand",product.getBrand())
                .addValue("price",product.getPrice())
                .addValue("stockQuantity",product.getStockQuantity())
                .addValue("categoryId",product.getCategoryId())
                .addValue("imageUrl",product.getImageUrl())
                .addValue("status",product.getStatus());

        return namedParameterJdbcTemplate.update(sql,param);
    }
}
