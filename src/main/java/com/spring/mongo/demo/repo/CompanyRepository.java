package com.spring.mongo.demo.repo;

import com.spring.mongo.demo.dto.indexdoc.CompanyDto;
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

    @Aggregation(pipeline = {
            "{ $match: { $text: { $search: '\"?0\"' } } }",
            "{ $unionWith: { coll: 'com1', pipeline: [ { $match: { $text: { $search: '\"?0\"' } } } ] } }",
            "{ $unionWith: { coll: 'com2', pipeline: [ { $match: { $text: { $search: '\"?0\"' } } } ] } }",
            "{ $sort: { 'name': 1 } }"
    })
    List<Company> findByAggregationTextSearch(String search);

    @Aggregation(pipeline = {
            "{ $match: { $text: { $search: '\"?0\"' } } }",
            "{ $unionWith: { coll: 'com1', pipeline: [ { $match: { $text: { $search: '\"?0\"' } } } ] } }",
            "{ $unionWith: { coll: 'com2', pipeline: [ { $match: { $text: { $search: '\"?0\"' } } } ] } }",
            "{ $addFields: { feed: '$name' } }",
            "{ $project: { _id: 0, feed: 1, ruleGroup: 1, " +
            "    ruleGroups: {" +
            "      $map: {" +
            "        input: '$departments'," +
            "        as: 'dept'," +
            "        in: {" +
            "          ruleGroup: '$$dept.name'," +
            "          rules: {" +
            "            $filter: { input: '$$dept.employees', as: 'emp', cond: { $regexMatch: { input: '$$emp.description', regex: /?0/ } } }" +
            "          }" +
            "        }" +
            "      }" +
            "    }" +
            "  }" +
            "}",
            "{ $project: { 'ruleGroups.employees.email': 0 } }"
    })
    List<CompanyDto> findRules(String search);
}
