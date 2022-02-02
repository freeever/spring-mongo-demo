package com.spring.mongo.demo.service;

import com.spring.mongo.demo.model.Course;
import com.spring.mongo.demo.util.CustomAggregationOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AggregationService {

    private final MongoTemplate mongoTemplate;

    public List<Course> findCoursesWithStudents() {

        AggregationOperation unwind = Aggregation.unwind("studentIds");

        String query = "{$lookup: {from: 'studen t', let: { stuId: { $toObjectId: '$studentIds' } },"
                + "pipeline: [{$match: {$expr: { $eq: [ '$_id', '$$stuId' ] },},}, "
                + "{$project: {isSendTemplate: 1, openId: 1, name: '$name',stu_id: '$_id',},},], "
                + "as: 'students',}, }";

        TypedAggregation<Course> aggregation = Aggregation.newAggregation(
                Course.class,
                unwind,
                new CustomAggregationOperation(query)
        );

        AggregationResults<Course> results =  mongoTemplate.aggregate(aggregation, Course.class);
        List<Course> courses = results.getMappedResults();
        log.info("Courses: {}", courses);
        return courses;
    }

}
