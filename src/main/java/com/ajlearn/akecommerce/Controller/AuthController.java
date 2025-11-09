package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.User;
import com.ajlearn.akecommerce.Service.JwtService;
import com.ajlearn.akecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> data){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.get("username"),data.get("password"))
        );
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(data.get("username"));
        }
        else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        int rowUpdated = userService.addUser(user);
        if (rowUpdated>0)
            return "User signup successfully";
        else
            return "Something went wrong";
    }

}
