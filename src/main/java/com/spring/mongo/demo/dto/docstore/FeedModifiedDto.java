package com.spring.mongo.demo.dto.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class FeedModifiedDto {

    @Field(name = "_id")
    String id;

    @Field(name = "Info")
    List<ModificationInfoDto> info;
}
