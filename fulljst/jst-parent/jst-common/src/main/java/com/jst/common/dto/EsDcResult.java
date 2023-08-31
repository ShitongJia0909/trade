package com.jst.common.dto;

import java.util.Map;

public class EsDcResult<T> {

    private Map<String, String> statusMap;
    private Map<String, T> resultMap;

    public EsDcResult(Map<String, String> statusMap, Map<String, T> resultMap) {
        this.statusMap = statusMap;
        this.resultMap = resultMap;
    }

    public Map<String, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<String, String> statusMap) {
        this.statusMap = statusMap;
    }

    public Map<String, T> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, T> resultMap) {
        this.resultMap = resultMap;
    }
}
