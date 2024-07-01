package com.nightbeer.build;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.nightbeer.dao.dados;
import com.nightbeer.view.mAdmin;
import com.nightbeer.view.mPrincipal;


public class BuildMenuLogin {
    BuildMethods buildMethod = new BuildMethods();
    
    private JButton buttonClose = buildMethod.createButton("X", 2.4, 2.6);
    private JButton buttonSend = buildMethod.createButton("Enviar", 5, 3);
    
    private JLabel labelUser = buildMethod.createLabel("Usuario", 4, 3);
    private JLabel labelPassword = buildMethod.createLabel("Senha", 4, 3);
    private JLabel labelPasswordError = buildMethod.createLabel("Senha errada, tente novamente.", 10, 3); 
    private JTextField textFieldUser = buildMethod.createTextField("Usuario", 12, 3);  
    private JTextField textFieldPassword = buildMethod.createTextField("Password", 12, 3);      
    
    private Color colorBackground = buildMethod.colorBackground;
    private Color colorButtonClose = buildMethod.colorButtonClose;

    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlainSmall;
    
    private JPanel panelMain;
    
    public JPanel buildLoginPanel(JFrame frame) {
    	openFrame(frame);
    	initialize(frame);
    	editComponents();
    	blockingPanel(frame);
    	
        // Create and setup the panel
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        // Add components to the panel

        contentPane.add(panelMain);
        return contentPane;
    }
    
    public JPanel blockingPanel(JFrame frame) {
    	panelMain = new JPanel(new BorderLayout());
    	panelMain.setBackground(colorBackground);
    	
    	JPanel topContainer = new JPanel(new BorderLayout());
    	topContainer.add(buttonClose, BorderLayout.LINE_END);
    	topContainer.setBackground(colorBackground);
    	
	   	JPanel centerContainer = new JPanel(new FlowLayout());
	    centerContainer.setBackground(colorBackground);
    	
    	JPanel blocking = new JPanel();
    	blocking.setLayout(new BorderLayout());
    
    	JPanel componentsLogin = new JPanel(new GridLayout(5, 1));
    	componentsLogin.add(labelUser);
    	componentsLogin.add(textFieldUser);
    	componentsLogin.add(labelPassword);
    	componentsLogin.add(textFieldPassword);
    	componentsLogin.add(labelPasswordError);
    	
    	JPanel bottomContainer = new JPanel(new BorderLayout());
    	bottomContainer.setBackground(colorBackground);
    	bottomContainer.add(buttonSend, BorderLayout.EAST);
    	
    	
    	blocking.add(componentsLogin, BorderLayout.CENTER);
    	blocking.add(bottomContainer, BorderLayout.SOUTH);
    	
    	centerContainer.add(blocking);
    	
    	panelMain.add(topContainer, BorderLayout.NORTH);
    	panelMain.add(centerContainer, BorderLayout.CENTER);
    	return panelMain;
    }
    
    public void editComponents() {
    	
    	buttonClose.setBackground(colorButtonClose);
    	buttonClose.setFont(FontRobotoPlainSmall);
        labelPasswordError.setForeground(colorBackground);
        
    }
    
    public void initialize(JFrame frame) {
        buttonClose.addActionListener(e -> frame.dispose());
        
        buttonSend.addActionListener(e -> {
            dados msDados = new dados();
            if (!msDados.validUser(textFieldUser.getText(), textFieldPassword.getText())) {
                textFieldUser.setText("");
                textFieldPassword.setText("");
                textFieldUser.requestFocusInWindow();
                labelPasswordError.setForeground(new Color(200, 0, 0));
            } else {
                mAdmin mAdmin = new mAdmin();
                mAdmin.setVisible(true);
                mAdmin.setBounds(BuildMethods.bounds);
              
                // close login menu
                frame.dispose();
                
                // close menu principal
                mPrincipal.getInstance().getFrame().dispose();
            }
        });
        
    }
    
    public void openFrame(JFrame frame) {
    	frame.addWindowListener(new WindowAdapter() {
    	    public void windowOpened( WindowEvent e ){
    	    	textFieldUser.requestFocus();
    	    }
		});
    }
}

