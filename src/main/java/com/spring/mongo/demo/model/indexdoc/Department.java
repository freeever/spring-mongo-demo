package com.spring.mongo.demo.model.indexdoc;

import lombok.Data;

import java.util.List;

@Data
public class Department {

    String id;
    String name;
    List<Employee> employees;
}
