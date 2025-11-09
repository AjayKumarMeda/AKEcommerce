package com.ajlearn.akecommerce;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/AKEcommerce")
    public String greet(){
        return "Hello Aj, Welcome to Ak E-commerce Application.";
    }
}
