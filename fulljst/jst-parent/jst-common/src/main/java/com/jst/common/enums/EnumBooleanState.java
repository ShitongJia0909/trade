package com.jst.common.enums;

public enum EnumBooleanState {

    FALSE(0),
    TRUE(1);

    private int value;

    EnumBooleanState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
