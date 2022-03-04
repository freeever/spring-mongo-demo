package com.spring.mongo.demo.rest;

import com.spring.mongo.demo.dto.docstore.RuleSearchRequest;
import com.spring.mongo.demo.dto.indexdoc.CompanyDto;
import com.spring.mongo.demo.dto.indexdoc.CompanyTextSearchRequest;
import com.spring.mongo.demo.model.indexdoc.Company;
import com.spring.mongo.demo.service.CompanyService;
import com.spring.mongo.demo.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/rules")
@RestController
public class RuleController {

    private final RuleService ruleService;

    @PostMapping
    @ResponseBody
    public List<Document> findByTextSearchNativeQuery(@RequestBody RuleSearchRequest request) {
        return ruleService.findRules(request);
    }
}
