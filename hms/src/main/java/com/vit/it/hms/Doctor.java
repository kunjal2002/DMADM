package com.vit.it.hms;

public class Doctor {




    public int getDOC_ID() {
        return DOC_ID;
    }

    public void setDOC_ID(int DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public Doctor(int DOC_ID, String NAME, String DEGREE, int DISEASE_ID, String DISEASE_NAME) {
        this.DOC_ID = DOC_ID;
        this.NAME = NAME;
        this.DEGREE = DEGREE;
        this.DISEASE_ID = DISEASE_ID;
        this.DISEASE_NAME = DISEASE_NAME;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDEGREE() {
        return DEGREE;
    }

    public void setDEGREE(String DEGREE) {
        this.DEGREE = DEGREE;
    }

    public int getDISEASE_ID() {
        return DISEASE_ID;
    }

    public void setDISEASE_ID(int DISEASE_ID) {
        this.DISEASE_ID = DISEASE_ID;
    }



    int DOC_ID;
    String NAME;
    String DEGREE;
    int DISEASE_ID;

    public String getDISEASE_NAME() {
        return DISEASE_NAME;
    }

    public void setDISEASE_NAME(String DISEASE_NAME) {
        this.DISEASE_NAME = DISEASE_NAME;
    }

    String DISEASE_NAME;

}
