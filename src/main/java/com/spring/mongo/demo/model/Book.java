package com.spring.mongo.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@Document("book")
public class Book {
    @Id
    String id;
    String courseName;
    @DocumentReference(lookup = "{ '_id' : ?#{#target} }")
    Author author;

}
