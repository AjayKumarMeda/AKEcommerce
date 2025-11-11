package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.DAO.UserInfoUpdate;
import com.ajlearn.akecommerce.Modal.User;
import com.ajlearn.akecommerce.Modal.UserPrincipal;
import com.ajlearn.akecommerce.Service.JwtService;
import com.ajlearn.akecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

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
    public String update(Authentication authentication, @RequestBody UserInfoUpdate updateduser) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = userService.updateUser(principal.getUsername(), updateduser);
        if (rows >= 1)
            return principal.getUsername() + " User updated successfully";
        else
            return "Something went wrong, Not updated anything please again.";
    }

    @PostMapping("/delete")
    public String delete(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = userService.deleteUser(principal.getUsername());
        if(rows >=1)
            return "User deleted successfully";
        else
            return "Something went wrong, please try again.";
    }

}
