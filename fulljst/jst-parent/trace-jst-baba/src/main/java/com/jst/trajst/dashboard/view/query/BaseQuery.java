package com.jst.trajst.dashboard.view.query;

import javax.validation.constraints.NotNull;

public class BaseQuery {

    private String dc;
    @NotNull
    private Long beginTime;
    @NotNull
    private Long endTime;

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
