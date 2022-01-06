package com.vit.it.hms;

public class DoctorInfo {
    public int getDoctor_ID() {
        return DOC_ID;
    }

    public DoctorInfo(int DOC_ID, String NAME) {
        this.DOC_ID = DOC_ID;
        this.NAME = NAME;
    }

    public void setDoctor_ID(int DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    int DOC_ID;
    String NAME;
}
