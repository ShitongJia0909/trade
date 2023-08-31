package com.jst.trajst.dashboard.vo;

import com.jst.trajst.dashboard.internal.globalCalling.AbstractGlobalEsResultMerge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseResponse<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseResponse.class);

    private String message;
    private Integer code;
    private T data;

    public BaseResponse(String message, Integer code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        BaseResponse.logger = logger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
