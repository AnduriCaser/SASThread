package com.sast.sastthread.specification;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.sast.sastthread.criteria.SearchCriteria;
import com.sast.sastthread.enums.SearchOperation;
import com.sast.sastthread.model.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {

    private final SearchCriteria searchCriteria;

    public ProductSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Product> root,
            CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue()
                .toString().toLowerCase();

        switch (Objects.requireNonNull(
                SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS:
                if ("deptName".equals(searchCriteria.getFilterKey())) {
                    return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

            case DOES_NOT_CONTAIN:
                if ("deptName".equals(searchCriteria.getFilterKey())) {
                    return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

            default:
                throw new IllegalArgumentException("Unsupported operation: " + searchCriteria.getOperation());
        }
    }
}
