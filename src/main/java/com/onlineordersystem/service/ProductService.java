package com.onlineordersystem.service;

import com.onlineordersystem.model.ProductCreateRequestDTO;
import com.onlineordersystem.model.ProductDeleteRequestDTO;
import com.onlineordersystem.model.ProductUpdateRequestDTO;
import java.util.UUID;

public interface ProductService {

    UUID createProduct(ProductCreateRequestDTO productCreateRequestDTO);

    UUID updateProduct(ProductUpdateRequestDTO productUpdateRequestDTO);

    void deleteProduct(ProductDeleteRequestDTO productDeleteRequestDTO);
}
