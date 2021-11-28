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

public class HospitalDetailsController implements Initializable {

    public TableView<Hospital> tableHospital;
    public TableColumn<Hospital, Integer> col_hospID;
    public TableColumn<Hospital, String> col_hospName;
    public TableColumn<Hospital, String> col_hospAdd;
    public TableColumn<Hospital, String> col_hospSpecs;

    private ObservableList<Hospital> hospitalList;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //load view
        hospitalList = FXCollections.observableArrayList();

        //run these methods to load table at start of page
        loadHospitalTable();
        loadHospitalData();
    }

    @FXML
    private Button goBackButton;
    @FXML
    private Button addHospitalButton;
    @FXML
    private Button deleteHospitalButton;
    @FXML
    private TextField hospName;
    @FXML
    private TextField hospAdd;
    @FXML
    private TextField hospSpecs;

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

    public void addHospitalAction(ActionEvent actionEvent) throws IOException {
        //get user input
        String hName = hospName.getText().toString();
        String hAdd = hospAdd.getText().toString();
        String hSpecs = hospSpecs.getText().toString();

        if (hName.trim().equalsIgnoreCase("") || hName.trim().equalsIgnoreCase("")  || hSpecs.trim().equalsIgnoreCase("") ){
            //error it out
            MessagePopup.display("Validation Error", "Name | Address | Specialization cannot be empty");
            return;
        }

        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "INSERT INTO HOSPITAL(NAME, ADDRESS, SPECIALIZATION) VALUES (?, ?, ?)";

        try{
            //get a connection with MySQL 'hospital' database
            Connection conn = DBConnect.getConnection("hospitaldb");
            pst = conn.prepareStatement(query);
            pst.setString(1, hName);
            pst.setString(2, hAdd);
            pst.setString(3, hSpecs);

            int i = pst.executeUpdate();
            if( i == 1) {
                MessagePopup.display("Add Hospital Action", "Success!");
            } else {
                MessagePopup.display("Add Hospital Action", "Failure!");
            }

            //also update the table view
            

        }catch (Exception e){
            MessagePopup.display("Add Hospital Action", e.getMessage() + "" + e.toString());
        }

        //also now refresh the view window
        loadHospitalTable();
        loadHospitalData();
    }

    //then we LOAD THE DATA onto the tableview
    private void loadHospitalTable() {
        col_hospID.setCellValueFactory(new PropertyValueFactory<>("HOSPITAL_ID"));
        col_hospName.setCellValueFactory(new PropertyValueFactory<>("NAME"));
        col_hospAdd.setCellValueFactory(new PropertyValueFactory<>("ADDRESS"));
        col_hospSpecs.setCellValueFactory(new PropertyValueFactory<>("SPECIALIZATION"));
        //col_patLastUpdated.setCellValueFactory(new PropertyValueFactory<>("LASTUPDATED"));

        //set data to tableview
        tableHospital.setItems(hospitalList);
    }

    //LOAD PATIENT DATA from database
    private void loadHospitalData() {
        hospitalList.clear();
        try {
            Connection conn = DBConnect.getConnection("hospitaldb");

            PreparedStatement prst = conn.prepareStatement("SELECT * FROM hospital ORDER BY HOSPITAL_ID");
            ResultSet rs = prst.executeQuery();

            while(rs.next()){
                Hospital p = new Hospital(rs.getInt("HOSPITAL_ID"),rs.getString("NAME"), rs.getString("ADDRESS"), rs.getString("SPECIALIZATION"));
                hospitalList.add(p);
            }
        }catch (SQLException e) {
            MessagePopup.display("View refresh", e.getMessage() + "" + e.toString());
        }

    }

    public void deleteHospitalAction(ActionEvent actionEvent) throws IOException {
            tableHospital.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            Hospital selectedItem = tableHospital.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {

                tableHospital.getItems().remove(selectedItem);

                //get selected row
                int HOSPITAL_ID = selectedItem.getHOSPITAL_ID();

                if (HOSPITAL_ID == 0) {
                    //error it out
                    MessagePopup.display("Validation Error", "No item selected for delete...");
                    return;
                }

                PreparedStatement pst = null;
                ResultSet rs = null;
                String query = "DELETE FROM HOSPITAL WHERE HOSPITAL_ID = ? ";

                try {
                    //get a connection with MySQL 'hospital' database
                    Connection conn = DBConnect.getConnection("hospitaldb");
                    pst = conn.prepareStatement(query);
                    pst.setInt(1, HOSPITAL_ID);

                    int i = pst.executeUpdate();
                    if (i == 1) {
                        MessagePopup.display("Delete Hospital Action", "Success!");
                    } else {
                        MessagePopup.display("Delete Hospital Action", "Failure!");
                    }


                } catch (Exception e) {
                    MessagePopup.display("Delete Hospital Action", e.getMessage() + "" + e.toString());
                }


            /*
            // this is for multiple selection
            ObservableList<Patient> selectedRows = tablePatient.getSelectionModel().getSelectedItems();
            // we don't want to iterate on same collection on with we remove items
            ArrayList<Patient> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> tablePatient.getItems().remove(row));
             */


                loadHospitalTable();
                loadHospitalData();
            } else {
                MessagePopup.display("Delete Hospital Action", "Select row to delete!");
            }

    }


}
