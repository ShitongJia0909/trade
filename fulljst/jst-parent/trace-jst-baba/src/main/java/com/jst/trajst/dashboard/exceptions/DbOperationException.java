package com.jst.trajst.dashboard.exceptions;

import com.jst.common.exceptions.BaseRuntimeException;

import static com.jst.trajst.dashboard.enums.ErrorCode.DB_OPERATION_ERROR;

public class DbOperationException extends BaseRuntimeException {
    public DbOperationException(String message) {
        super(DB_OPERATION_ERROR, message);
    }
}
