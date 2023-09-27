package com.jst.trajst.dashboard.common.exceptions;

import com.jst.common.exceptions.BaseRuntimeException;
import com.jst.trajst.dashboard.enums.ErrorCode;

public class ZabbixOperationException extends BaseRuntimeException {

    public ZabbixOperationException(String message){
        super(ErrorCode.ZABBIX_OPERATION_ERROR, message);
    }

}
