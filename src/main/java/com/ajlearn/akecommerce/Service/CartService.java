package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.CartDAO.Cart;
import com.ajlearn.akecommerce.Repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    public Cart getUserCart(Long id,Long pid) {
        return cartRepo.getCart(id,pid);
    }

    public int addProductToCart(Long id, String username, Cart cart) {
        return cartRepo.addToCart(id,username,cart);
    }
}
