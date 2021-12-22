package com.onlineordersystem.repository;

import com.onlineordersystem.domain.base.Principle;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipleRepository extends CrudRepository<Principle, UUID> {

    Optional<Principle> findByEmail(String username);

    boolean existsByEmail(String username);
}