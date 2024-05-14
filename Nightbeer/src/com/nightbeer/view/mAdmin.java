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
	
	public int x;
	public int y;
	public int normalWidth;
	public int normalHeight;
	public boolean maximized = false;

	Build buildMethod = new Build();
	mPrincipal mPrincipal = new mPrincipal();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mAdmin frame = new mAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mAdmin() {
		//
		
		//Create and Modify items
		JButton btnExit = buildMethod.createButton("X", 0, 0, 18, 15, 14); 
			btnExit.setBackground(buildMethod.cBtnClose);	
		
		JButton btnMaximize = buildMethod.createButton("[ ]", 0, 0, 18, 15, 12);
			
		JButton btnMinimize = buildMethod.createButton("-", 0, 0, 18, 15, 12);
		
		JButton btnBackToMain = buildMethod.createButton("Y", 0, 0, 40, 40, 14);

		JLabel labelSystemTitle = buildMethod.createLabel("  Nightbeer Config", 600, 30, 22, 0, 14);
		labelSystemTitle.setHorizontalAlignment(SwingConstants.LEFT);
		labelSystemTitle.setBackground(buildMethod.cBackground);

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
		

		
		
		//Adding function to item
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();	
			}
		});
		
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
		
		btnMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		btnBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPrincipal.setVisible(true);
				setVisible(false);
				
				
				
			}
		});

		
		// Add item in JPanel button
		JPanel panelBtn1 = new JPanel();
		panelBtn1.setLayout(new FlowLayout()); 
		panelBtn1.setBackground(buildMethod.cBackground);
			panelBtn1.add(btnMinimize);
			panelBtn1.add(btnMaximize);
			panelBtn1.add(btnExit);
		
		JPanel panelBtn2 = new JPanel();
		panelBtn2.setLayout(new FlowLayout()); 
		panelBtn2.setBackground(buildMethod.cBackground);
			panelBtn2.add(btnBackToMain);
		
		JPanel panelBtnMain = new JPanel();
		panelBtnMain.setLayout(new GridLayout(1, 2)); 
		panelBtnMain.setBackground(buildMethod.cBackground);
			panelBtnMain.add(panelBtn2);
			panelBtnMain.add(panelBtn1);
		
		contentPane.add(panelBtnMain, BorderLayout.EAST);		
		JPanel panelNavbar = new JPanel();
		panelNavbar.setLayout(new BorderLayout()); // Use BorderLayout
		panelNavbar.setBackground(buildMethod.cBackground);
		panelNavbar.setPreferredSize(new Dimension(30, 50));

		panelNavbar.add(labelSystemTitle, BorderLayout.WEST);

		panelNavbar.add(panelBtnMain, BorderLayout.EAST);

		contentPane.add(panelNavbar, BorderLayout.PAGE_START);

		movTela();
		
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
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
	

}
