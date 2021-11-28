package com.vit.it.hms;

public class DoctorHosp {

    int DOC_ID;
    int HOSPITAL_ID;
    String DOC_NAME;
    String HOSP_NAME;

    public DoctorHosp(int DOC_ID, String DOC_NAME, int HOSPITAL_ID, String HOSP_NAME) {
        this.DOC_ID = DOC_ID;
        this.HOSPITAL_ID = HOSPITAL_ID;
        this.DOC_NAME = DOC_NAME;
        this.HOSP_NAME = HOSP_NAME;
    }

    public int getDOC_ID() {
        return DOC_ID;
    }

    public void setDOC_ID(int DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public int getHOSPITAL_ID() {
        return HOSPITAL_ID;
    }

    public void setHOSPITAL_ID(int HOSPITAL_ID) {
        this.HOSPITAL_ID = HOSPITAL_ID;
    }

    public String getDOC_NAME() {
        return DOC_NAME;
    }

    public void setDOC_NAME(String DOC_NAME) {
        this.DOC_NAME = DOC_NAME;
    }

    public String getHOSP_NAME() {
        return HOSP_NAME;
    }

    public void setHOSP_NAME(String HOSP_NAME) {
        this.HOSP_NAME = HOSP_NAME;
    }
}
