package com.spring.mongo.demo.rest;

import com.spring.mongo.demo.dto.indexdoc.CompanyDto;
import com.spring.mongo.demo.dto.indexdoc.CompanyTextSearchRequest;
import com.spring.mongo.demo.model.indexdoc.Company;
import com.spring.mongo.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/companies")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/criteria")
    @ResponseBody
    public List<Company> findByCrtiterialTextSearch(@RequestBody CompanyTextSearchRequest request) {
        return companyService.findByCrtiterialTextSearch(request);
    }

    @PostMapping("/aggregation")
    @ResponseBody
    public List<Company> findByAggregationTextSearch(@RequestBody CompanyTextSearchRequest request) {
        return companyService.findByAggregationTextSearch(request);
    }

    @PostMapping("/rules")
    @ResponseBody
    public List<CompanyDto> findRules(@RequestBody CompanyTextSearchRequest request) {
        return companyService.findRules(request);
    }

    @PostMapping("/text-search-native")
    @ResponseBody
    public List<Document> findByTextSearchNativeQuery(@RequestBody CompanyTextSearchRequest request) {
        return companyService.findByTextSearchNativeQuery(request);
    }

}
