package com.spring.mongo.demo.dto.docstore;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class DocStoreConfiguration implements Serializable {

    private String elkHost;
    private String elkPort;
    private boolean elkSsl;
    private String elkRuleIndex;

}
