package com.sast.sastthread.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sast.sastthread.builder.FindingSpecificationBuilder;
import com.sast.sastthread.builder.ProductSpecificationBuilder;
import com.sast.sastthread.builder.UserSpecificationBuilder;
import com.sast.sastthread.criteria.SearchCriteria;
import com.sast.sastthread.dto.APIResponse;
import com.sast.sastthread.dto.FindingSearchDto;
import com.sast.sastthread.dto.ProductSearchDto;
import com.sast.sastthread.dto.UserSearchDto;
import com.sast.sastthread.model.Finding;
import com.sast.sastthread.model.Product;
import com.sast.sastthread.model.User;
import com.sast.sastthread.service.FindingService;
import com.sast.sastthread.service.ProductService;
import com.sast.sastthread.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/v1/search")
public class SearchController {
        private final UserService userService;
        private final ProductService productService;
        private final FindingService findingService;

        public SearchController(final UserService userService, final ProductService productService,
                        final FindingService findingService) {
                this.findingService = findingService;
                this.productService = productService;
                this.userService = userService;
        }

        @GetMapping("/products")
        public ResponseEntity<APIResponse> searchProducts(
                        @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                        @RequestBody ProductSearchDto productSearchDto) {
                APIResponse apiResponse = new APIResponse();
                ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
                List<SearchCriteria> criteriaList = productSearchDto.getProductSearchCriteriaList();
                if (criteriaList != null) {
                        criteriaList.forEach(x -> {
                                x.setDataOption(productSearchDto
                                                .getDataOption());
                                builder.with(x);
                        });
                }

                Pageable page = PageRequest.of(pageNum, pageSize,
                                Sort.by("empfirstNm")
                                                .ascending()
                                                .and(Sort.by("emplastNm"))
                                                .ascending()
                                                .and(Sort.by("department"))
                                                .ascending());

                Page<Product> employeePage = productService.findBySearchCriteria(builder.build(),
                                page);
                apiResponse.setData(employeePage.toList());
                apiResponse.setResponseCode(HttpStatus.OK);
                apiResponse.setMessage("Successfully retrieved product record");

                return new ResponseEntity<>(apiResponse,
                                apiResponse.getResponseCode());
        }

        @GetMapping("/findings")
        public ResponseEntity<APIResponse> searchFindings(
                        @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                        @RequestBody FindingSearchDto findingSearchDto) {
                APIResponse apiResponse = new APIResponse();
                FindingSpecificationBuilder builder = new FindingSpecificationBuilder();
                List<SearchCriteria> criteriaList = findingSearchDto.getFindingSearchCriteriaList();
                if (criteriaList != null) {
                        criteriaList.forEach(x -> {
                                x.setDataOption(findingSearchDto
                                                .getDataOption());
                                builder.with(x);
                        });
                }

                Pageable page = PageRequest.of(pageNum, pageSize,
                                Sort.by("empfirstNm")
                                                .ascending()
                                                .and(Sort.by("emplastNm"))
                                                .ascending()
                                                .and(Sort.by("department"))
                                                .ascending());

                Page<Finding> employeePage = findingService.findBySearchCriteria(builder.build(), page);
                apiResponse.setData(employeePage.toList());
                apiResponse.setResponseCode(HttpStatus.OK);
                apiResponse.setMessage("Successfully retrieved finding record");

                return new ResponseEntity<>(apiResponse,
                                apiResponse.getResponseCode());
        }

        @GetMapping("/users")
        public ResponseEntity<APIResponse> searchUsers(
                        @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                        @RequestBody UserSearchDto userSearchDto) {
                APIResponse apiResponse = new APIResponse();
                UserSpecificationBuilder builder = new UserSpecificationBuilder();
                List<SearchCriteria> criteriaList = userSearchDto.getUserSearchCriteriaList();
                if (criteriaList != null) {
                        criteriaList.forEach(x -> {
                                x.setDataOption(userSearchDto
                                                .getDataOption());
                                builder.with(x);
                        });
                }

                Pageable page = PageRequest.of(pageNum, pageSize,
                                Sort.by("empfirstNm")
                                                .ascending()
                                                .and(Sort.by("emplastNm"))
                                                .ascending()
                                                .and(Sort.by("department"))
                                                .ascending());

                Page<User> userPage = userService.findBySearchCriteria(builder.build(), page);
                apiResponse.setData(userPage.toList());
                apiResponse.setResponseCode(HttpStatus.OK);
                apiResponse.setMessage("Successfully retrieved user record");

                return new ResponseEntity<>(apiResponse,
                                apiResponse.getResponseCode());
        }
}
