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
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorDetailsController implements Initializable {

    public TableView<Doctor> tableDoctor;
    public TableColumn<Doctor, Integer> col_drID;
    public TableColumn<Doctor, String> col_drName;
    public TableColumn<Doctor, String> col_drDegree;
    public TableColumn<Doctor, String> col_drDisease;
    public TableColumn<Doctor, String> col_drDiseaseId;

    private ObservableList<Doctor> doctorList;
    private ObservableList<DiseaseInfo> diseaseList;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //load view
        doctorList = FXCollections.observableArrayList();

        //run these methods to load table at start of page
        loadDoctorTable();
        loadDoctorData();

        diseaseList = FXCollections.observableArrayList();
        loadDiseaseData();

    }

    @FXML
    private Button goBackButton;
    @FXML
    private Button addDocButton;
    @FXML
    private Button deleteDocButton;
    @FXML
    private TextField txtDrName;
    @FXML
    private TextField txtDrDegree;
    @FXML
    private ComboBox CBoxSpecialization;


    private void loadDiseaseData() {
        try {
            Connection conn = DBConnect.getConnection("hospitaldb");

            PreparedStatement prst = conn.prepareStatement("SELECT * FROM DISEASE_INFO ORDER BY DISEASE_ID");
            ResultSet rs = prst.executeQuery();

            while(rs.next()){
                DiseaseInfo p = new DiseaseInfo(rs.getInt("DISEASE_ID"),rs.getString("NAME"));
                diseaseList.add(p);
            }

            CBoxSpecialization.setItems(null);
            CBoxSpecialization.setItems(diseaseList);

            CBoxSpecialization.setConverter(new StringConverter<DiseaseInfo>() {
                @Override
                public String toString(DiseaseInfo p) {
                    return p.getNAME();
                }
                @Override
                public DiseaseInfo fromString(final String string) {
                    //TODO
                    return new DiseaseInfo(0, "");
                    //return CBoxSpecialization.getItems().stream().filter(p -> p.getName().equals(string)).findFirst().orElse(null);
                }
            });

        }catch (SQLException e) {
            MessagePopup.display("View refresh - loadDiseaseData", e.getMessage() + "" + e.toString());
        }
    }

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

    public void addDoctorAction(ActionEvent actionEvent) throws IOException {
        //get user input
        String dName = txtDrName.getText().toString();
        String dDeg = txtDrDegree.getText().toString();
        DiseaseInfo d = (DiseaseInfo) CBoxSpecialization.getValue();

        String dSpec = d.getNAME();
        //lookup DISEASE_ID from specilization selected
        int dDiseaseId = 0;
        dDiseaseId = d.getDISEASE_ID();

        if (dName.trim().equalsIgnoreCase("") || dDeg.trim().equalsIgnoreCase("")  || dSpec.trim().equalsIgnoreCase("") ){
            //error it out
            MessagePopup.display("Validation Error", "Name | Degree | Specialization cannot be empty");
            return;
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "INSERT INTO DOCTOR(NAME, DEGREE, DISEASE_ID) VALUES (?, ?, ?)";

        try{
            //get a connection with MySQL 'hospital' database
            Connection conn = DBConnect.getConnection("hospitaldb");
            pst = conn.prepareStatement(query);
            pst.setString(1, dName);
            pst.setString(2, dDeg);
            pst.setInt(3, dDiseaseId);

            int i = pst.executeUpdate();
            if( i == 1) {
                MessagePopup.display("Add Doctor Action", "Success!");
            } else {
                MessagePopup.display("Add Doctor Action", "Failure!");
            }

            //also update the table view
            

        }catch (Exception e){
            MessagePopup.display("Add Doctor Action", e.getMessage() + "" + e.toString());
        }

        //also now refresh the view window
        loadDoctorTable();
        loadDoctorData();
    }

    //then we LOAD THE DATA onto the tableview
    private void loadDoctorTable() {
        col_drID.setCellValueFactory(new PropertyValueFactory<>("DOC_ID"));
        col_drName.setCellValueFactory(new PropertyValueFactory<>("NAME"));
        col_drDegree.setCellValueFactory(new PropertyValueFactory<>("DEGREE"));
        col_drDisease.setCellValueFactory(new PropertyValueFactory<>("DISEASE_NAME"));
        col_drDiseaseId.setCellValueFactory(new PropertyValueFactory<>("DISEASE_ID"));
        //col_patLastUpdated.setCellValueFactory(new PropertyValueFactory<>("LASTUPDATED"));

        //set data to tableview
        tableDoctor.setItems(doctorList);
    }

    //LOAD PATIENT DATA from database
    private void loadDoctorData() {
        doctorList.clear();
        try {
            Connection conn = DBConnect.getConnection("hospitaldb");

            PreparedStatement prst = conn.prepareStatement("SELECT doc.DOC_ID, doc.NAME, doc.DEGREE, doc.DISEASE_ID, d.NAME AS DISEASE_NAME" +
                    " FROM DOCTOR doc INNER JOIN DISEASE_INFO d ON doc.DISEASE_ID = d.DISEASE_ID ORDER BY doc.DOC_ID");
            ResultSet rs = prst.executeQuery();

            while(rs.next()){
                Doctor p = new Doctor(rs.getInt("DOC_ID"),rs.getString("NAME"),
                        rs.getString("DEGREE"), rs.getInt("DISEASE_ID"), rs.getString("DISEASE_NAME"));
                doctorList.add(p);
            }
        }catch (SQLException e) {
            MessagePopup.display("View refresh", e.getMessage() + "" + e.toString());
        }

    }

    public void deleteDoctorAction(ActionEvent actionEvent) throws IOException {
            tableDoctor.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            Doctor selectedItem = tableDoctor.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {

                tableDoctor.getItems().remove(selectedItem);

                //get selected row
                int DOC_ID = selectedItem.getDOC_ID();

                if (DOC_ID == 0) {
                    //error it out
                    MessagePopup.display("Validation Error", "No item selected for delete...");
                    return;
                }

                PreparedStatement pst = null;
                ResultSet rs = null;

                //first delete the doctor rows from

                String query = "DELETE FROM DOCTOR WHERE DOC_ID = ? ";

                try {
                    //get a connection with MySQL 'hospital' database
                    Connection conn = DBConnect.getConnection("hospitaldb");
                    pst = conn.prepareStatement(query);
                    pst.setInt(1, DOC_ID);

                    int i = pst.executeUpdate();
                    if (i == 1) {
                        MessagePopup.display("Delete Doctor Action", "Success!");
                    } else {
                        MessagePopup.display("Delete Doctor Action", "Failure!");
                    }


                } catch (Exception e) {
                    MessagePopup.display("Delete Doctor Action", e.getMessage() + "" + e.toString());
                }


            /*
            // this is for multiple selection
            ObservableList<Patient> selectedRows = tablePatient.getSelectionModel().getSelectedItems();
            // we don't want to iterate on same collection on with we remove items
            ArrayList<Patient> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> tablePatient.getItems().remove(row));
             */


                loadDoctorTable();
                loadDoctorData();
            } else {
                MessagePopup.display("Delete Doctor Action", "Select row to delete!");
            }

    }


}
