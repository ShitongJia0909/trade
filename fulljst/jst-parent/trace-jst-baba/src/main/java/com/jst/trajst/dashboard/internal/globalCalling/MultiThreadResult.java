package com.jst.trajst.dashboard.internal.globalCalling;

public class MultiThreadResult {

    private String idcName;
    private Object result;
    private String currentUrl;
    private int statusCode;
    private Exception exception;

    public String getIdcName() {
        return idcName;
    }

    public void setIdcName(String idcName) {
        this.idcName = idcName;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
