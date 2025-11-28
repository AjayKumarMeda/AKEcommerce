package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.Orders.Order;
import com.ajlearn.akecommerce.Modal.Orders.OrderResponse;
import com.ajlearn.akecommerce.Modal.User.User;
import com.ajlearn.akecommerce.Modal.User.UserPrincipal;
import com.ajlearn.akecommerce.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
public class  OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public OrderResponse checkout(Authentication authentication, @RequestParam Long addressId){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return orderService.placeOrder(principal.getId(),addressId);
    }

    @GetMapping("/userorders")
    public List<Order> getAllOrders(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return orderService.findAll(principal.getId());
    }

    @GetMapping("/orderDetails")
    public OrderResponse findByOrderId(Authentication authentication, @RequestParam Long orderId){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return orderService.getOrderDetails(principal.getId(),orderId);
    }

    @PutMapping("/cancel")
    public OrderResponse cancelOrder(Authentication authentication, @RequestParam Long orderId){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return orderService.cancelOrderById(principal.getId(),orderId);
    }

    @PutMapping("/update")
    public OrderResponse updateStatus(Authentication authentication, @RequestParam Long userId, Long orderId,String status){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return orderService.updateOrderStatus(principal.getId(),userId,orderId,status);
    }
}
