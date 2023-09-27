package com.jst.trajst.dashboard.vo.zabbix.dto;

public class TrendResps {

    private String itemid;
    private Integer num;
    private String value;
    private String valueMin;
    private String valueMax;
    private String valueAvg;
    private String unit;
    private long clock;

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValueMin(String valueMin) {
        this.valueMin = valueMin;
    }

    public void setValueMax(String valueMax) {
        this.valueMax = valueMax;
    }

    public void setValueAvg(String valueAvg) {
        this.valueAvg = valueAvg;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setClock(long clock) {
        this.clock = clock;
    }

    public String getItemid() {
        return itemid;
    }

    public Integer getNum() {
        return num;
    }

    public String getValue() {
        return value;
    }

    public String getValueMin() {
        return valueMin;
    }

    public String getValueMax() {
        return valueMax;
    }

    public String getValueAvg() {
        return valueAvg;
    }

    public String getUnit() {
        return unit;
    }

    public long getClock() {
        return clock;
    }
}
