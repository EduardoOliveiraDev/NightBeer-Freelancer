package com.nightbeer.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.nightbeer.buildmethods.Build;
import com.nightbeer.dao.dados;

public class mPrincipal extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	
	private JButton btnExit;
	private JButton btnMaximize;
	private JButton btnMinimize;
	private JButton btnLogin;
	
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
	
	// User
	private JLabel iconUserField;
	private ImageIcon iconUserImage;

		
	
	Build buildMethod = new Build();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mPrincipal frame = new mPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Main Menu
	public mPrincipal() {
		//
		// JPanel Main
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 960, 560);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setTitle("NightBeer System");
        
		setContentPane(contentPane);
		
		movTela();
		
		
		// NavBar Panel
		mPrincipalContainerNavBar();
		
		// blocking menu 
		mPrincipalBlocking();
		
		// Search bar Panel
		mPrincipalSearchBar();

		
	}
	
	// Create icon for label's and button's
	public void createIcon() {
		iconLogoImage = buildMethod.createImage("../images/nightbeerIcon.jpg", 60, 60);
		iconLogoField = new JLabel(iconLogoImage);
		
		iconUserImage = buildMethod.createImage("../images/iconUserLogin.png", 30, 30);
		iconUserField = new JLabel(iconUserImage);
	}
	
	//
	// Element complete of NavBar
	public void mPrincipalContainerNavBar() {
		createIcon();
		
		
	//
	//Create and Modify items
		btnExit = buildMethod.createButton("X", 0, 0, 18, 15, 14); 
			btnExit.setBackground(buildMethod.cBtnClose);		
		btnMaximize = buildMethod.createButton("[ ]", 0, 0, 18, 15, 12);
		btnMinimize = buildMethod.createButton("-", 0, 0, 18, 15, 12);
		btnLogin = buildMethod.createButton("", 0, 0, 40, 40, 14);
			btnLogin.setBackground(buildMethod.cBackground);
			btnLogin.setIcon(iconUserImage);

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
		
		// Function to the maximize the window
		btnMaximize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(buildMethod.maximized) {
					setExtendedState(JFrame.NORMAL);
	                setSize(buildMethod.normalWidth, buildMethod.normalHeight);
	                buildMethod.maximized = false;
				}else {
					buildMethod.normalWidth = getWidth();
					buildMethod.normalHeight = getHeight();
	                    
	                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	                Rectangle bounds = env.getMaximumWindowBounds();
	                setBounds(bounds);
	                    
	                buildMethod.maximized = true;
				}
			}
		});
		
		// Function to the minimize the window
		btnMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		// Function to the login in configuration menu
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        mLogIn mLogin = new mLogIn(mPrincipal.this);
			    mLogin.setVisible(true);
			     
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
		panelBtn1.add(btnMaximize);
		panelBtn1.add(btnExit);
		panelBtn2.add(btnLogin);
		
			
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
	
	//
	// Main organization layout
	public void mPrincipalBlocking() {
		JPanel panelContainerPrimary = new JPanel();
		panelContainerPrimary.setLayout(new GridLayout(1, 2));
		panelContainerPrimary.setBackground(buildMethod.cBtnClose);
		
		contentPane.add(panelContainerPrimary);
	}
	
	
	//
	// Element incomplete of SearchBar
	public void mPrincipalSearchBar() {
		JPanel panelMainSearchBar = new JPanel();
		panelMainSearchBar.setLayout(new GridLayout(1 , 3));
		panelMainSearchBar.setBackground(buildMethod.cBtnClose);
		
//		contentPane.add(panelMainSearchBar);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	// method to move screen
    public void movTela() {
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(buildMethod.maximized) {
                    setExtendedState(JFrame.NORMAL);
                    setSize(buildMethod.normalWidth, buildMethod.normalHeight);
                    buildMethod.maximized = false;
					setLocation(e.getXOnScreen() -buildMethod.x, e.getYOnScreen() -buildMethod.y);
				} else {
					setLocation(e.getXOnScreen() -buildMethod.x, e.getYOnScreen() -buildMethod.y);
				}
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				buildMethod.x = e.getX();
				buildMethod.y = e.getY();
				
;				}
		});
		
	}
 
    // method to close Main menu
    public void closeMPrincipal() {
        dispose();
    }
    
}
