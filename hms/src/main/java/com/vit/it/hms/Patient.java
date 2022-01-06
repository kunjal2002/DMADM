package com.vit.it.hms;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Patient {

    public Patient(int PATIENT_ID, String NAME, String DOB) {
        this.PATIENT_ID = PATIENT_ID;
        this.NAME = NAME;
        this.DOB = DOB;
    }

    public int getPATIENT_ID() {
        return PATIENT_ID;
    }

    public void setPATIENT_ID(int PATIENT_ID) {
        this.PATIENT_ID = PATIENT_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getLASTUPDATED() {
        return LASTUPDATED;
    }

    public void setLASTUPDATED(String LASTUPDATED) {
        this.LASTUPDATED = LASTUPDATED;
    }

    int PATIENT_ID;
    String NAME;
    String DOB;
    String LASTUPDATED;

}
