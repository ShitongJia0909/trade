package com.jst.trajst.dashboard.enums;

public enum EnumTaskStatus {

    INACTIVE(0),
    RUNNING(1),

    DISABLE(0),
    ENABLE(1);

    private int value;

    EnumTaskStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
