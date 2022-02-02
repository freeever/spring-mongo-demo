package com.spring.mongo.demo.model.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.List;

@Data
public class FeedAdded {

    @Field(name = "NewTokens")
    List<NewToken> newTokens;

    @Field(name = "NewObjects")
    List<NewObject> newObjects;
}
