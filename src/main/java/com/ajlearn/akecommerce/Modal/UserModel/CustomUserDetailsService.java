package com.ajlearn.akecommerce.Modal.UserModel;

import com.ajlearn.akecommerce.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        if (user == null){
            System.out.println("User 404");
            throw new UsernameNotFoundException("Invalid User");
        }
//        System.out.println(user.getRole());
        return new UserPrincipal(user.getId(),user.getUsername(), user.getPassword(), "ROLE_"+user.getRole());
    }
}
