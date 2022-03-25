package com.spring.mongo.demo.model.docstore;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName = "#{@environment.getProperty('elk.index.rule')}", createIndex = false)
public class Rule {

    @Id
    private String id;

    String tile;
    String rulegroupId;
    String rulegroup;
    String rulegroupSequence;
    String ruleId;
    String value;
    String attributeName;
    String ruleSequence;
}
