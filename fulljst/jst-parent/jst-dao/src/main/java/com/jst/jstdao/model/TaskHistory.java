package com.jst.jstdao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "task_history")
public class TaskHistory {

    @Id
    @Column
    @GenericGenerator(name="user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "uudi")
    private String id;

    private String taskName;

    private int result;

    private int performTimes;

    private long cost;

    private String hostname;

    private String threadName;

    @Column(nullable = false)
    private Timestamp startTime;
    @Column(nullable = false)
    private Timestamp endTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getPerformTimes() {
        return performTimes;
    }

    public void setPerformTimes(int performTimes) {
        this.performTimes = performTimes;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
