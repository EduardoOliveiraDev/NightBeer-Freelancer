package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
    
    private JLabel labelNavBarTitle = buildMethod.createLabel("", 22, 0);
    private JButton buttonAccess = buildMethod.createButton("", 5.5, 5.5);
    private JButton buttonMinimize = buildMethod.createButton("-", 2.2, 2.5);
    private JButton buttonExit = buildMethod.createButton("X", 2.2, 2.5);
    
    private Color colorBackground = buildMethod.colorBackground;
    private Color colorButtonClose = buildMethod.colorButtonClose;

    private JFrame frame;
    
    private JPanel painelNavBarWest; 
    private JPanel painelNavBarEast;
    
    public ImageIcon iconUser = buildMethod.createImage("../images/iconUserLogin.png", 35, 35);
    public ImageIcon iconBack = buildMethod.createImage("../images/iconGoBack.png", 35, 35);
    private JLabel icon = buildMethod.createIcon("../images/nightbeerIcon.jpg", 70, 70);
    
    public BuildNavBar() {
    	buttonExit.setFont(buildMethod.FontRobotoPlainMedium);
    	buttonMinimize.setFont(buildMethod.FontRobotoPlainMedium);
        initialize();
    }

    // Method for get a other frame
    public void getFrame(JFrame frame) {
        this.frame = frame;
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
    
    // Left Container
    public JPanel panelWest(String text) {
    	painelNavBarWest = new JPanel(new BorderLayout());
        painelNavBarWest.setBackground(colorBackground);
        
        labelNavBarTitle.setHorizontalAlignment(SwingConstants.LEFT);
        labelNavBarTitle.setBackground(colorBackground);
        labelNavBarTitle.setFont(buildMethod.FontRobotoPlainLarge);
        labelNavBarTitle.setText(text);

        painelNavBarWest.add(icon, BorderLayout.WEST);
        painelNavBarWest.add(labelNavBarTitle, BorderLayout.CENTER);
        return painelNavBarWest;
    }
    
    // Right Container
    public JPanel panelEast(ImageIcon icon) {
    	painelNavBarEast = new JPanel(new FlowLayout());
        painelNavBarEast.setBackground(buildMethod.colorBackground);
        
        buttonAccess.setIcon(icon);
        buttonAccess.setBackground(colorBackground);
        buttonExit.setBackground(colorButtonClose);
        
        painelNavBarEast.add(buttonAccess);
        painelNavBarEast.add(buttonMinimize);
        painelNavBarEast.add(buttonExit);
        return painelNavBarEast;
    }
    
    // Global NavBar Container
    public JPanel containerNavBar() {
        JPanel painelNavBar = new JPanel(new BorderLayout());
        painelNavBar.setBackground(buildMethod.colorBackground);
        
        painelNavBar.add(painelNavBarWest, BorderLayout.WEST);
        painelNavBar.add(painelNavBarEast, BorderLayout.EAST);

        return painelNavBar;
    }
    
    // Method for starting item's functions
    public void initialize() {
        
        // Exit Button
        buttonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();    
            }
        });
        
        // Minimize Button
        buttonMinimize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.ICONIFIED);
            }
        });
        
        // Access Button
        buttonAccess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                replaceFunctionButton();
            }
        });
    }
}
