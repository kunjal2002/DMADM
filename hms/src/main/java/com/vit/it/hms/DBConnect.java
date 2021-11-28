/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vit.it.hms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Kunjal Agrawal
 */
public class DBConnect {
    
    private static Connection conn;
    private static final String url = "jdbc:mysql://localhost:3306/hospitaldb?zeroDateTimeBehavior=convertToNull";
    private static final String user = "root";
    private static final String pass = "admin";
    
    public static Connection getConnection(String DbName) throws SQLException {

        Connection connect = null;
        try {
            // This will load the MySQL driver, each DB has its own driver
            //Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/" + DbName + "?user=root&password=admin");;

            return connect;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw e;
        } finally {
            return connect;
        }
        
    }
    
    public static Connection conn() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
        getConnection("hospitaldb");
        return conn;
 
    }
    
    
}
