package com.onlineordersystem.repository;

import com.onlineordersystem.domain.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

}
