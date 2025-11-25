package com.ajlearn.akecommerce.Modal.Orders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
public class OrderItems {

    private Long id;
    private Long orderId;          // FK to orders(id)
    private Long productId;        // FK to products(id)
    private Integer quantity;      // must be > 0
    private BigDecimal priceAtPurchase; // must be >= 0

}
