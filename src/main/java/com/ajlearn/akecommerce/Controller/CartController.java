package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.CartDAO.Cart;
import com.ajlearn.akecommerce.Modal.UserModel.User;
import com.ajlearn.akecommerce.Modal.UserModel.UserPrincipal;
import com.ajlearn.akecommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cartitems")
    public List<Cart> getUserCart(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return cartService.findUserCart(principal.getId());
    }

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

        int row = cartService.removeProduct(principal.getId(),cart);
        if (row > 0 ){
            if (row == 1)
                return cartService.getUserCart(principal.getId(), cart.getProductId());
            else
                return null;
        }
        else
            throw new RuntimeException("Failed to remove the product from cart");
    }

    @DeleteMapping("/clear")
    public String clearUsercart(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = cartService.clearCart(principal.getId());
        if (rows > 0)
            return "Cart is cleared, Empty now";
        else
            return "Faild to clear cart";
    }

}
