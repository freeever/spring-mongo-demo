package com.spring.mongo.demo.common.error;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = -627273722361603240L;

    private int httpStatusCode;
    private ErrorCategory errorCategory;
    @JsonIgnore
    private String errorCode;
    private String message;

    public ErrorInfo() {
    }

    public ErrorInfo(int httpStatusCode, ErrorCategory errorCategory) {
        this.httpStatusCode = httpStatusCode;
        this.errorCategory = errorCategory;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public ErrorCategory getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(ErrorCategory errorCategory) {
        this.errorCategory = errorCategory;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
