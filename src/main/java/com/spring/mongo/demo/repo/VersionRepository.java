package com.spring.mongo.demo.repo;

import com.spring.mongo.demo.dto.docstore.VersionDto;
import com.spring.mongo.demo.model.docstore.Version;
import org.bson.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepository extends MongoRepository<Version, String>  {

    @Aggregation(pipeline = { "{ $out: '?0' }" })
    public List<Document> backup(String collectionName);

    @Aggregation(pipeline= {
            "{ $match: { Category: 'Config' } } ",
            "{ $unwind: { path: '$Modified', preserveNullAndEmptyArrays: true } }",
            "{ $unwind: { path: '$Added.NewTokens', preserveNullAndEmptyArrays: true } }",
            "{ $unwind: { path: '$Added.NewObjects', preserveNullAndEmptyArrays: true } }",
            "{ $lookup: { from: 'EtlMetaData', pipeline: [ { $match: { feed: 'USInvestment' } } ], as: 'feed' } }",
            "{ $addFields: { " +
                    "feedId: { $arrayElemAt: [ '$feed.id', 0 ] }, " +
                    "deleteId: { $arrayElemAt: [ '$Deleted.Delta._id', 0 ] }, " +
                    "modifyId: '$Modified._id', addTokenId: '$Added.NewTokens._id', " +
                    "addObjectId: '$Added.NewObjects.id' } }",
            "{ $match: { $and: [ { 'feedId': { $exists: true, $ne: null } }, { $expr: { " +
                    "$or: [ " +
                    "  { $eq: [ '$deleteId', '$feedId' ] }, " +
                    "  { $eq: [ '$modifyId', '$feedId' ] }, " +
                    "  { $eq: [ '$addTokenId', '$feedId' ] }, " +
                    "  { $eq: [ '$addObjectId', '$feedId' ] } ] } } ] } }",
            "{ $group: { _id: '$_id', version: { '$first': '$$ROOT' } } }",
            "{ '$replaceRoot': { 'newRoot': { '$mergeObjects': '$version' } } }"
    })
    public List<VersionDto> findByAggregation();

}
