package com.spring.mongo.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring.mongo.demo.common.DataQueryLoader;
import com.spring.mongo.demo.dto.docstore.DocStoreConfiguration;
import com.spring.mongo.demo.dto.docstore.RuleDto;
import com.spring.mongo.demo.dto.docstore.RuleSearchRequest;
import com.spring.mongo.demo.model.docstore.Rule;
import com.spring.mongo.demo.repo.RuleRepository;
import com.spring.mongo.demo.util.CustomAggregationOperation;
import com.spring.mongo.demo.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RuleService {

    private final MongoTemplate mongoTemplate;
    private final DataQueryLoader dataQueryLoader;
    private final ModelMapper modelMapper;
    private final RuleRepository ruleRepository;
    private final DocStoreConfiguration docStoreConfiguration;
    @Qualifier("elasticsearchClient")
    private final RestHighLevelClient elasticsearchClient;

    private static final Map<String, String> ruleSearchCollectionMap = new HashMap<String, String>() {{
        put("EtlMetaData", "Onboarding");
        put("EtlMetaData_Test", "Onboarding_Test");
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

    /**
     * Delete the existing rule index on Elasticsearch, create new index,
     * and transform and export all the rules to the index
     * @return
     */
    public int exportRuleToElasticsearch() throws IOException {
        this.recreateRuleIndex();
        List<RuleDto> ruleDtoList = this.findAll();
        List<Rule> rules = ruleDtoList.stream()
                .map(rule -> this.modelMapper.map(rule, Rule.class))
                .collect(Collectors.toList());

        List<Rule> rulesAdded = (List<Rule>) ruleRepository.saveAll(rules);
        return rulesAdded.size();
    }

    /**
     * Find all the rules and flatten the results
     * @return
     */
    public List<RuleDto> findAll() {
        List<Rule> rules = this.findAllRules();

        return rules.stream()
                .map(rule -> modelMapper.map(rule, RuleDto.class))
                .collect(Collectors.toList());
    }

    public List<RuleDto> findByText(RuleSearchRequest request) throws IOException {
        List<String> terms = QueryUtils.splitText(request.getTerm());
        if (terms == null || terms.isEmpty()) {
            return null;
        }

        SearchRequest searchRequest = new SearchRequest(docStoreConfiguration.getElkRuleIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        List<WildcardQueryBuilder> wildcards = new ArrayList<WildcardQueryBuilder>();
        for (String term : terms) {
            wildcards.add(new WildcardQueryBuilder("value", "*" + term.toLowerCase() + "*"));
            wildcards.add(new WildcardQueryBuilder("attributeName", "*" + term.toLowerCase() + "*"));
        }

        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        for(WildcardQueryBuilder wildcard : wildcards) {
            boolQuery.should(wildcard);
        }

        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

        return getRules(response);
    }

    private List<RuleDto> getRules(SearchResponse response) {
        List<RuleDto> rules = new ArrayList<>();
        SearchHit[] results = response.getHits().getHits();
        for(SearchHit hit : results){
            String sourceAsString = hit.getSourceAsString();
            if (sourceAsString != null) {
                Gson gson = new Gson();
                rules.add(gson.fromJson(sourceAsString, RuleDto.class));
            }
        }

        return rules;
    }

    /**
     * Delete the existing rule index, and create new one
     * @throws IOException
     */
    private void recreateRuleIndex() throws IOException {
        String indexName = docStoreConfiguration.getElkRuleIndex();
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        elasticsearchClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        elasticsearchClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    private List<Rule> findAllRules() {
        log.info("RuleService.findAllRules() from MongoDB");
        AggregationOperation unwindRuleGroups = Aggregation.unwind("rulegroups", true);
        AggregationOperation unwindRules = Aggregation.unwind("rulegroups.rules", true);
        List<Document> allDocuments = new ArrayList<>();

        new ArrayList<String>(ruleSearchCollectionMap.keySet()).parallelStream().forEach(coll -> {
            String param1 = ruleSearchCollectionMap.get(coll);
            String query = dataQueryLoader.getRuleQuery("rule.search.all", param1);
            log.info(query);

            String jsonCommand = "{aggregate: 'EtlMetaData', pipeline: " + query + "}";
            Document commandResult = mongoTemplate.executeCommand(jsonCommand);
            List<Document> documents = (List<Document>) ((Document) commandResult.get("cursor")).get("firstBatch");

            allDocuments.addAll(documents);
        });

        Gson gson = new Gson();
        List<Rule> rules = allDocuments.stream()
                .map(rule -> {
                    return gson.fromJson(rule.toJson(), Rule.class);
                })
                .collect(Collectors.toList());

        return rules;
    }

}
