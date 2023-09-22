package com.jtk.ps.api.dto;

public enum EPoint {
    SANGAT_BAIK(5),
    BAIK(4),
    CUKUP(3),
    KURANG(2),
    SANGAT_KURANG(1);

    private final int value;

    EPoint(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
