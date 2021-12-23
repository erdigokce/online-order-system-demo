package com.onlineordersystem.controller.seller;

import com.onlineordersystem.model.ProductCreateRequestDTO;
import com.onlineordersystem.model.ProductCreateResultDTO;
import com.onlineordersystem.model.ProductDeleteRequestDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@PreAuthorize("hasAuthority('SELLER')")
@RestController
@RequestMapping("/seller/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductCreateResultDTO> createProduct(@Valid @RequestBody ProductCreateRequestDTO productCreateRequestDTO) {
        UUID productId = productService.createProduct(productCreateRequestDTO);
        return ResponseEntity.created(URI.create("/seller/product/" + productId)).body(new ProductCreateResultDTO(productId));
    }

    @PutMapping
    public ResponseEntity<ProductUpdateResultDTO> updateProduct(@Valid @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO) {
        UUID productId = productService.updateProduct(productUpdateRequestDTO);
        return ResponseEntity.ok(new ProductUpdateResultDTO(productId));
    }

    @DeleteMapping
    public void deleteProduct(@Valid @RequestBody ProductDeleteRequestDTO productDeleteRequestDTO) {
        productService.deleteProduct(productDeleteRequestDTO);
    }
}
