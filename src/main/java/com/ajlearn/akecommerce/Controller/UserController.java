package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.UserDAO.LoginRequest;
import com.ajlearn.akecommerce.Modal.UserDAO.UserInfoUpdate;
import com.ajlearn.akecommerce.Modal.UserModel.User;
import com.ajlearn.akecommerce.Modal.UserModel.UserPrincipal;
import com.ajlearn.akecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestBody LoginRequest data){
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

    @PutMapping("/update")
    public String update(Authentication authentication, @RequestBody UserInfoUpdate updateduser) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = userService.updateUser(principal.getUsername(), updateduser);
        if (rows >= 1)
            return principal.getUsername() + " User updated successfully";
        else
            return "Something went wrong, Not updated anything please again.";
    }

    @DeleteMapping("/delete")
    public String delete(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = userService.deleteUser(principal.getUsername());
        if(rows >=1)
            return "User deleted successfully";
        else
            return "Something went wrong, please try again.";
    }

    @GetMapping("/allusers")
    public List<User> findAllUsers(){
//        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//        String role = principal.getAuthorities()
//                .stream()
//                .findFirst()
//                .map(GrantedAuthority::getAuthority)
//                .orElse("USER");
        List<User> users = userService.getAllUsers();
        return users;
    }
}
