package com.spring.mongo.demo.service;

import com.spring.mongo.demo.dto.docstore.VersionDto;
import com.spring.mongo.demo.model.docstore.Version;
import com.spring.mongo.demo.repo.PublisherRepository;
import com.spring.mongo.demo.repo.VersionRepository;
import com.spring.mongo.demo.util.CustomAggregationOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class VersionService {

    private final VersionRepository versionRepository;

    private final MongoTemplate mongoTemplate;

    public List<Version> findAll() {
        return versionRepository.findAll();
    }

    public Version findById(String id) {
        return versionRepository.findById(id).get();
    }

    public List<VersionDto> findByAggregation() {

//        AggregationOperation unwindModified = Aggregation.unwind("Modified", true);
//        AggregationOperation unwindAddedNewTokens = Aggregation.unwind("Added.NewTokens", true);
//        AggregationOperation unwindAddedNewObjects = Aggregation.unwind("Added.NewObjects", true);

        String query = "[ { $match: { Category: 'Config' } }, " +
                "    { $unwind: { path: '$Modified', preserveNullAndEmptyArrays: true } }, " +
                "    { $unwind: { path: '$Added.NewTokens', preserveNullAndEmptyArrays: true } }, " +
                "    { $unwind: { path: '$Added.NewObjects', preserveNullAndEmptyArrays: true } }, " +
                "    { " +
                "      $addFields: { " +
                "        feedId: '98cbefb7-2e35-43e6-a8ec-f8245132d2af', " +
                "        deleteId: { $arrayElemAt: [ '$Deleted.Delta._id', 0 ] }, " +
                "        modifyId: '$Modified._id', " +
                "        addTokenId: '$Added.NewTokens._id', " +
                "        addObjectId: '$Added.NewObjects.id' " +
                "      } " +
                "    }, " +
                "    { " +
                "      $match: { " +
                "        $and: [ " +
                "          { 'feedId': { $exists: true, $ne: null } }, " +
                "          { " +
                "            $expr: { " +
                "              $or: [ " +
                "                { $eq: [ '$deleteId', '$feedId' ] }, " +
                "                { $eq: [ '$modifyId', '$feedId' ] }, " +
                "                { $eq: [ '$addTokenId', '$feedId' ] }, " +
                "                { $eq: [ '$addObjectId', '$feedId' ] } " +
                "              ] " +
                "            } " +
                "          } " +
                "        ] " +
                "      } " +
                "    }, " +
                "    { " +
                "      $group: { " +
                "        _id: '$_id', " +
                "        version: { '$first': '$$ROOT' } " +
                "      } " +
                "    }, " +
                "    { " +
                "      '$replaceRoot': { 'newRoot': { '$mergeObjects': '$version' } } " +
                "    } ], cursor: {} ";
// Option 1
//        TypedAggregation<Version> aggregation = Aggregation.newAggregation(
//                Version.class,
////                unwindModified, unwindAddedNewTokens, unwindAddedNewObjects,
//                new CustomAggregationOperation(query)
//        );
//
//        AggregationResults<Version> results =  mongoTemplate.aggregate(aggregation, Version.class);
//        List<Version> courses = results.getMappedResults();
//        log.info(query);

        // Option 2
//        Document commandResult = mongoTemplate.executeCommand("{aggregate: 'Versions', pipeline: " + query + "}");

        // Option 3
        List<VersionDto> versions = versionRepository.findByAggregation();
//
//        log.info("======== {}", document.toJson());
        return versions;
    }
}

/**
 * MongoDB changed in 3.6 how the aggregation command works. Aggregations require now a cursor.
 * We adapted Spring Data MongoDB 2.1 but not previous versions.
 *
 * Aggregations must be invoked through the collection's aggregate(…) method instead of calling the command directly.
 * This is also the reason why we didn't backport the change. executeCommand(…) is no longer called and we don't want to
 * break compatibility in a bugfix release.
 *
 * The easiest approach for you can be to override the aggregate(…) method and call the appropriate method,
 * DBCollection.aggregate(…) with the mapped aggregation pipeline.
 */
