package com.onlineordersystem.controller.seller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/seller/product")
public class ProductController {

    @PostMapping
    public void createProduct() {
    }

    @PutMapping
    public void updateProduct() {

    }

    @DeleteMapping
    public void deleteProduct() {

    }
}
