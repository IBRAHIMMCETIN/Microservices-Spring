package com.turkcell.cart_service.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/carts")
public class CartController {

    @GetMapping("")
    public String sayHello(){
        return "Hello from Cart Service";
    }
}
