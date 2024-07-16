package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.nightbeer.view.mAdmin;
import com.nightbeer.view.mLogIn;
import com.nightbeer.view.mHistoricTableBuy;
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
    private JButton buttonHistoric;
    private JButton buttonAccess;
    private JButton buttonMinimize;
    private JButton buttonExit;

    private JFrame frame;
    
    private JPanel painelLogoAndTitle; 
    private JPanel painelButtons;
    
    public ImageIcon iconUser = buildMethod.createImage("../images/iconUserLogin.png", 35, 35);
    public ImageIcon iconBack = buildMethod.createImage("../images/iconGoBack.png", 35, 35);
    public ImageIcon iconHistoric  = buildMethod.createImage("../images/iconHistoric.png", 35, 35);
    private JLabel icon = buildMethod.createIcon("../images/nightbeerIcon.jpg", 70, 70);
    
    public BuildNavBar() {
        initializeComponents();
        initialize();
    }
     
    private void initializeComponents() {
        labelNavBarTitle = buildMethod.createLabel("", 22, 0, SwingConstants.LEFT, colorTextWhite, buildMethod.colorBackgroundBlack, FontRobotoPlainLarge, 0,0,0,0);
        buttonAccess = buildMethod.createButton("", 5, 5.5, SwingConstants.CENTER, colorTextWhite, colorBlackBackground);
        buttonAccess.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 40, colorBlackBackground));
        buttonHistoric = buildMethod.createButton("", 3, 5.5, SwingConstants.CENTER, colorTextWhite, colorBlackBackground);
        buttonMinimize = buildMethod.createButton("-", 2.2, 2.5, SwingConstants.CENTER, colorTextWhite, colorButton);
        buttonExit = buildMethod.createButton("X", 2.2, 2.5, SwingConstants.CENTER, colorTextWhite, colorButtonClose);
        
        buttonExit.setFont(buildMethod.FontRobotoPlain18);
        buttonMinimize.setFont(buildMethod.FontRobotoPlain18);
    }
    
    public JPanel containerNavBar() {
        JPanel painelNavBar = buildMethod.createPanel(100, 6, new BorderLayout(), colorBackgroundWhite, 0,0,0,0);
        painelNavBar.setBackground(buildMethod.colorBackgroundBlack);
        
        painelNavBar.add(painelLogoAndTitle, BorderLayout.WEST);
        painelNavBar.add(painelButtons, BorderLayout.EAST);
        return painelNavBar;
    }
    
    public JPanel panelLogoAndTitle(String text) {
        painelLogoAndTitle = buildMethod.createPanel(30, 0, new BorderLayout(), colorBackgroundWhite, 0,0,0,0);
        painelLogoAndTitle.setBackground(colorBlackBackground);
        
        labelNavBarTitle.setText(text);

        painelLogoAndTitle.add(icon, BorderLayout.WEST);
        painelLogoAndTitle.add(labelNavBarTitle, BorderLayout.CENTER);
        return painelLogoAndTitle;
    }
    
    public JPanel panelButtons(ImageIcon icon) {
        painelButtons = buildMethod.createPanel(20, 0, new FlowLayout(FlowLayout.RIGHT), colorBackgroundWhite, 0,0,0,0);
        painelButtons.setBackground(buildMethod.colorBackgroundBlack);
        
        buttonHistoric.setIcon(iconHistoric);
        buttonAccess.setIcon(icon);
        
        painelButtons.add(buttonHistoric);
        painelButtons.add(buttonAccess);
        painelButtons.add(buttonMinimize);
        painelButtons.add(buttonExit);
        return painelButtons;
    }
    
    public void replaceFunctionButton() throws SQLException {
        boolean visible = mPrincipal.getInstance().getVisibleFrame();
        if (visible) {
            mLogIn mLogIn = new mLogIn();
            mLogIn.setVisible(true);
        } else {
            mPrincipal mPrincipal = new mPrincipal();
            mPrincipal.setVisible(true);
            mPrincipal.setBounds(BuildMethods.bounds);
            
            mAdmin.getInstance().getFrame().dispose();
        }
    }
    
    public void initialize() {
        buttonMinimize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.ICONIFIED);
            }
        });
        
        buttonHistoric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mHistoricTableBuy mHistoricTableBuy = new mHistoricTableBuy();
				mHistoricTableBuy.setVisible(true);
			}
		});
    }
    
    public void addCloseButtonListener(ActionListener listener) {
        buttonExit.addActionListener(listener);
    }
    
    public void addAcessButtonListener(ActionListener listener) {
    	buttonAccess.addActionListener(listener);
    }
    
    public void getFrame(JFrame frame) {
        this.frame = frame;
    }
}
