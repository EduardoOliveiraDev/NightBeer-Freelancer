package com.nightbeer.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.nightbeer.build.BuildCreateTipoMarca;
import com.nightbeer.build.BuildMethods;
import com.nightbeer.build.BuildHistoricBuy;

public class mHistoricTableBuy extends JFrame {
	private JPanel contentPane;
	private BuildHistoricBuy buildHistoricBuy = new BuildHistoricBuy();
	private static mHistoricTableBuy instance;

	
	public mHistoricTableBuy() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Compra anterior");
        setResizable(false);
        setUndecorated(true);
        
        contentPane = new JPanel(new BorderLayout());
		contentPane.add(new BuildHistoricBuy().containerMain(this));

		
		setContentPane(contentPane);
		pack();
		setLocationRelativeTo(null);
	} 
	
	
	
	
	
	public static mHistoricTableBuy getInstance() {
	    return instance;
	}
	
	public JFrame getFrame() {
	    return this;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mHistoricTableBuy frame = new mHistoricTableBuy();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
