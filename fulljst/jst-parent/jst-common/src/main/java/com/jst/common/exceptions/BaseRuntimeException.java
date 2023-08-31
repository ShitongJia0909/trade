package com.jst.common.exceptions;

public class BaseRuntimeException extends RuntimeException {

    protected int code;

    public BaseRuntimeException(int code){
        super();
        this.code = code;
    }

    public BaseRuntimeException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
