package com.sast.sastthread.dto;

import java.util.List;

import com.sast.sastthread.criteria.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDto {
    private List<SearchCriteria> productSearchCriteriaList;
    private String dataOption;
}
