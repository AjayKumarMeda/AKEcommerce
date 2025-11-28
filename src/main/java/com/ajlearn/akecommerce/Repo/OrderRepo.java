package com.ajlearn.akecommerce.Repo;

import com.ajlearn.akecommerce.Modal.Orders.Order;
import com.ajlearn.akecommerce.Modal.Orders.OrderResponse;
import io.jsonwebtoken.lang.Maps;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class OrderRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Order> find(Long id) {
        String sql = "select * from orders where user_id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", id);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Order.class));
    }

    public List<Map<String, Object>> getCartItems(Long id) {
        String cartSql = "select c.product_id, c.quantity, p.price " +
                "from usercart c join products p on c.product_id = p.id " +
                "where c.user_id = :userId";
        MapSqlParameterSource cartParams = new MapSqlParameterSource()
                .addValue("userId", id);
        return namedParameterJdbcTemplate.queryForList(cartSql, cartParams);
    }

    public Long addOrder(Long id, Long addressId, BigDecimal total) {
        String orderSql = "insert into orders (user_id, address_id, total_amount) " +
                "values (:userId, :addressId, :total) Returning id";
        MapSqlParameterSource orderParams = new MapSqlParameterSource()
                .addValue("userId", id)
                .addValue("addressId", addressId)
                .addValue("total", total);
        return namedParameterJdbcTemplate
                .queryForObject(orderSql, orderParams, Long.class);
    }

    public int addOrderItems(Long orderId, Map<String, Object> items) {
        String itemSql = "insert into order_items (order_id, product_id, quantity, price_at_purchase) " +
                "values (:orderId, :productId, :quantity, :price)";
        //While insect order items, we should reduce the Stock Quantity of each Product
        String stockSql = "update products set stock_quantity = stock_quantity - :quantity " +
                "where id = :productId and stock_quantity >= :quantity";
        MapSqlParameterSource itemParams = new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("productId", items.get("product_id"))
                .addValue("quantity", items.get("quantity"))
                .addValue("price", items.get("price"));
        namedParameterJdbcTemplate.update(itemSql, itemParams);
        return namedParameterJdbcTemplate.update(stockSql, itemParams);
    }

    public int deleteCart(Long id) {
        String delSql = "delete from usercart where user_id=:userId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", id);
        return namedParameterJdbcTemplate.update(delSql, params);
    }

    public OrderResponse displayOrder(String msg,Long orderId, BigDecimal total,
                                     List<Map<String, Object>> cartItems) {
        return new OrderResponse(
                msg,
                orderId,
                total,
                cartItems
        );
    }

    public Map<String, Object> findOrderById(Long orderId) {
        String sql = "select user_id,order_status,total_amount from orders where id = :orderId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orderId", orderId);
        return namedParameterJdbcTemplate.queryForMap(sql, params);
    }

    public List<Map<String, Object>> getOrderItems(Long orderId) {
        String sql = "select product_id,quantity,status from order_items where order_id = :orderId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orderId", orderId);
        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    public int restore(Long orderId, Map<String, Object> item) {
        String productsSql = "update products set stock_quantity = stock_quantity + :quantity " +
                "where id = :product_id";
        String itemSql = "update order_items set status = 'CANCELLED' " +
                "where order_id = :orderId and product_id = :product_id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("quantity", item.get("quantity"))
                .addValue("product_id", item.get("product_id"))
                .addValue("orderId",orderId);
        namedParameterJdbcTemplate.update(productsSql,params);
        return namedParameterJdbcTemplate.update(itemSql, params);
    }

    public int statusChange(Long orderId) {
        String sql = "update orders set order_status = 'CANCELLED' where id = :orderId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("orderId",orderId);
        return namedParameterJdbcTemplate.update(sql,params);
    }

    public int updateStatus(Long id, Long userId, Long orderId, String newStatus, String oldStatus) {
        String orderSql = "update orders set order_status = :newStatus " +
                "where user_id = :userId and id = :orderId";
        String itemSql = "update order_items set status = :newStatus " +
                "where order_id = :orderId";
        String historySql = "insert into order_status_history (order_id, old_status, new_status, changed_by_id) " +
                "values (:orderId, :oldStatus, :newStatus, :changedUserId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("changedUserId",id)
                .addValue("userId",userId)
                .addValue("orderId",orderId)
                .addValue("newStatus",newStatus)
                .addValue("oldStatus",oldStatus);
        namedParameterJdbcTemplate.update(orderSql,params);
        namedParameterJdbcTemplate.update(itemSql,params);
        return namedParameterJdbcTemplate.update(historySql,params);
    }

    public Long checkUser(Long userId) {
        String sql = "select id from users where id = :userId ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId",userId);
        return namedParameterJdbcTemplate.queryForObject(sql,params,Long.class);
    }
}
