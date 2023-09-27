package com.jst.trajst.dashboard.common.exceptions;

import com.jst.common.exceptions.BaseRuntimeException;
import com.jst.trajst.dashboard.enums.ErrorCode;

public class ZabbixLoginException extends BaseRuntimeException {

    public ZabbixLoginException(String message){
        super(ErrorCode.ZABBIX_LOGIN_ERROR, message);
    }

}
