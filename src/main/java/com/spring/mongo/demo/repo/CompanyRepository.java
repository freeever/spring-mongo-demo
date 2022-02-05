package com.spring.mongo.demo.repo;

import com.spring.mongo.demo.model.indexdoc.Company;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

    List<Company> findAllBy(TextCriteria criteria, Sort sort);

    @Aggregation(pipeline= {
            "{ $match: { $text: { $search: '\"?0\"' } } }",
            "{ $unionWith: { coll: 'com1', pipeline: [ { $match: { $text: { $search: '\"?0\"' } } } ] } }",
            "{ $unionWith: { coll: 'com2', pipeline: [ { $match: { $text: { $search: '\"?0\"' } } } ] } }",
            "{ $sort: { 'name': 1 } }"
    })
    List<Company> findByAggregationTextSearch(String search);
}
