package com.spring.mongo.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("office")
public class Publisher {
    @Id
    String id;
    String roomNo;
}
