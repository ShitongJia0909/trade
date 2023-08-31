package com.jst.common.config;

public class ElasticsearchConfig {

    private int connectTimeOut;

    private int socketTimeOut;

    private int connectionRequestTimeOut;

    private boolean ioKeepAlive;

    private int keepAliveStrategy;

    private int maxConnectNum;

    private int maxConnectNumPerRoute;

    private int ioThreadCount;

    private int maxRedirects;

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public int getConnectionRequestTimeOut() {
        return connectionRequestTimeOut;
    }

    public void setConnectionRequestTimeOut(int connectionRequestTimeOut) {
        this.connectionRequestTimeOut = connectionRequestTimeOut;
    }

    public boolean isIoKeepAlive() {
        return ioKeepAlive;
    }

    public void setIoKeepAlive(boolean ioKeepAlive) {
        this.ioKeepAlive = ioKeepAlive;
    }

    public int getKeepAliveStrategy() {
        return keepAliveStrategy;
    }

    public void setKeepAliveStrategy(int keepAliveStrategy) {
        this.keepAliveStrategy = keepAliveStrategy;
    }

    public int getMaxConnectNum() {
        return maxConnectNum;
    }

    public void setMaxConnectNum(int maxConnectNum) {
        this.maxConnectNum = maxConnectNum;
    }

    public int getMaxConnectNumPerRoute() {
        return maxConnectNumPerRoute;
    }

    public void setMaxConnectNumPerRoute(int maxConnectNumPerRoute) {
        this.maxConnectNumPerRoute = maxConnectNumPerRoute;
    }

    public int getIoThreadCount() {
        return ioThreadCount;
    }

    public void setIoThreadCount(int ioThreadCount) {
        this.ioThreadCount = ioThreadCount;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }
}
