package com.spring.mongo.demo.common.exception;

import com.spring.mongo.demo.common.error.ErrorCategory;

public class DocStoreDataAccessException extends DocStoreException {

    public DocStoreDataAccessException() {
        super();
        setErrorCategory(ErrorCategory.DataAccess);
    }
}
