package com.sast.sastthread.dto;

import java.util.List;

import com.sast.sastthread.criteria.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto {
    private List<SearchCriteria> userSearchCriteriaList;
    private String dataOption;
}
