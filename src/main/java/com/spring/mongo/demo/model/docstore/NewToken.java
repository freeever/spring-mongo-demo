package com.spring.mongo.demo.model.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.List;

@Data
public class NewToken {

    @Field(name = "_id")
    String id;

    @Field(name = "Info")
    List<ModificationInfo> info;
}
