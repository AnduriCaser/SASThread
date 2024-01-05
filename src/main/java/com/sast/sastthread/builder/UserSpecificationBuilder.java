package com.sast.sastthread.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sast.sastthread.criteria.SearchCriteria;
import com.sast.sastthread.enums.SearchOperation;
import com.sast.sastthread.model.User;
import com.sast.sastthread.specification.UserSpecification;

public class UserSpecificationBuilder {
    private final List<SearchCriteria> params;

    public UserSpecificationBuilder(){
        this.params = new ArrayList<>();
    }

    public final UserSpecificationBuilder with(String key,
            String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final UserSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<User> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification<User> result = new UserSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria
                    .getDataOption()) == SearchOperation.ALL
                            ? Specification.where(result).and(new UserSpecification(criteria))
                            : Specification.where(result).or(
                                    new UserSpecification(criteria));
        }
        return result;
    }
}
