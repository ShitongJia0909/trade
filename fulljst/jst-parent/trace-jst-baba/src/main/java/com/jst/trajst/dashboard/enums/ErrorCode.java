package com.jst.trajst.dashboard.enums;

public class ErrorCode {


    //2. related to database error
    public static final int DB_OPERATION_ERROR = 200001;


    //3. related to controller error
    public static final int PARA_ILLEGAL_ERROR = 30001;
    public static final int ILLEGAL_INPUT_PARAM = 30003;

    // ZABBIX ERROR
    public static final int ZABBIX_LOGIN_ERROR = 50004;
    public static final int ZABBIX_OPERATION_ERROR = 50005;



}
