package com.spring.mongo.demo.model.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.List;
import java.util.UUID;

@Data
public class ModificationInfo {

    @Field(name = "_id")
    UUID id;
    @Field(name = "Index")
    int index;
    @Field(name = "Path")
    String path;
    @Field(name = "OldValue")
    List<String> oldValue;
    @Field(name = "NewValue")
    List<String> newValue;
    @Field(name = "key")
    String key;

    @Field(name = "Parents")
    List<String> parents;
}
