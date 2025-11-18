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
import java.util.Set;

@Repository
public class ProductRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Product> findAllProducts() {
        String sql = "select * from products";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public Product findProductById(int id) {
        String sql = "select * from products where id=:id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, param, new BeanPropertyRowMapper<>(Product.class));
    }

    public int create(Product product) {
        String sql = "insert into products (" +
                "name,description,brand,price,stock_quantity,category_id,image_url) values (" +
                ":name,:description,:brand,:price,:stockQuantity,:categoryId,:imageUrl)";
        return namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(product));
    }

    public int update(Product product) {
        String sql = "update products set " +
                "name=:name,description=:description,brand=:brand,price=:price,stock_quantity=:stockQuantity," +
                "category_id=:categoryId,image_url=:imageUrl,status=:status where id =:id";

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", product.getId())
                .addValue("name", product.getName())
                .addValue("description", product.getDescription())
                .addValue("brand", product.getBrand())
                .addValue("price", product.getPrice())
                .addValue("stockQuantity", product.getStockQuantity())
                .addValue("categoryId", product.getCategoryId())
                .addValue("imageUrl", product.getImageUrl())
                .addValue("status", product.getStatus());

        return namedParameterJdbcTemplate.update(sql, param);
    }

    public int deleteById(int id) {
        String sql = "delete from products where id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    public List<Product> search(String keyword) {
        String sql = "select * from products where LOWER(name) LIKE :keyword " +
                "OR LOWER(description) LIKE :keyword";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("keyword", "%" + keyword.toLowerCase() + "%");
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Product.class));
    }

    //query for sorting the products
    private static final Set<String> ALLOWED_SORT_COLUMNS = Set.of(
            "name",
            "brand",
            "price",
            "stock_quantity",
            "created_at"
    );

    public List<Product> sort(String sortBy, String order) {
        if (!ALLOWED_SORT_COLUMNS.contains(sortBy)) {
            sortBy = "name";
        }

        order = order.equalsIgnoreCase("desc") ? "DESC" : "ASC";
        String sql = "select * from products order by "+ sortBy +" " + order;

        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Product.class));
    }

    public List<Product> fliter(String fkeyword) {
        String sql = "select p.* from products p " +
                "join categories c on p.category_id = c.id where LOWER(c.name) = LOWER(:fkeyword)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("fkeyword",fkeyword);

        return namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Product.class));
    }
}
