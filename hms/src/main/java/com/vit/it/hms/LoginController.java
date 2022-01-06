/*
 * If MySQL time zone is off, run this in MySQL workbench: 

            SET GLOBAL time_zone = '+3:00';

 */
package com.vit.it.hms;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author Kunjal Agrawal
 */
public class LoginController implements Initializable {
    //add Login page controllers
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private RadioButton usertype1;
    @FXML
    private RadioButton usertype2;
    @FXML
    private RadioButton usertype3;
    @FXML
    private Button login;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //log user in system when login button is pressed
    @FXML
    public void loginAction(ActionEvent e) throws IOException{
        //get user input
        String username = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        boolean ut1 = usertype1.isSelected();
        boolean ut2 = usertype2.isSelected();
        String ut = "A";

        if (ut1) {
            ut = "P";
        }
        else if(ut2){
            ut = "D";
        }

        //MessagePopup.display("Login Credentials", username + "-" + password + "-" + ut);

        //uses check to see if the data is correct to login successfully
        if(check(username, password, ut)){
            MessagePopup.display("Login Status", "Login Success!");
             
            login.getScene().getWindow().hide();
            Stage stage = new Stage();

            //now we are able to login, based on the User logged in open different pages
            Parent root = null;

            if (ut.equalsIgnoreCase("P")) {
                //load patient related page
                root = FXMLLoader.load(HmsApplication.class.getResource("patient-details-view.fxml"));
                stage.setTitle("VIT - Hospital Management System - Patient");
            }else if (ut.equalsIgnoreCase("D")) {
                //load doctor related page
                root = FXMLLoader.load(HmsApplication.class.getResource("doctor-details-view.fxml"));
                stage.setTitle("VIT - Hospital Management System - Doctor");
            } else {
                //load admin page
                root = FXMLLoader.load(HmsApplication.class.getResource("admin-view.fxml"));
                stage.setTitle("VIT - Hospital Management System - Admin");
            }

            Scene scene = new Scene(root, 960, 720);
            stage.setScene(scene);
            stage.show();
            //stage.setResizable(false);
        }
        else{
            MessagePopup.display("Login Status", "Username/password is wrong. Try again!");
        }
        

        
    }
    
    //this will check to see if the user account is stored in the database
    public static boolean check(String usrnm, String psswrd, String ut){
         PreparedStatement pst = null;
         ResultSet rs = null;
         String query = "SELECT LOGIN_ID, PWD, USER_TYPE FROM LOGIN_INFO WHERE LOGIN_ID=? AND PWD=? AND USER_TYPE=? ";
         
         try{
             //get a connection with MySQL 'hospital' database
             //Class.forName("com.mysql.jdbc.Driver");
             Connection conn = DBConnect.getConnection("hospitaldb");
             pst = conn.prepareStatement(query); 
             pst.setString(1,usrnm);
             pst.setString(2,psswrd);
             pst.setString(3,ut);
             rs = pst.executeQuery();

             //MessagePopup.display("Login Status", "DB connection successful");


             if(rs.next()){
                 String LOGIN_ID = rs.getString("LOGIN_ID");
                 String PWD = rs.getString("PWD");
                 String USER_TYPE = rs.getString("USER_TYPE");

                 //MessagePopup.display("Rows", LOGIN_ID + " " + PWD  + " " + USER_TYPE);

                 return true;
             }
             else{
                 return false;
             }
         }catch (Exception e){
             MessagePopup.display("Login Status", e.getMessage() + "" + e.toString());
             return false;
         }
    }

}
