package com.spring.mongo.demo.dto.indexdoc;

import lombok.Data;

@Data
public class CompanyTextSearchRequest {

    String words;

    String matchType;
}
