package com.nightbeer.build;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import com.nightbeer.dao.dados;
import com.nightbeer.view.mAdmin;
import com.nightbeer.view.mPrincipal;

public class BuildMenuLogin {
    private BuildMethods buildMethod = new BuildMethods();
    
    private Color colorButton = buildMethod.colorButton;
    private Color colorButtonClose = buildMethod.colorButtonClose;
    private Color colorButtonGrey = buildMethod.colorButtonGrey;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorTextWhite = buildMethod.colorTextWhite;

    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
    
    private JPanel containerNavBar;
    private JButton buttonClose;

    private JPanel containerComponentes;
    private JLabel labelUser;
    private JLabel labelPassword;
    private JLabel labelPasswordError;
    private JTextField textFieldUser;
    private JPasswordField textFieldPassword;   
    
    private JPanel panelMain; 
    private JButton buttonSend;
    
    
    public JPanel buildLoginPanel(JFrame frame) {
        openFrame(frame);
        blockingPanel(frame);
        initialize(frame);
        
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(panelMain);
        return contentPane;
    }
    
    public JPanel blockingPanel(JFrame frame) {
        panelMain = new JPanel(new BorderLayout());
        panelMain.setBackground(colorBlackBackground);
        panelMain.add(navBarContainer(), BorderLayout.NORTH);
        panelMain.add(loginComponents(), BorderLayout.CENTER);
        return panelMain;
    }
    
    public JPanel navBarContainer() {
        containerNavBar = new JPanel(new BorderLayout());
        buttonClose = buildMethod.createButton("X", 2.4, 2.6, SwingConstants.CENTER, colorTextWhite, colorButtonClose);
        buttonClose.setFont(FontRobotoPlainSmall);
        
        containerNavBar.add(buttonClose, BorderLayout.EAST);
        containerNavBar.setBackground(colorBlackBackground);
        return containerNavBar;
    }
    
    public JPanel loginComponents() {
        containerComponentes = new JPanel(new FlowLayout());
        containerComponentes.setBackground(colorBlackBackground);
        
        JPanel blockingComponents = new JPanel(new BorderLayout());
        JPanel componentsLogin = new JPanel(new GridLayout(5, 1));
        
        labelUser = buildMethod.createLabel("Usuario", 4, 3, SwingConstants.LEFT, colorTextWhite, colorBlackBackground, FontRobotoPlainSmall, 0,0,0,0);
        labelPassword = buildMethod.createLabel("Senha", 4, 3, SwingConstants.LEFT, colorTextWhite, colorBlackBackground, FontRobotoPlainSmall, 0,0,0,0);
        labelPasswordError = buildMethod.createLabel("Senha errada, tente novamente.", 16, 3, SwingConstants.LEFT, colorBlackBackground, colorBlackBackground, FontRobotoPlainSmall, 0,0,0,0); 
        
        textFieldUser = buildMethod.createTextField("Usuario", 16, 3, SwingConstants.LEFT, colorTextWhite, colorButtonGrey, FontRobotoPlainSmall, 0,10,0,10);  
        textFieldPassword = buildMethod.createPasswordField("Password", 16, 3, SwingConstants.LEFT, colorTextWhite, colorButtonGrey, FontRobotoPlainSmall, 0,10,0,10);
        
        componentsLogin.add(labelUser);
        componentsLogin.add(textFieldUser);
        componentsLogin.add(labelPassword);
        componentsLogin.add(textFieldPassword);
        componentsLogin.add(labelPasswordError);
        
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBackground(colorBlackBackground);
        
        buttonSend = buildMethod.createButton("Enviar", 5, 3, SwingConstants.CENTER, colorTextWhite, colorButton);
        bottomContainer.add(buttonSend, BorderLayout.EAST);
        
        blockingComponents.add(componentsLogin, BorderLayout.CENTER);
        blockingComponents.add(bottomContainer, BorderLayout.SOUTH);
        
        containerComponentes.add(blockingComponents);
        return containerComponentes;
    }
     
    @SuppressWarnings("deprecation")
	public void initialize(JFrame frame) {
    	buttonClose.addActionListener(e -> frame.dispose());
        
        if (buttonSend != null) {
            buttonSend.addActionListener(e -> {
                dados msDados = new dados();
                if (!msDados.validUser(textFieldUser.getText(), textFieldPassword.getText())) {
                    textFieldUser.setText("");
                    textFieldPassword.setText("");
                    textFieldUser.requestFocusInWindow();
                    labelPasswordError.setForeground(new Color(200, 0, 0));
                } else {
                    mAdmin mAdmin = null;
                    try {
                        mAdmin = new mAdmin();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    mAdmin.setVisible(true);
                    mAdmin.setBounds(BuildMethods.bounds);
                  
                    // close login menu
                    frame.dispose();
                    
                    // close menu principal
                    mPrincipal.getInstance().getFrame().dispose();
                }
            });
        } else {
            System.err.println("Botão buttonSend não foi inicializado corretamente.");
        }
        
        frame.getRootPane().setDefaultButton(buttonSend);
    }
    
    public void openFrame(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                textFieldUser.requestFocus();
            }
        });
    }
}
