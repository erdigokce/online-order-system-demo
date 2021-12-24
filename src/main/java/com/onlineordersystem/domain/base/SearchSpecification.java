package com.onlineordersystem.domain.base;

import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

public interface SearchSpecification<T> extends Specification<T> {

    default String likeClause(String string) {
        return "%" + string.toLowerCase(Locale.ENGLISH) + "%";
    }
}
