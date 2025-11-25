package com.ajlearn.akecommerce.Modal.Orders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class OrderItemsDTO {

    private Long productId;
    private String name;
    private int quantity;
    private Double price;
}
