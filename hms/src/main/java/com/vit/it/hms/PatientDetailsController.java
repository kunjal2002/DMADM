package com.vit.it.hms;

import com.dlsc.formsfx.model.structure.IntegerField;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class PatientDetailsController implements Initializable {

    public TableView<Patient> tablePatient;
    public TableColumn<Patient, Integer> col_patID;
    public TableColumn<Patient, String> col_patName;
    public TableColumn<Patient, String> col_patDOB;
    public TableColumn<Patient, String> col_patLastUpdated;

    private ObservableList<Patient> patientList;
    private ObservableList<DoctorInfo> doctorList;

    /**
     * Initializes the controller class.
     */
    public static final LocalDate NOW_LOCAL_DATE (){
        //default to todays date
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patDOB.setValue(NOW_LOCAL_DATE());

        //load view
        patientList = FXCollections.observableArrayList();

        //run these methods to load table at start of page
        loadPatientTable();
        loadPatientData();

        doctorList = FXCollections.observableArrayList();
        loadDoctorData();
    }


    @FXML
    private Button goBackButton;
    @FXML
    private Button bookAppButton;
    @FXML
    private Button addPatientButton;
    @FXML
    private Button deletePatientButton;
    @FXML
    private DatePicker patDOB;
    @FXML
    private TextField patName;
    @FXML
    private DatePicker appdate;
    @FXML
    private TextField doctname;
    @FXML
    private ComboBox CBoxSpecialization;


    //button will log you out back to login page
    public void goBackAction(ActionEvent actionEvent) throws IOException {
        goBackButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }

    public void addPatientAction(ActionEvent actionEvent) throws IOException {
        //get user input
        String pName = patName.getText().toString();

        if (pName.trim().equalsIgnoreCase("")){
            //error it out
            MessagePopup.display("Validation Error", "Name cannot be empty");
            return;
        }
        LocalDate dt = patDOB.getValue();
        String pDOB = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        PreparedStatement pst = null;
        ResultSet rs = null;
        String query = "INSERT INTO PATIENT(NAME, DOB) VALUES (?, ?) ";

        try{
            //get a connection with MySQL 'hospital' database
            Connection conn = DBConnect.getConnection("hospitaldb");
            pst = conn.prepareStatement(query);
            pst.setString(1, pName);
            pst.setString(2, pDOB);

            int i = pst.executeUpdate();
            if( i == 1) {
                MessagePopup.display("Add Patient Action", "Success!");
            } else {
                MessagePopup.display("Add Patient Action", "Failure!");
            }

            //also update the table view
            

        }catch (Exception e){
            MessagePopup.display("Add Patient Action", e.getMessage() + "" + e.toString());
        }

        //also now refresh the view window
        loadPatientTable();
        loadPatientData();
    }




    private void loadDoctorData() {
        try {
            Connection conn = DBConnect.getConnection("hospitaldb");

            PreparedStatement prst = conn.prepareStatement("SELECT * FROM DOCTOR ORDER BY DOC_ID");
            ResultSet rs = prst.executeQuery();

            while(rs.next()){
                DoctorInfo p = new DoctorInfo(rs.getInt("DOC_ID"),rs.getString("NAME"));
                doctorList.add(p);
            }

            CBoxSpecialization.setItems(null);
            CBoxSpecialization.setItems(doctorList);

            CBoxSpecialization.setConverter(new StringConverter<DoctorInfo>() {
                @Override
                public String toString(DoctorInfo p) {
                    return p.getNAME();
                }
                @Override
                public DoctorInfo fromString(final String string) {
                    //TODO
                    return new DoctorInfo(0, "");
                    //return CBoxSpecialization.getItems().stream().filter(p -> p.getName().equals(string)).findFirst().orElse(null);
                }
            });

        }catch (SQLException e) {
            MessagePopup.display("View refresh - loadDoctorData", e.getMessage() + "" + e.toString());
        }
    }






    public void bookAppAction(ActionEvent actionEvent) throws IOException {

        //deletePatientButton.setOnAction(ev -> {
        tablePatient.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Patient selectedItem = tablePatient.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {

            tablePatient.getItems().remove(selectedItem);

            //get selected row
            int PATIENT_ID = selectedItem.getPATIENT_ID();

            if (PATIENT_ID == 0) {
                //error it out
                MessagePopup.display("Validation Error", "No item selected for appointment booking...");
                return;
            }

            //String dName = doctname.getText().toString();


            DoctorInfo d = (DoctorInfo) CBoxSpecialization.getValue();
            String dName = d.getNAME();
            //lookup DISEASE_ID from specilization selected
            int dDoctorId = 0;
            dDoctorId = d.getDoctor_ID();

            if (dName.trim().equalsIgnoreCase("")){
                //error it out
                MessagePopup.display("Validation Error", "Name cannot be empty");
                return;
            }
            LocalDate dt = appdate.getValue();
            String appD = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


            PreparedStatement pst = null;
            ResultSet rs = null;
            String query = "INSERT INTO APPOINTMENTS(patientID, dName, appDate) VALUES (?, ?, ?) ";

            try{
                //get a connection with MySQL 'hospital' database
                Connection conn = DBConnect.getConnection("hospitaldb");
                pst = conn.prepareStatement(query);
                pst.setInt(1, PATIENT_ID);
                pst.setString(2, dName);
                pst.setString(3, appD);

                int i = pst.executeUpdate();
                if( i == 1) {
                    MessagePopup.display("Book Appointment Action", "Appointment booked!");
                } else {
                    MessagePopup.display("Book Appointment Action", "Failure!");
                }

                //also update the table view


            }catch (Exception e){
                MessagePopup.display("Book Appointment Action", e.getMessage() + "" + e.toString());
                System.out.println(e);
            }



            loadPatientTable();
            loadPatientData();
        } else {
            MessagePopup.display("Book Appointment Action", "Select row to book!");
        }

    }


    //then we LOAD THE DATA onto the tableview
    private void loadPatientTable() {
        col_patID.setCellValueFactory(new PropertyValueFactory<>("PATIENT_ID"));
        col_patName.setCellValueFactory(new PropertyValueFactory<>("NAME"));
        col_patDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        //col_patLastUpdated.setCellValueFactory(new PropertyValueFactory<>("LASTUPDATED"));

        //set data to tableview
        tablePatient.setItems(patientList);

    }

    //LOAD PATIENT DATA from database
    private void loadPatientData() {
        patientList.clear();
        try {
            Connection conn = DBConnect.getConnection("hospitaldb");

            PreparedStatement prst = conn.prepareStatement("SELECT * FROM patient ORDER BY PATIENT_ID");
            ResultSet rs = prst.executeQuery();

            while(rs.next()){
                Patient p = new Patient(rs.getInt("PATIENT_ID"),rs.getString("NAME"), rs.getString("DOB"));
                patientList.add(p);
            }
        }catch (SQLException e) {
            MessagePopup.display("View Refresh", e.getMessage() + "" + e.toString());
        }

    }

    public void deletePatientAction(ActionEvent actionEvent) throws IOException {

        //deletePatientButton.setOnAction(ev -> {
            tablePatient.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            Patient selectedItem = tablePatient.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {

                tablePatient.getItems().remove(selectedItem);

                //get selected row
                int PATIENT_ID = selectedItem.getPATIENT_ID();

                if (PATIENT_ID == 0) {
                    //error it out
                    MessagePopup.display("Validation Error", "No item selected for delete...");
                    return;
                }

                PreparedStatement pst = null;
                ResultSet rs = null;
                String query = "DELETE FROM PATIENT WHERE PATIENT_ID = ? ";

                try {
                    //get a connection with MySQL 'hospital' database
                    Connection conn = DBConnect.getConnection("hospitaldb");
                    pst = conn.prepareStatement(query);
                    pst.setInt(1, PATIENT_ID);

                    int i = pst.executeUpdate();
                    if (i == 1) {
                        MessagePopup.display("Delete Patient Action", "Success!");
                    } else {
                        MessagePopup.display("Delete Patient Action", "Failure!");
                    }


                } catch (Exception e) {
                    MessagePopup.display("Delete Patient Action", e.getMessage() + "" + e.toString());
                }


            /*
            // this is for multiple selection
            ObservableList<Patient> selectedRows = tablePatient.getSelectionModel().getSelectedItems();
            // we don't want to iterate on same collection on with we remove items
            ArrayList<Patient> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> tablePatient.getItems().remove(row));
             */


                loadPatientTable();
                loadPatientData();
            } else {
                MessagePopup.display("Delete Patient Action", "Select row to delete!");
            }

    }


}
