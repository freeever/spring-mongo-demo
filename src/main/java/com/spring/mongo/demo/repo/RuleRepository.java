package com.spring.mongo.demo.repo;

import com.spring.mongo.demo.model.docstore.Rule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RuleRepository extends ElasticsearchRepository<Rule, String> {

}
