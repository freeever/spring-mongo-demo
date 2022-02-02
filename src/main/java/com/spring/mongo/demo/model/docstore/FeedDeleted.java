package com.spring.mongo.demo.model.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.List;

@Data
public class FeedDeleted {

    @Field(name = "Ids")
    List<String> ids;

    @Field(name = "Delta")
    List<String> delta;
}
