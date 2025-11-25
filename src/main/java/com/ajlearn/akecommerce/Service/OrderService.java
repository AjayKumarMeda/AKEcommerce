package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.Orders.Order;
import com.ajlearn.akecommerce.Modal.Orders.OrderResponse;
import com.ajlearn.akecommerce.Repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Transactional
    public OrderResponse placeOrder(Long id, Long addressId) {
        //Get Cart Items
        List<Map<String,Object>> cartItems = orderRepo.getCartItems(id);
        if (cartItems.isEmpty())
            throw new RuntimeException("Cart is Empty...");

        //Calculate the Total Amount
        BigDecimal total = BigDecimal.ZERO;
        for (Map<String,Object> item : cartItems){
            BigDecimal price = (BigDecimal) item.get("price");
            int qty = (int) item.get("quantity");
            total = total.add(price.multiply(BigDecimal.valueOf(qty)));
        }

        //Insert the Order
        Long orderId = orderRepo.addOrder(id,addressId,total);
        for (Map<String,Object> items : cartItems) {
            int updated = orderRepo.addOrderItems(orderId, items);
            if (updated == 0)
                throw new RuntimeException("Not enough stock!");
        }

        //clear cart
        int deleted = orderRepo.deleteCart(id);
        if (deleted == 0)
            throw new RuntimeException("Unable to delete the Cart");

        return orderRepo.createOrder(orderId,total,cartItems);
    }

    public List<Order> findAll(Long id) {
        return orderRepo.find(id);
    }
}
