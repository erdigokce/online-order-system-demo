package com.onlineordersystem.repository;

import com.onlineordersystem.domain.Sellers;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends CrudRepository<Sellers, UUID> {

}