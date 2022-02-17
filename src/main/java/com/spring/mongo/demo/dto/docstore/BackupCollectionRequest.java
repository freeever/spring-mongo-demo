package com.spring.mongo.demo.dto.docstore;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

public class BackupCollectionRequest implements Serializable {

    @NotEmpty(message="{COMM_0003E}")
    List<String> collections;

    public List<String> getCollections() {
        return collections;
    }

    public void setCollections(List<String> collections) {
        this.collections = collections;
    }
}
