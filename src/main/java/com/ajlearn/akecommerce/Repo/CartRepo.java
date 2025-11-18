package com.ajlearn.akecommerce.Repo;

import com.ajlearn.akecommerce.Modal.CartDAO.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CartRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Cart getCart(Long id,Long pid) {
        String sql = "select * from usercart where user_id = :id and product_id = :pid";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("pid",pid);
        return namedParameterJdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<>(Cart.class));
    }

    public int addToCart(Long id, String username, Cart cart) {
        String sql = "insert into usercart (user_id,username,product_id,quantity) " +
                "values (:id,:username,:product_id,:quantity) " +
                "on conflict (user_id,product_id) " +
                "do update set quantity = usercart.quantity + EXCLUDED.quantity";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("username",username)
                .addValue("product_id",cart.getProductId())
                .addValue("quantity",cart.getQuantity());

        return namedParameterJdbcTemplate.update(sql,params);
    }


}
