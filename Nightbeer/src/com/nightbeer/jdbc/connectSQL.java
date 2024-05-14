package com.nightbeer.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class connectSQL {
    final private String url = "jdbc:mysql://localhost/sqlinventorynightbeer";
    final private String usuario = "root";
    final private String senha = "";


    public Connection getConnect(){
        try {
            return DriverManager.getConnection(url,usuario,senha);
        } catch (SQLException e) {            
            JOptionPane.showMessageDialog(null, "connection error"+e);
        }
        return null;
    }
}
