package com.spring.mongo.demo.common.error;

public interface MessageCode {

    String SYSTEM_ERROR = "COMM_0001E";
    String AUTHENTICATION_ERROR = "COMM_0002E";

    String COLLECTION_NOT_FOUND = "COMM_0100E";

    String ERR_BOOKMARK_NOT_FOUND = "error.bookmark.not.found";
}
