package com.nightbeer.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.nightbeer.build.BuildCreateTipoMarca;
import com.nightbeer.build.BuildMethods;
import com.nightbeer.build.BuildPreviusBuy;

public class mPreviusTableBuy extends JFrame {
	private JPanel contentPane;
	private BuildMethods buildMethods = new BuildMethods();
	private static mPreviusTableBuy instance;

	
	public mPreviusTableBuy() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Compra anterior");
        setResizable(false);
        setUndecorated(true);
        
        contentPane = new JPanel(new BorderLayout());
		contentPane.add(new BuildPreviusBuy().containerMain(this));
        
		setContentPane(contentPane);
		pack();
		setLocationRelativeTo(null);
	} 
	
	
	
	
	
	public static mPreviusTableBuy getInstance() {
	    return instance;
	}
	
	public JFrame getFrame() {
	    return this;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mPreviusTableBuy frame = new mPreviusTableBuy();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
