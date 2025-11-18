package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.CartDAO.Cart;
import com.ajlearn.akecommerce.Modal.UserModel.UserPrincipal;
import com.ajlearn.akecommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(Authentication authentication,@RequestBody Cart cart){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        int row = cartService.addProductToCart(principal.getId(),principal.getUsername(),cart);
        if (row > 0)
            return cartService.getUserCart(principal.getId(), cart.getProductId());
        else
            throw new RuntimeException("Failed to add product to cart");
    }

    @PostMapping("/remove")
    public Cart removeFromCart(Authentication authentication, @RequestBody Cart cart){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return null;
    }

}
