package com.jst.common.dto;

import java.util.Map;

public class GlobalResult<T> {

    private Map<String, String> status;
    private T resultObj;

    public GlobalResult(Map<String, String> status, T resultObj) {
        this.status = status;
        this.resultObj = resultObj;
    }

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }
}
