package com.spring.mongo.demo.dto.docstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class VersionDto {

    @Id
    String id;
    @Field(name = "VersionId")
    String versionId;
    @Field(name = "IsOverride")

    @JsonProperty("isOverride")
    boolean isOverride;
    @Field(name = "Comments")
    String comments;
    @Field(name = "CreatedBy")
    String createdBy;
    @Field(name = "CreatedOn")
    Date createdOn;
    @Field(name = "ChangedOn")
    Date changedOn;
    @Field(name = "ApprovedBy")
    String approvedBy;
    @Field(name = "Number")
    String number;
    @Field(name = "Status")
    String status;
    @Field(name = "ReleaseVersion")
    String releaseVersion;
    @Field(name = "IsUpload")

    @JsonProperty("isUpload")
    boolean isUpload;
    @Field(name = "FileName")
    String fileName;

    @Field(name = "Deleted")
    FeedDeletedDto deleted;

    @Field(name = "Modified")
    FeedModifiedDto modified;

    @Field(name = "Added")
    FeedAddedDto added;

    String feedId;
    String modifyId;
    String addTokenId;

}
