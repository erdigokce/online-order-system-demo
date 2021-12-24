package com.onlineordersystem.controller;

import com.onlineordersystem.model.ProductCreateRequestDTO;
import com.onlineordersystem.model.ProductCreateResultDTO;
import com.onlineordersystem.model.ProductDeleteRequestDTO;
import com.onlineordersystem.model.ProductSearchRequestDTO;
import com.onlineordersystem.model.ProductSearchResultDTO;
import com.onlineordersystem.model.ProductUpdateRequestDTO;
import com.onlineordersystem.model.ProductUpdateResultDTO;
import com.onlineordersystem.service.ProductService;
import java.net.URI;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping
    public ResponseEntity<ProductCreateResultDTO> createProduct(@Valid @RequestBody ProductCreateRequestDTO productCreateRequestDTO) {
        UUID productId = productService.createProduct(productCreateRequestDTO);
        return ResponseEntity.created(URI.create("/seller/product/" + productId)).body(new ProductCreateResultDTO(productId));
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping
    public ResponseEntity<ProductUpdateResultDTO> updateProduct(@Valid @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO) {
        UUID productId = productService.updateProduct(productUpdateRequestDTO);
        return ResponseEntity.ok(new ProductUpdateResultDTO(productId));
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping
    public void deleteProduct(@Valid @RequestBody ProductDeleteRequestDTO productDeleteRequestDTO) {
        productService.deleteProduct(productDeleteRequestDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<ProductSearchResultDTO> searchProduct(@Valid @RequestBody ProductSearchRequestDTO productSearchRequestDTO) {
        return ResponseEntity.ok(productService.searchProducts(productSearchRequestDTO));
    }
}
