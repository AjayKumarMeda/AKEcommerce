package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private JwtService jwtService;

    @GetMapping("/akecommercecheck")
    public String greet(@RequestHeader("Authorization") String token){
        String username = jwtService.extractUsername(token.substring(7));
        return "Hello "+username +", Welcome to Ak E-commerce Application.";
    }

    @GetMapping("/akecommerce")
    public String AKEcommercePublic(){
        return "please Login to get more Info about Ak E-commerce Application.";
    }
}
