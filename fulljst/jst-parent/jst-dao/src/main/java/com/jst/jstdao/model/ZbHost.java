package com.jst.jstdao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "t_zb_host")
public class ZbHost {

    @Id
    @GeneratedValue(generator = "user-uuid")
    @GenericGenerator(name = "user-uuid", strategy = "uuid")
    private String id;
    private String ip;
    private String host;
    private String hostid;
    private String dc;
    private Timestamp updateTime;
    private Timestamp createTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getHost() {
        return host;
    }

    public String getHostid() {
        return hostid;
    }

    public String getDc() {
        return dc;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
