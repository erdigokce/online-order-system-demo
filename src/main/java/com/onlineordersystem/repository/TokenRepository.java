package com.onlineordersystem.repository;

import com.onlineordersystem.domain.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository // Later it will be changed to a redisson repository
public interface TokenRepository extends CrudRepository<Token, String> {

}
