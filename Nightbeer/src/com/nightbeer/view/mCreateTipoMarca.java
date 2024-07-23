package com.nightbeer.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.nightbeer.build.BuildCreateTipoMarca;

@SuppressWarnings("serial")
public class mCreateTipoMarca extends JFrame {
	private JPanel contentPane = new JPanel(new BorderLayout());
	private static mCreateTipoMarca instance;
	
	public mCreateTipoMarca() throws SQLException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
		setTitle("Criar Tipo/Marca");
		
		BuildCreateTipoMarca bd = new BuildCreateTipoMarca();
		contentPane.add(bd.contentPaneCreateTypesAndBrands(this));
		
		setContentPane(contentPane);
		pack();
		setLocationRelativeTo(null);
	}
 
	public static mCreateTipoMarca getInstance() {
	    return instance;
	}
	
	public JFrame getFrame() {
	    return this;
	}
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mCreateTipoMarca frame = new mCreateTipoMarca();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
