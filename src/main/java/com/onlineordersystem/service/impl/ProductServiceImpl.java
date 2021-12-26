package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.domain.Product;
import com.onlineordersystem.domain.specification.ProductSpecification;
import com.onlineordersystem.error.ProductError;
import com.onlineordersystem.model.ProductCreateRequestDTO;
import com.onlineordersystem.model.ProductDTO;
import com.onlineordersystem.model.ProductDeleteRequestDTO;
import com.onlineordersystem.model.ProductSearchRequestDTO;
import com.onlineordersystem.model.ProductSearchResultDTO;
import com.onlineordersystem.model.ProductUpdateRequestDTO;
import com.onlineordersystem.repository.ProductRepository;
import com.onlineordersystem.service.ProductService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.mapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Transactional
    @Override
    public UUID createProduct(final ProductCreateRequestDTO productCreateRequestDTO) {
        Product newProduct = new Product();
        mapper.map(productCreateRequestDTO, newProduct);
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct.getId();
    }

    @Transactional
    @Override
    public UUID updateProduct(final ProductUpdateRequestDTO productUpdateRequestDTO) {
        Product product = productRepository.findById(productUpdateRequestDTO.getId()).orElseThrow(() -> new OosRuntimeException(ProductError.NOT_FOUND));
        log.debug("Before product update: {}", product);
        mapper.map(productUpdateRequestDTO, product);
        log.debug("After product update: {}", product);
        return product.getId();
    }

    @Transactional
    @Override
    public void deleteProduct(final ProductDeleteRequestDTO productDeleteRequestDTO) {
        productRepository.deleteById(productDeleteRequestDTO.getId());
        log.warn("Product {} has been deleted.", productDeleteRequestDTO.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductSearchResultDTO searchProducts(ProductSearchRequestDTO productSearchRequestDTO) {
        Integer pageSize = Optional.ofNullable(productSearchRequestDTO.getPageSize()).orElse(10);
        Integer pageIndex = Optional.ofNullable(productSearchRequestDTO.getPageIndex()).orElse(0);
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<Product> foundProducts = productRepository.findAll(new ProductSpecification(productSearchRequestDTO), pageRequest);
        List<ProductDTO> products = foundProducts.map(product -> mapper.map(product, ProductDTO.class)).toList();

        return ProductSearchResultDTO.builder()
            .products(products)
            .totalElements(foundProducts.getTotalElements())
            .totalPages(foundProducts.getTotalPages())
            .currentPage(foundProducts.getNumber())
            .pageSize(foundProducts.getSize())
            .build();
    }

    @Transactional
    @Override
    public Product dropOutOfStock(UUID productId, Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new OosRuntimeException(ProductError.MUST_REQUEST_AT_LEAST_ONE_PRODUCT);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new OosRuntimeException(ProductError.NOT_FOUND));

        int remainingQuantity = product.getQuantity() - quantity;
        if (remainingQuantity < 0) {
            throw new OosRuntimeException(ProductError.NOT_ENOUGH_STOCK);
        }
        product.setQuantity(remainingQuantity);
        return product;
    }

    @Transactional
    @Override
    public void returnToStock(UUID productId, int quantity) {
        if (quantity < 1) {
            throw new OosRuntimeException(ProductError.MUST_REQUEST_AT_LEAST_ONE_PRODUCT);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new OosRuntimeException(ProductError.NOT_FOUND));
        int regainedQuantity = product.getQuantity() + quantity;
        product.setQuantity(regainedQuantity);
    }
}
