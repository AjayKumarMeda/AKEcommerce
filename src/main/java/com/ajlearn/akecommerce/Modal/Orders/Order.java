package com.ajlearn.akecommerce.Modal.Orders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
public class Order {

    private Long id;
    private Long userId;
    private Long addressId;
    private BigDecimal totalAmount;
    private String orderStatus;     // PENDING, CONFIRMED, CANCELLED, DELIVERED
    private String paymentStatus;   // NOT_PAID, PAID

}
