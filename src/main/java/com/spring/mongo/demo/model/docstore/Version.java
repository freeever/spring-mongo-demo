package com.spring.mongo.demo.model.docstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Date;
import java.util.List;

@Data
@Document("Versions")
public class Version {

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
    FeedDeleted deleted;

    @Field(name = "Modified")
    List<FeedModified> modified;

    @Field(name = "Added")
    FeedAdded added;
}
