package com.jst.jstdao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TAlarmHistory implements Serializable {
    @Id
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    @GeneratedValue(generator = "user-uuid")
    private String id;
    private String evalName;
    private String evalDesc;
    private String fieldsExp;
    private Date logTime;
    private int state;
    private String alarm_processor;
    private int alarm_time_scope;
    private String content;
    private String evalResult;
    private Date createTime;
    private Timestamp modifyTime;
    private String standby4;

    public String getId() {
        return id;
    }

    public String getEvalName() {
        return evalName;
    }

    public String getEvalDesc() {
        return evalDesc;
    }

    public String getFieldsExp() {
        return fieldsExp;
    }

    public Date getLogTime() {
        return logTime;
    }

    public int getState() {
        return state;
    }

    public String getAlarm_processor() {
        return alarm_processor;
    }

    public int getAlarm_time_scope() {
        return alarm_time_scope;
    }

    public String getContent() {
        return content;
    }

    public String getEvalResult() {
        return evalResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public String getStandby4() {
        return standby4;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEvalName(String evalName) {
        this.evalName = evalName;
    }

    public void setEvalDesc(String evalDesc) {
        this.evalDesc = evalDesc;
    }

    public void setFieldsExp(String fieldsExp) {
        this.fieldsExp = fieldsExp;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAlarm_processor(String alarm_processor) {
        this.alarm_processor = alarm_processor;
    }

    public void setAlarm_time_scope(int alarm_time_scope) {
        this.alarm_time_scope = alarm_time_scope;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEvalResult(String evalResult) {
        this.evalResult = evalResult;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setStandby4(String standby4) {
        this.standby4 = standby4;
    }
}
