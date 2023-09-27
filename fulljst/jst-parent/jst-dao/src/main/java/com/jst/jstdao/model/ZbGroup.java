package com.jst.jstdao.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "t_zb_group")
public class ZbGroup {

    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    private String id;
    private String groupId;
    private String groupName;
    private String dc;
    private Timestamp modifyTime;
    private Timestamp pushTime;
    private Timestamp createTime;


    public void setId(String id) {
        this.id = id;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setPushTime(Timestamp pushTime) {
        this.pushTime = pushTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDc() {
        return dc;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public Timestamp getPushTime() {
        return pushTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
