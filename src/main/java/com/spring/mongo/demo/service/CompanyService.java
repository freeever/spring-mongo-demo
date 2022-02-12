package com.spring.mongo.demo.service;

import com.spring.mongo.demo.dto.indexdoc.CompanyDto;
import com.spring.mongo.demo.dto.indexdoc.CompanyTextSearchRequest;
import com.spring.mongo.demo.model.indexdoc.Company;
import com.spring.mongo.demo.repo.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> findByCrtiterialTextSearch(CompanyTextSearchRequest request) {
        if (StringUtils.isBlank(request.getWords())) {
            return new ArrayList<>();
        }

        List<Company> results = null;
        Sort sort = Sort.by("name").ascending();
        String words = StringUtils.normalizeSpace(request.getWords()).trim();
        if ("All".equalsIgnoreCase(request.getMatchType())) {
            TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(words);
            results = companyRepository.findAllBy(criteria, sort);
        } else {
            TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(StringUtils.split(words, " "));
            results = companyRepository.findAllBy(criteria, sort);
        }

        return results;
    }

    public List<Company> findByAggregationTextSearch(CompanyTextSearchRequest request) {
        if (StringUtils.isBlank(request.getWords())) {
            return new ArrayList<>();
        }

        String words = StringUtils.normalizeSpace(request.getWords()).trim();
        if ("All".equalsIgnoreCase(request.getMatchType())) {
            words = "\\\"" + words + "\\\"";
        }
        List<Company> results = companyRepository.findByAggregationTextSearch(request.getWords());
        return results;
    }

    public List<CompanyDto> findRules(CompanyTextSearchRequest request) {
        if (StringUtils.isBlank(request.getWords())) {
            return new ArrayList<>();
        }

        String words = StringUtils.normalizeSpace(request.getWords()).trim();
        if ("All".equalsIgnoreCase(request.getMatchType())) {
            words = "\\\"" + words + "\\\"";
        }
        List<CompanyDto> results = companyRepository.findRules(request.getWords());
        return results;
    }
}
