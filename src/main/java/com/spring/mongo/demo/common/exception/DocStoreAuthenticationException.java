package com.spring.mongo.demo.common.exception;

import com.spring.mongo.demo.common.error.ErrorCategory;

public class DocStoreAuthenticationException extends DocStoreException {

    public DocStoreAuthenticationException() {
        setErrorCategory(ErrorCategory.Authentication);
    }

}
