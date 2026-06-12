package com.turkcell.product_services.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @GetMapping()
    public String hello() {
        return "Hello";
    }
    
}