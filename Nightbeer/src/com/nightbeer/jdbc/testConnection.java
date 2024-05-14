package com.nightbeer.jdbc;

import com.nightbeer.view.mPrincipal;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class testConnection {

    public static void main(String[] args) {
        try {
            new connectSQL().getConnect();
            mPrincipal Principal = new mPrincipal();
            Principal.setVisible(true);
            Principal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } catch (HeadlessException erro) {
            JOptionPane.showMessageDialog(null, "connection error"+ erro.getMessage());
        }
    }
    
}
