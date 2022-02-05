package com.spring.mongo.demo.model.indexdoc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("company")
@Data
public class Company {

    @Id
    String id;
    String name;

    List<Department> departments;
}
