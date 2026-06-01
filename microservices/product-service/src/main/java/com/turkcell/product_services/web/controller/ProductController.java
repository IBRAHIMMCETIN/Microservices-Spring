package com.turkcell.product_services.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @GetMapping("")
    public String sayHello(){
        return "Hello from Product Service";
    }
}