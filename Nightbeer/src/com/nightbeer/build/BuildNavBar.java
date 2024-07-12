package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.nightbeer.view.mAdmin;
import com.nightbeer.view.mLogIn;
import com.nightbeer.view.mPrincipal;

public class BuildNavBar {
    private BuildMethods buildMethod = new BuildMethods();
    private Color colorTextWhite = buildMethod.colorTextWhite;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;
    

    private Color colorButton = buildMethod.colorButton;
    private Color colorButtonClose = buildMethod.colorButtonClose;
    
    private Font FontRobotoPlainLarge = buildMethod.FontRobotoPlain22;
    
    private JLabel labelNavBarTitle;
    private JButton buttonAccess;
    private JButton buttonMinimize;
    private JButton buttonExit;

    private JFrame frame;
    
    private JPanel painelNavBarWest; 
    private JPanel painelNavBarEast;
    
    public ImageIcon iconUser = buildMethod.createImage("../images/iconUserLogin.png", 35, 35);
    public ImageIcon iconBack = buildMethod.createImage("../images/iconGoBack.png", 35, 35);
    private JLabel icon = buildMethod.createIcon("../images/nightbeerIcon.jpg", 70, 70);
    
    public BuildNavBar() {
        initializeComponents();
        initialize();
    }
    
    private void initializeComponents() {
        // Inicialize todos os componentes aqui
        labelNavBarTitle = buildMethod.createLabel("", 22, 0, SwingConstants.LEFT, colorTextWhite, buildMethod.colorBackgroundBlack, FontRobotoPlainLarge, 0,0,0,0);
        buttonAccess = buildMethod.createButton("", 5.5, 5.5, SwingConstants.CENTER, colorTextWhite, colorBlackBackground);
        buttonMinimize = buildMethod.createButton("-", 2.2, 2.5, SwingConstants.CENTER, colorTextWhite, colorButton);
        buttonExit = buildMethod.createButton("X", 2.2, 2.5, SwingConstants.CENTER, colorTextWhite, colorButtonClose);
        
        buttonExit.setFont(buildMethod.FontRobotoPlain18);
        buttonMinimize.setFont(buildMethod.FontRobotoPlain18);
    }
    
    // Global NavBar Container
    public JPanel containerNavBar() {
        JPanel painelNavBar = buildMethod.createPanel(100, 6, new BorderLayout(), colorBackgroundWhite, 0,0,0,0);
        painelNavBar.setBackground(buildMethod.colorBackgroundBlack);
        
        painelNavBar.add(painelNavBarWest, BorderLayout.WEST);
        painelNavBar.add(painelNavBarEast, BorderLayout.EAST);

        return painelNavBar;
    }
    
    // Left Container
    public JPanel panelWest(String text) {
        painelNavBarWest = buildMethod.createPanel(30, 0, new BorderLayout(), colorBackgroundWhite, 0,0,0,0);
        painelNavBarWest.setBackground(colorBlackBackground);
        
        labelNavBarTitle.setText(text);

        painelNavBarWest.add(icon, BorderLayout.WEST);
        painelNavBarWest.add(labelNavBarTitle, BorderLayout.CENTER);
        return painelNavBarWest;
    }
    
    // Right Container
    public JPanel panelEast(ImageIcon icon) {
        painelNavBarEast = buildMethod.createPanel(20, 0, new FlowLayout(FlowLayout.RIGHT), colorBackgroundWhite, 0,0,0,0);
        painelNavBarEast.setBackground(buildMethod.colorBackgroundBlack);
        
        buttonAccess.setIcon(icon);
        
        painelNavBarEast.add(buttonAccess);
        painelNavBarEast.add(buttonMinimize);
        painelNavBarEast.add(buttonExit);
        return painelNavBarEast;
    }
    
    // Method for replace commands
    public void replaceFunctionButton() {
    	
    	// Checking visibility of the mainframe
        boolean visible = mPrincipal.getInstance().getVisibleFrame();
        
        // Checking for replace a button command to do open a Login Menu or Mainframe (mPrincipal)
        if (visible) {
            mLogIn mLogIn = new mLogIn();
            mLogIn.setVisible(true);
        } else {
            mPrincipal mPrincipal = new mPrincipal();
            mPrincipal.setVisible(true);
            mPrincipal.setBounds(BuildMethods.bounds);
            
            // close mAdmin frame
            mAdmin.getInstance().getFrame().dispose();
        }
    }
    
    // Method for starting item's functions
    public void initialize() {
        
        // Minimize Button
        buttonMinimize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.ICONIFIED);
            }
        });
        
        // Access Button
//        buttonAccess.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                replaceFunctionButton();
//            }
//        });
    }
    
    public void addCloseButtonListener(ActionListener listener) {
        buttonExit.addActionListener(listener);
    }
    
    public void addAcessButtonListener(ActionListener listener) {
    	buttonAccess.addActionListener(listener);
    }
    
    // Method for get a other frame
    public void getFrame(JFrame frame) {
        this.frame = frame;
    }
}
