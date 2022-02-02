package com.spring.mongo.demo.dto.docstore;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class NewObjectDto {

    @Field(name = "Active")
    boolean active;
    @Field(name = "AdjustedFloorType")
    String adjustedFloorType;
    @Field(name = "AssetSortDirection")
    String assetSortDirection;
    @Field(name = "AssetSortField")
    String assetSortField;
    @Field(name = "BucketDesc")
    String bucketDesc;
    @Field(name = "BucketId")
    String bucketId;
    @Field(name = "BucketName")
    String bucketName;
    @Field(name = "HypoSecurityId")
    String hypoSecurityId;
    @Field(name = "IFRS")
    boolean ifrs;
    @Field(name = "IndexType")
    String indexType;
    @Field(name = "ResetFrequency")
    int resetFrequency;
    @Field(name = "SimulationModel")
    String simulationModel;
    @Field(name = "StochasticModel")
    String stochasticModel;
    @Field(name = "USGAAP")
    boolean usgaap;
    @Field(name = "U+0024type")
    String u0024type;

}
