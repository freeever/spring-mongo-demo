package com.spring.mongo.demo.dto.indexdoc;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    String feed;
    List<RuleGroupDto> ruleGroups;
}
