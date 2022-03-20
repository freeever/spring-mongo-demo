package com.spring.mongo.demo.model.docstore;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "bookmark")
public class Bookmark implements Serializable {

    @Id
    private String id;
    private String name;
    private String path;
    private String user;
}
