package com.nightbeer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.nightbeer.buildmethods.Build;
import com.nightbeer.dao.dados;

public class mAdmin extends JFrame implements ActionListener{

	private JPanel contentPane;

	private JButton btnExit;
	private JButton btnMinimize;
	private JButton btnBackToMain;
	
	private JLabel labelSystemTitle;
	
	private JPanel panelBtn1;
	private JPanel panelBtn2;
	private JPanel panelNavBarContainerPrimary;
	private JPanel panelNavBarContainerSecond;
	private JPanel panelNavbar;
	
	//
	// Images
	
	// Logo
	private JLabel iconLogoField;
	private ImageIcon iconLogoImage;
	
	// go back
	private JLabel iconBackField;
	private ImageIcon iconBackImage;
	
	
	// Building classes
	Build buildMethod = new Build();
	mPrincipal mPrincipal = new mPrincipal();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mAdmin frame = new mAdmin();
					frame.setBounds(Build.bounds);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Main Menu
	public mAdmin() {
		//
		//JPanel Main
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 960, 560);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setTitle("Admin");
        
		
		
		setContentPane(contentPane);

		//NarBar
		mAdminContainerNavBar();
		
		
	}
	
	// Create icon for label's and button's
	public void createIcon() {
		iconLogoImage = buildMethod.createImage("../images/nightbeerIcon.jpg", 60, 60);
		iconLogoField = new JLabel(iconLogoImage);
		
		iconBackImage = buildMethod.createImage("../images/iconGoBack.png", 30, 30);
		iconBackField = new JLabel(iconBackImage);
	}
	
	// element complete of NavBar
	public void mAdminContainerNavBar() {
		createIcon();
		
		
	//
	//Create and Modify items
		btnExit = buildMethod.createButton("X", 0, 0, 18, 15, 14); 
			btnExit.setBackground(buildMethod.cBtnClose);		
		btnMinimize = buildMethod.createButton("-", 0, 0, 18, 15, 12);
		btnBackToMain = buildMethod.createButton("", 0, 0, 40, 40, 14);
			btnBackToMain.setBackground(buildMethod.cBackground);
			btnBackToMain.setIcon(iconBackImage);

	//
	// Creating title to navBar
		labelSystemTitle = buildMethod.createLabel("  Nightbeer", 600, 30, 22, 0, 14);
			labelSystemTitle.setHorizontalAlignment(SwingConstants.LEFT);
			labelSystemTitle.setBackground(buildMethod.cBackground);
			
	//
	// Adding function to buttons	
			
		// Function to the close the window
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();	
				}
			});
								
		// Function to the minimize the window
			btnMinimize.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setExtendedState(JFrame.ICONIFIED);
				}
			});
		
		// Function to back the principal menu	
			btnBackToMain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mPrincipal.setVisible(true);
					mPrincipal.setBounds(buildMethod.bounds);
					setVisible(false);
				}
			});				
	
	//
	// Creating and configuration panel for the items
				
		// Simple containers
			panelBtn1 = new JPanel();
				panelBtn1.setLayout(new FlowLayout()); 
				panelBtn1.setBackground(buildMethod.cBackground);
					
		// Simple containers	
			panelBtn2 = new JPanel();
				panelBtn2.setLayout(new FlowLayout()); 
				panelBtn2.setBackground(buildMethod.cBackground);
				
		// Primary containers	
			panelNavBarContainerPrimary = new JPanel();
				panelNavBarContainerPrimary.setLayout(new GridLayout(1, 2));
				panelNavBarContainerPrimary.setBackground(buildMethod.cBackground);			
							
		// Second containers	
			panelNavBarContainerSecond = new JPanel();
				panelNavBarContainerSecond.setLayout(new GridLayout(1, 2)); 
				panelNavBarContainerSecond.setBackground(buildMethod.cBackground);
				
		// Container that goes to the main Panel
			panelNavbar = new JPanel();
				panelNavbar.setLayout(new BorderLayout()); // Use BorderLayout
				panelNavbar.setBackground(buildMethod.cBackground);
				panelNavbar.setPreferredSize(new Dimension(30, 50));

	//
	// Adding item's to new painel's
					
		// simple items in containers
			panelBtn1.add(btnMinimize);
			panelBtn1.add(btnExit);
			panelBtn2.add(btnBackToMain);
				
					
		// Primary container in label to the left
			panelNavBarContainerPrimary.add(iconLogoField, BorderLayout.WEST);
			panelNavBarContainerPrimary.add(labelSystemTitle, BorderLayout.EAST);
				
		// containers in Second container
			panelNavBarContainerSecond.add(panelBtn2);
			panelNavBarContainerSecond.add(panelBtn1);
					
		// Second container to the right
			panelNavbar.add(panelNavBarContainerPrimary, BorderLayout.WEST);
			panelNavbar.add(panelNavBarContainerSecond, BorderLayout.EAST);
					
		// Adding all containers in main Panel
			contentPane.add(panelNavbar, BorderLayout.PAGE_START);
					

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
			
	}

}
