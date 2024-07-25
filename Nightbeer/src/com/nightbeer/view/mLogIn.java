package com.nightbeer.view;

import javax.swing.*;

import com.nightbeer.build.BuildMenuLogin;
import com.nightbeer.build.BuildMethods;

import java.awt.*;

@SuppressWarnings("serial")
public class mLogIn extends JFrame {

    private JPanel contentPane = new JPanel(new BorderLayout());
    BuildMethods buildMethod = new BuildMethods();
    BuildMenuLogin builder = new BuildMenuLogin();
    
	private static mPrincipal instance;

    public mLogIn() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setTitle("LogIn");

//        this.setPreferredSize(buildMethod.createResponsive(20, 24));
        JPanel loginPanel = builder.buildLoginPanel(this);
        contentPane.add(loginPanel);

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
    }
    
	public static mPrincipal getInstance() {
	    return instance;
	}
	
	public JFrame getFrame() {
	    return this;
	}
     
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                mLogIn frame = new mLogIn();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
