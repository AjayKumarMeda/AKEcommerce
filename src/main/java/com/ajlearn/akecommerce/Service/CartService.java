package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.CartDTO.Cart;
import com.ajlearn.akecommerce.Repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    public List<Cart> findUserCart(Long id) {
        return cartRepo.findCart(id);
    }

    public Cart getUserCart(Long id,Long pid) {
        return cartRepo.getCart(id,pid);
    }

    public int addProductToCart(Long id, String username, Cart cart) {
        return cartRepo.addToCart(id,username,cart);
    }

    public int removeProduct(Long id, Cart cart) {
        return cartRepo.remove(id,cart);
    }

    public int clearCart(Long id) {
        return cartRepo.clear(id);
    }
}
