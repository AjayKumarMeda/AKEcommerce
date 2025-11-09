package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.User;
import com.ajlearn.akecommerce.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int addUser(User user) {
        // Encode password before saving so authentication (which expects BCrypt) works
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }
}
