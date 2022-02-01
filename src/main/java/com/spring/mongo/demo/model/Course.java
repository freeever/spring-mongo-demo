package com.spring.mongo.demo.model;

import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Course {

    @Id
    private String id;

    private String name;

    private List<String> studentIds;



    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", studentIds=" + studentIds +
                '}';
    }
}
