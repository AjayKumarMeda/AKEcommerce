package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.DAO.UserInfoUpdate;
import com.ajlearn.akecommerce.Modal.User;
import com.ajlearn.akecommerce.Service.JwtService;
import com.ajlearn.akecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> data){
        return userService.generateJwtToken(data);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        int rowUpdated = userService.addUser(user);
        if (rowUpdated>0)
            return user.getUsername()+ " User signup successfully";
        else
            return "Something went wrong";
    }

    @PostMapping("/update")
    public String update(@RequestHeader("Authorization") String token, @RequestBody UserInfoUpdate updateduser) {

        String username = jwtService.extractUsername(token.substring(7));
        int rows = userService.updateUser(username, updateduser);
        if (rows >= 1)
            return username + " User updated successfully";
        else
            return "Something went wrong, Not updated anything please again.";
    }


}
