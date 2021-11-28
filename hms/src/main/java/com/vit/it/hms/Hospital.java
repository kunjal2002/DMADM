package com.vit.it.hms;

public class Hospital {


    public Hospital(int HOSPITAL_ID, String NAME, String ADDRESS, String SPECIALIZATION) {
        this.HOSPITAL_ID = HOSPITAL_ID;
        this.NAME = NAME;
        this.ADDRESS = ADDRESS;
        this.SPECIALIZATION = SPECIALIZATION;
    }

    public int getHOSPITAL_ID() {
        return HOSPITAL_ID;
    }

    public void setHOSPITAL_ID(int HOSPITAL_ID) {
        this.HOSPITAL_ID = HOSPITAL_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getSPECIALIZATION() {
        return SPECIALIZATION;
    }

    public void setSPECIALIZATION(String SPECIALIZATION) {
        this.SPECIALIZATION = SPECIALIZATION;
    }

    int HOSPITAL_ID;
    String NAME;
    String ADDRESS;
    String SPECIALIZATION;

}
