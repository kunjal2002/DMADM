package com.vit.it.hms;

public class DiseaseInfo {

    public int getDISEASE_ID() {
        return DISEASE_ID;
    }

    public DiseaseInfo(int DISEASE_ID, String NAME) {
        this.DISEASE_ID = DISEASE_ID;
        this.NAME = NAME;
    }

    public void setDISEASE_ID(int DISEASE_ID) {
        this.DISEASE_ID = DISEASE_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    int DISEASE_ID;
    String NAME;

}
