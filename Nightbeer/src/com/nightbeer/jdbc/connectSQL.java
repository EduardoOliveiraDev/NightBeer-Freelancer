package com.nightbeer.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class connectSQL {
    final private String JDBC_url = "jdbc:mysql://localhost/database_nightbeer";
    final private String JDBC_user = "root";
    final private String JDBC_password = "";
    
    String querry = "SELECT * FROM items";
    
    public Connection getConnect(){
        try {
        	Connection connectionSQL = DriverManager.getConnection(JDBC_url, JDBC_user, JDBC_password);     	
            return connectionSQL;
        } catch (Exception e) {            
            JOptionPane.showMessageDialog(null, "connection error " + e);
        }
        return null;
    }
}