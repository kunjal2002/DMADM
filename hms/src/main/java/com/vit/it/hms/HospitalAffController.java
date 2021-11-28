package com.vit.it.hms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HospitalAffController implements Initializable {

    public TableView<DoctorHosp> tableDoctorHosp;
    public TableColumn<DoctorHosp, Integer> col_drID;
    public TableColumn<DoctorHosp, String> col_drName;
    public TableColumn<DoctorHosp, String> col_hospID;
    public TableColumn<DoctorHosp, String> col_hospName;

    private ObservableList<DoctorHosp> doctorHospList;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //load view
        doctorHospList = FXCollections.observableArrayList();

        //run these methods to load table at start of page
        loadDoctorHospTable();
        loadDoctorHospData();
    }

    @FXML
    private Button goBackButton;


    //button will log you out back to login page
    public void goBackAction(ActionEvent actionEvent) throws IOException {
        goBackButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("admin-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }

    //then we LOAD THE DATA onto the tableview
    private void loadDoctorHospTable() {
        col_drID.setCellValueFactory(new PropertyValueFactory<>("DOC_ID"));
        col_drName.setCellValueFactory(new PropertyValueFactory<>("DOC_NAME"));
        col_hospID.setCellValueFactory(new PropertyValueFactory<>("HOSPITAL_ID"));
        col_hospName.setCellValueFactory(new PropertyValueFactory<>("HOSP_NAME"));

        //set data to tableview
        tableDoctorHosp.setItems(doctorHospList);
    }

    //LOAD PATIENT DATA from database
    private void loadDoctorHospData() {
        doctorHospList.clear();
        try {
            Connection conn = DBConnect.getConnection("hospitaldb");

            PreparedStatement prst = conn.prepareStatement("SELECT hda.DOC_ID, d.NAME AS DOC_NAME, hda.HOSPITAL_ID, h.NAME AS HOSP_NAME " +
                    " FROM HOSPITAL_DOCTOR_AFFILIATION hda LEFT JOIN HOSPITAL h ON hda.HOSPITAL_ID = h.HOSPITAL_ID" +
                    " LEFT JOIN DOCTOR d ON hda.DOC_ID = d.DOC_ID");
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                DoctorHosp p = new DoctorHosp(rs.getInt("DOC_ID"), rs.getString("DOC_NAME"),
                        rs.getInt("HOSPITAL_ID"), rs.getString("HOSP_NAME"));
                doctorHospList.add(p);
            }
        } catch (SQLException e) {
            MessagePopup.display("View refresh", e.getMessage() + "" + e.toString());
        }

    }


}


