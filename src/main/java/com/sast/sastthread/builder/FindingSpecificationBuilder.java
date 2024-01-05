package com.sast.sastthread.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sast.sastthread.criteria.SearchCriteria;
import com.sast.sastthread.enums.SearchOperation;
import com.sast.sastthread.model.Finding;
import com.sast.sastthread.specification.FindingSpecification;

public class FindingSpecificationBuilder {
    private final List<SearchCriteria> params;

    public FindingSpecificationBuilder(){
        this.params = new ArrayList<>();
    }

    public final FindingSpecificationBuilder with(String key,
            String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final FindingSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Finding> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<Finding> result = new FindingSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria
                    .getDataOption()) == SearchOperation.ALL
                            ? Specification.where(result).and(new FindingSpecification(criteria))
                            : Specification.where(result).or(
                                    new FindingSpecification(criteria));
        }
        return result;
    }
}
