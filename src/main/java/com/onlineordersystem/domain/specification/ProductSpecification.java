package com.onlineordersystem.domain.specification;

import com.onlineordersystem.domain.Product;
import com.onlineordersystem.domain.Product.Fields;
import com.onlineordersystem.domain.base.SearchSpecification;
import com.onlineordersystem.model.ProductSearchRequestDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification implements SearchSpecification<Product> {

    private final ProductSearchRequestDTO searchRequestDTO;

    public ProductSpecification(ProductSearchRequestDTO searchRequestDTO) {
        this.searchRequestDTO = searchRequestDTO;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchRequestDTO.getName() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Fields.name)), likeClause(searchRequestDTO.getName())));
        }
        if (searchRequestDTO.getDescription() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Fields.description)), likeClause(searchRequestDTO.getDescription())));
        }
        if (searchRequestDTO.getQuantity() != null) {
            predicates.add(criteriaBuilder.equal(root.get(Fields.quantity), searchRequestDTO.getQuantity()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
