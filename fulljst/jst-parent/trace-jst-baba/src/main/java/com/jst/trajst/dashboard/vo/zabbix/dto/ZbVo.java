package com.jst.trajst.dashboard.vo.zabbix.dto;

import java.sql.Timestamp;
import java.util.List;

public class ZbVo {

    private String groupName;
    private String groupId;
    private String idc;
    private String ip;
    private String name;
    private String valueType;
    private Timestamp startTime;
    private Timestamp endTime;
    private List<String> ipList;

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getIdc() {
        return idc;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public String getValueType() {
        return valueType;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public List<String> getIpList() {
        return ipList;
    }
}
