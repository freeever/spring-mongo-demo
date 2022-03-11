package com.spring.mongo.demo.service;

import com.spring.mongo.demo.common.DataQueryLoader;
import com.spring.mongo.demo.dto.docstore.RuleSearchRequest;
import com.spring.mongo.demo.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RuleService {

    private final MongoTemplate mongoTemplate;
    private final DataQueryLoader dataQueryLoader;

    private static final Map<String, String> ruleSearchCollectionMap = new HashMap<String, String>() {{
        put("EtlMetaData", "Onboarding");
    }};

    public List<Document> findRules(RuleSearchRequest request) {
        String param1 = QueryUtils.parseTextSearchTerm(request.getTerm());
        String param2 = QueryUtils.parseTextSearchRegex(param1);

        List<Document> rules = new ArrayList<>();
        new ArrayList<String>(ruleSearchCollectionMap.keySet()).parallelStream().forEach(coll -> {
            String param3 = ruleSearchCollectionMap.get(coll);
            String query = dataQueryLoader.getRuleQuery("rule.text.search", param1, param2, param3);
            log.info(query);

            String jsonCommand = "{aggregate: 'EtlMetaData', pipeline: " + query + "}";
            Document commandResult = mongoTemplate.executeCommand(jsonCommand);
            List<Document> documents = (List<Document>) ((Document) commandResult.get("cursor")).get("firstBatch");

            rules.addAll(documents);
        });

        return rules;
    }

}
