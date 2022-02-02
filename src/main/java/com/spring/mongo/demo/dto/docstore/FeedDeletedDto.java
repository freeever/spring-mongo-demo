package com.spring.mongo.demo.dto.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class FeedDeletedDto {

    @Field(name = "Ids")
    List<String> ids;

    @Field(name = "Delta")
    List<String> delta;
}
