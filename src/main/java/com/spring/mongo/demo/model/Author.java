package com.spring.mongo.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@NoArgsConstructor
@Document("teacher")
public class Author {
    @Id
    String id;
    String firstName;
    String lastName;
    @DocumentReference(lazy = false)
    List<Book> courses;
    @DocumentReference
    Publisher office;
}
