package com.jst.jstdao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "t_evaluator_result")
public class TEvaluatorResult implements Serializable {


    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    private String id;
    private String evalName;
    private String dsCode;
    private String groupName;
    private String fieldsExp;
    private String result;
    private int evalState;
    private int alarmState;
    private Date logTime;
    private Timestamp evalStartTime;
    private Date evalEndTime;
    private Date createTime;
    private Timestamp modifyTime;
    private String standby4;

    public void setId(String id) {
        this.id = id;
    }

    public void setEvalName(String evalName) {
        this.evalName = evalName;
    }

    public void setDsCode(String dsCode) {
        this.dsCode = dsCode;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setFieldsExp(String fieldsExp) {
        this.fieldsExp = fieldsExp;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setEvalState(int evalState) {
        this.evalState = evalState;
    }

    public void setAlarmState(int alarmState) {
        this.alarmState = alarmState;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public void setEvalStartTime(Timestamp evalStartTime) {
        this.evalStartTime = evalStartTime;
    }

    public void setEvalEndTime(Date evalEndTime) {
        this.evalEndTime = evalEndTime;
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

    public String getId() {
        return id;
    }

    public String getEvalName() {
        return evalName;
    }

    public String getDsCode() {
        return dsCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getFieldsExp() {
        return fieldsExp;
    }

    public String getResult() {
        return result;
    }

    public int getEvalState() {
        return evalState;
    }

    public int getAlarmState() {
        return alarmState;
    }

    public Date getLogTime() {
        return logTime;
    }

    public Timestamp getEvalStartTime() {
        return evalStartTime;
    }

    public Date getEvalEndTime() {
        return evalEndTime;
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
}
