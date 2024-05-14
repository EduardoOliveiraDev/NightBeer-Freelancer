package com.nightbeer.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.nightbeer.buildmethods.Build;
import com.nightbeer.dao.dados;

public class mPrincipal extends JFrame implements ActionListener{
	
	private int x;
	private int y;
	private int normalWidth;
	private int normalHeight;
	private boolean maximized = false;
	
	private JPanel contentPane;
	
	private JButton btnExit;
	private JButton btnMaximize;
	private JButton btnMinimize;
	private JButton btnLogin;
	
	private JLabel labelSystemTitle;
	
	private JPanel panelBtn1;
	private JPanel panelBtn2;
	private JPanel panelBtnMain;
	private JPanel panelNavbar;
	
	
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
		
		// NavBar Panel
		containerNavBar();
		

		movTela();
	}

	//
	// NavBar
	public void containerNavBar() {
		
	//
	//Create and Modify items
		btnExit = buildMethod.createButton("X", 0, 0, 18, 15, 14); 
			btnExit.setBackground(buildMethod.cBtnClose);		
		btnMaximize = buildMethod.createButton("[ ]", 0, 0, 18, 15, 12);
		btnMinimize = buildMethod.createButton("-", 0, 0, 18, 15, 12);
		btnLogin = buildMethod.createButton("Y", 0, 0, 40, 40, 14);

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
				if(maximized) {
					setExtendedState(JFrame.NORMAL);
	                setSize(normalWidth, normalHeight);
					maximized = false;
				}else {
					normalWidth = getWidth();
	                normalHeight = getHeight();
	                    
	                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	                Rectangle bounds = env.getMaximumWindowBounds();
	                setBounds(bounds);
	                    
	                maximized = true;
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
		
		// Main containers	
		panelBtnMain = new JPanel();
			panelBtnMain.setLayout(new GridLayout(1, 2)); 
			panelBtnMain.setBackground(buildMethod.cBackground);
		
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
		
		// containers in main container
		panelBtnMain.add(panelBtn2);
		panelBtnMain.add(panelBtn1);
			
		// Title label to the left
		panelNavbar.add(labelSystemTitle, BorderLayout.WEST);
			
		// Main container to the right
		panelNavbar.add(panelBtnMain, BorderLayout.EAST);
			
		// Adding all containers in main Panel
		contentPane.add(panelNavbar, BorderLayout.PAGE_START);
	
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	// method to moving the screen
    public void movTela( ) {
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(maximized) {
                    setExtendedState(JFrame.NORMAL);
                    setSize(normalWidth, normalHeight);
					maximized = false;
					setLocation(e.getXOnScreen() -x, e.getYOnScreen() -y);
				} else {
					setLocation(e.getXOnScreen() -x, e.getYOnScreen() -y);
				}
			}
		});
		contentPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				
;				}
		});
		
	}
	
    
    // method to close Main menu
    public void closeMPrincipal() {
        dispose();
    }
    
}
