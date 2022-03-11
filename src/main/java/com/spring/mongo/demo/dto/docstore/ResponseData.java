package com.spring.mongo.demo.dto.docstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T extends Object> implements Serializable {

    private boolean success;
    private T Data;
}
