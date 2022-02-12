package com.spring.mongo.demo.dto.indexdoc;

import lombok.Data;

import java.util.List;

@Data
public class RuleGroupDto {

    String ruleGroup;
    List<RuleDto> rules;
}
