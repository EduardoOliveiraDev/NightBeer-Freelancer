package com.nightbeer.jdbc;

import com.nightbeer.build.BuildMethods;
import com.nightbeer.view.mPrincipal;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class testConnection {

    public static void main(String[] args) {
        try {
        	
            new connectSQL().getConnect();
            mPrincipal Principal = new mPrincipal();
            Principal.setBounds(BuildMethods.bounds);
            Principal.setVisible(true);
            
        } catch (HeadlessException erro) {
            JOptionPane.showMessageDialog(null, "connection error"+ erro.getMessage());
        }
    }
    
}
