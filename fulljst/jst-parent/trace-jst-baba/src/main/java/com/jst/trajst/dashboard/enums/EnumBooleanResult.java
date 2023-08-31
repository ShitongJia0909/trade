package com.jst.trajst.dashboard.enums;

public enum EnumBooleanResult {

    TRUE(1),
    FALSE(0);

    private int value;
    EnumBooleanResult(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
