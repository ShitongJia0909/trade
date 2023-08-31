package com.jst.trajst.dashboard.vo.dto;

import com.jst.trajst.dashboard.persistance.es.model.TraceRawData;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SpanChainDTO {

    private String spanId;
    private String traceId;
    private String parentId;
    private String name;
    private String tappName;
    private String tremoteName;
    private String operationName;
    private String tkind;
    private String tprotocol;
    private String tretCode;  // 业务归属
    private BigInteger duration;
    private boolean virtual;
    private Timestamp startTime;
    private TraceRawData traceRawData;
    private List<SpanChainDTO> children = new ArrayList<>();


    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTappName() {
        return tappName;
    }

    public void setTappName(String tappName) {
        this.tappName = tappName;
    }

    public String getTremoteName() {
        return tremoteName;
    }

    public void setTremoteName(String tremoteName) {
        this.tremoteName = tremoteName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getTkind() {
        return tkind;
    }

    public void setTkind(String tkind) {
        this.tkind = tkind;
    }

    public String getTprotocol() {
        return tprotocol;
    }

    public void setTprotocol(String tprotocol) {
        this.tprotocol = tprotocol;
    }

    public String getTretCode() {
        return tretCode;
    }

    public void setTretCode(String tretCode) {
        this.tretCode = tretCode;
    }

    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
        this.duration = duration;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public TraceRawData getTraceRawData() {
        return traceRawData;
    }

    public void setTraceRawData(TraceRawData traceRawData) {
        this.traceRawData = traceRawData;
    }

    public List<SpanChainDTO> getChildren() {
        return children;
    }

    public void setChildren(List<SpanChainDTO> children) {
        this.children = children;
    }
}
