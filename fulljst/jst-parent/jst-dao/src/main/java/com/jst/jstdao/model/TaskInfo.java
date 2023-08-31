package com.jst.jstdao.model;


import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "task_info")
@Entity
public class TaskInfo {

    @Id
    private String taskName;
    @Column
    private int status;

    private int enbale;

    private int intervalTime;

    private int totalExeTime;

    private int retryTime;

    private Date createTime;

    private Timestamp startTime;

    private Timestamp modifyTime;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEnbale() {
        return enbale;
    }

    public void setEnbale(int enbale) {
        this.enbale = enbale;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getTotalExeTime() {
        return totalExeTime;
    }

    public void setTotalExeTime(int totalExeTime) {
        this.totalExeTime = totalExeTime;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getModityTime() {
        return modifyTime;
    }

    public void setModityTime(Timestamp modityTime) {
        this.modifyTime = modityTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "TaskInfo{" +
                "taskName='" + taskName + '\'' +
                ", status=" + status +
                ", intervalTime=" + intervalTime +
                ", startTime=" + startTime +
                ", modityTime=" + modifyTime +
                '}';
    }
}
