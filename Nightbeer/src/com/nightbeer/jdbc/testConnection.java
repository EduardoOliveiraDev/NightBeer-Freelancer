package com.nightbeer.jdbc;

import com.nightbeer.build.BuildMethods;
import com.nightbeer.view.mPrincipal;
import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class testConnection {

    public static void main(String[] args) throws SQLException {
        try {
        	
            new connectionSQL().getConnect();
            mPrincipal Principal = new mPrincipal();
            Principal.setBounds(BuildMethods.bounds);
            Principal.setVisible(true);
            
        } catch (HeadlessException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar-se ao banco de dados");
        }
    }
    
}
