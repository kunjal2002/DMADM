package com.vit.it.hms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminController  implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button logoutButton;
    @FXML
    private Button addDoctorButton;
    @FXML
    private Button editDoctorButton;
    @FXML
    private Button  addPatientButton;
    @FXML
    private Button editPatientButton;
    @FXML
    private Button addHospitalButton;
    @FXML
    private Button editHospitalButton;

    //button will log you out back to login page
    public void logoutButtonPushed(ActionEvent actionEvent) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }

    public void addDoctorButtonPushed(ActionEvent actionEvent) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }


    public void editDoctorButtonPushed(ActionEvent actionEvent) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }

    public void addPatientButtonPushed(ActionEvent actionEvent) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("patient-details-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }

    public void editPatientButtonPushed(ActionEvent actionEvent) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }

    public void addHospitalButtonPushed(ActionEvent actionEvent) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }

    public void editHospitalButtonPushed(ActionEvent actionEvent) throws IOException {
        logoutButton.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(HmsApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("VIT - Hospital Management System - Admin");
        stage.setScene(scene);
        stage.show();
    }


}
