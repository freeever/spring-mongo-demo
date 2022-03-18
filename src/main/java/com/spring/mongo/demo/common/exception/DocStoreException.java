package com.spring.mongo.demo.common.exception;

import com.spring.mongo.demo.common.error.ErrorCategory;

public class DocStoreException extends Exception {

    private static final long serialVersionUID = 1047509332857665745L;

    private ErrorCategory errorCategory = ErrorCategory.InternalError;
    private String errorCode;
    private String messageCode;
    private Object[] arguments;

    public DocStoreException() {
        super();
    }

    public DocStoreException(String message) {
        super(message);
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

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
}
