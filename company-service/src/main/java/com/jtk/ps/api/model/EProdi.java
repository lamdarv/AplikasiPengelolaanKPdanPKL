package com.jtk.ps.api.model;

public enum EProdi {
    D3(0),
    D4(1);

    public final int id;

    EProdi(int i) {
        this.id = i;
    }

    public static EProdi valueOfId(int id) {
        for (EProdi e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}
