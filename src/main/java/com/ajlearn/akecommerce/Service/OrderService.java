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

    List<Map<String, Object>> cartItems;
    int updated;

    @Transactional
    public OrderResponse placeOrder(Long id, Long addressId) {
        //Get Cart Items
        cartItems = orderRepo.getCartItems(id);
        if (cartItems.isEmpty())
            throw new RuntimeException("Cart is Empty...");

        //Calculate the Total Amount
        BigDecimal total = BigDecimal.ZERO;
        for (Map<String, Object> item : cartItems) {
            BigDecimal price = (BigDecimal) item.get("price");
            int qty = (int) item.get("quantity");
            total = total.add(price.multiply(BigDecimal.valueOf(qty)));
        }

        //Insert the Order
        Long orderId = orderRepo.addOrder(id, addressId, total);
        //Insert the OrderItems
        for (Map<String, Object> items : cartItems) {
            updated = orderRepo.addOrderItems(orderId, items);
            if (updated == 0)
                throw new RuntimeException("Not enough stock!");
        }

        //clear cart
        int deleted = orderRepo.deleteCart(id);
        if (deleted == 0)
            throw new RuntimeException("Unable to delete the Cart");

        return orderRepo.createOrder(orderId, total, cartItems);
    }

    public List<Order> findAll(Long id) {
        return orderRepo.find(id);
    }

    @Transactional
    public OrderResponse cancelOrderById(Long id, Long orderId) {
        //Get Order Details
        Map<String, Object> order = orderRepo.findOrderById(orderId);

        //Check if order belongs to user
        if (!order.get("order_status").equals("PENDING"))
            throw new RuntimeException("Order can't be cancelled at this stage.");
        //Check the status
        if (!order.get("user_id").equals(id))
            throw new RuntimeException("Not Your Order.");

        //Get Items for stack restore
        cartItems = orderRepo.getOrderItems(orderId);
        if (cartItems.isEmpty())
            throw new RuntimeException("order items are Empty...");
        //Restore Stacks
        for (Map<String, Object> item : cartItems) {
            updated = orderRepo.restore(orderId,item);
            if (updated == 0)
                throw new RuntimeException("unable restore stocks!");
        }

        //Update the Order status
        updated = orderRepo.statusChange(orderId);
        if (updated == 0)
            throw new RuntimeException("Unable to cancel the order.");

        return orderRepo.cancel(orderId,(BigDecimal) order.get("total_amount"),cartItems);
    }
}
