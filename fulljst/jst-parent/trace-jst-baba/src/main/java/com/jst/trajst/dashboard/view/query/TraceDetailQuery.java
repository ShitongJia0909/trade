package com.jst.trajst.dashboard.view.query;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TraceDetailQuery extends BaseQuery{

    @NotEmpty
    private String traceID;
    private String tmsgSn;

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getTmsgSn() {
        return tmsgSn;
    }

    public void setTmsgSn(String tmsgSn) {
        this.tmsgSn = tmsgSn;
    }
}
