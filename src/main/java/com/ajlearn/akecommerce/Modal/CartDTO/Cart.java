package com.ajlearn.akecommerce.Modal.CartDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Cart {

//    private Long id;
//    private Long userId;
//    private String username;
    private Long productId;
    private int quantity;
}
