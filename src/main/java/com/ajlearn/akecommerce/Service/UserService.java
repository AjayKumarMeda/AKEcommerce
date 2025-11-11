package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.DAO.UserInfoUpdate;
import com.ajlearn.akecommerce.Modal.User;
import com.ajlearn.akecommerce.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String generateJwtToken(Map<String, String> data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.get("username"),data.get("password"))
        );
        if (authentication.isAuthenticated()){
            String jwtToken = jwtService.generateToken(data.get("username"));
            repo.updateLastLogin(data.get("username"));
            return jwtToken;
        }
        else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

    public int addUser(User user) {
        // Encode password before saving so authentication (which expects BCrypt) works
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public int updateUser(String username, UserInfoUpdate user) {
        return repo.updateUserInfo(username,user);
    }

    public int deleteUser(String username) {
        return repo.deleteUser(username);
    }
}
