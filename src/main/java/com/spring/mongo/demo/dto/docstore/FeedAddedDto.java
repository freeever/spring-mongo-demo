package com.spring.mongo.demo.dto.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class FeedAddedDto {

    @Field(name = "NewTokens")
    NewTokenDto newTokens;

    @Field(name = "NewObjects")
    NewObjectDto newObjects;
}
