package com.onlineordersystem.service;

import com.onlineordersystem.domain.Product;
import com.onlineordersystem.model.ProductCreateRequestDTO;
import com.onlineordersystem.model.ProductDeleteRequestDTO;
import com.onlineordersystem.model.ProductSearchRequestDTO;
import com.onlineordersystem.model.ProductSearchResultDTO;
import com.onlineordersystem.model.ProductUpdateRequestDTO;
import java.util.UUID;

public interface ProductService {

    UUID createProduct(ProductCreateRequestDTO productCreateRequestDTO);

    UUID updateProduct(ProductUpdateRequestDTO productUpdateRequestDTO);

    void deleteProduct(ProductDeleteRequestDTO productDeleteRequestDTO);

    ProductSearchResultDTO searchProducts(ProductSearchRequestDTO productSearchRequestDTO);

    Product dropOutOfStock(UUID productId, Integer quantity);

    void returnToStock(UUID productId, int quantity);
}
