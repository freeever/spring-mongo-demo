package com.spring.mongo.demo.service;

import com.spring.mongo.demo.common.DataQueryLoader;
import com.spring.mongo.demo.dto.docstore.RuleSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RuleService {

    private final MongoTemplate mongoTemplate;
    private final DataQueryLoader dataQueryLoader;

    public List<Document> findRules(RuleSearchRequest request) {

        String query = dataQueryLoader.getRuleQuery("rule.text.search", request.getTerm());
        log.info(query);
        Document commandResult = mongoTemplate.executeCommand("{aggregate: 'EtlMetaData', pipeline: " + query + "}");
        List<Document> documemts = (List<Document>) ((Document) commandResult.get("cursor")).get("firstBatch");
        return documemts;
    }
}
