package com.jtk.ps.api.model;

public enum ERole {
    COMMITTEE(0),
    PARTICIPANT(1),
    COMPANY(2),
    HEAD_STUDY_PROGRAM(3);


    public final int id;

    ERole(int i) {
        this.id = i;
    }

    public static ERole valueOfId(int id) {
        for (ERole e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }
}