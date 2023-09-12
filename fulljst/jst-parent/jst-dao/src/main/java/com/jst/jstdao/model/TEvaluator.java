package com.jst.jstdao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_evaluator")
public class TEvaluator implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    private String id;
    private String name;
    private int enable;
    private String groupCode;
    private String dsCode;
    private String fields;
    private String expression;
    private String logicalOperator;
    private int defaultValue;
    private int every;
    private String frequency;
    private int alarmTimeScope;
    private String alarmProcessor;
    private Date createTime;
    private Date modifyTime;
    private String description;
    private String contactGroups;
    private String contactAppends;
    private String owner;
    private String exclusionKey;
    private String exclusionValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getDsCode() {
        return dsCode;
    }

    public void setDsCode(String dsCode) {
        this.dsCode = dsCode;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(String logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getEvery() {
        return every;
    }

    public void setEvery(int every) {
        this.every = every;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getAlarmTimeScope() {
        return alarmTimeScope;
    }

    public void setAlarmTimeScope(int alarmTimeScope) {
        this.alarmTimeScope = alarmTimeScope;
    }

    public String getAlarmProcessor() {
        return alarmProcessor;
    }

    public void setAlarmProcessor(String alarmProcessor) {
        this.alarmProcessor = alarmProcessor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactGroups() {
        return contactGroups;
    }

    public void setContactGroups(String contactGroups) {
        this.contactGroups = contactGroups;
    }

    public String getContactAppends() {
        return contactAppends;
    }

    public void setContactAppends(String contactAppends) {
        this.contactAppends = contactAppends;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getExclusionKey() {
        return exclusionKey;
    }

    public void setExclusionKey(String exclusionKey) {
        this.exclusionKey = exclusionKey;
    }

    public String getExclusionValue() {
        return exclusionValue;
    }

    public void setExclusionValue(String exclusionValue) {
        this.exclusionValue = exclusionValue;
    }
}
