package com.ajlearn.akecommerce.Modal.Orders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrderResponse {

    private String message;
    private Long orderId;
    private BigDecimal totalAmount;
    private List<Map<String, Object>> items;

    public OrderResponse(String message, Long orderId,
                         BigDecimal totalAmount, List<Map<String, Object>> items) {
        this.message = message;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.items = items;
    }
}
