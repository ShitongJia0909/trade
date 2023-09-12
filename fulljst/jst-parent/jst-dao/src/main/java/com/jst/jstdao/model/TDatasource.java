package com.jst.jstdao.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_datasource")
public class TDatasource {

    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    private String id;
    private String name;
    private String code;
    private String groupCode;
    private String kafkaTopic;
    private String esTable;
    private String timeAtt;
    private Date createTime;
    private Date modiftyTime;
    private String description;
    private String fieldDesc;
    private String fieldKey;
    private String multiFilter;
    private int type;

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getMultiFilter() {
        return multiFilter;
    }

    public void setMultiFilter(String multiFilter) {
        this.multiFilter = multiFilter;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public String getEsTable() {
        return esTable;
    }

    public void setEsTable(String esTable) {
        this.esTable = esTable;
    }

    public String getTimeAtt() {
        return timeAtt;
    }

    public void setTimeAtt(String timeAtt) {
        this.timeAtt = timeAtt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModiftyTime() {
        return modiftyTime;
    }

    public void setModiftyTime(Date modiftyTime) {
        this.modiftyTime = modiftyTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
