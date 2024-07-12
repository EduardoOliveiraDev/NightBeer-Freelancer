package com.nightbeer.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.nightbeer.build.BuildCreateTipoMarca;
import com.nightbeer.build.BuildMethods;

public class mCreateTipoMarca extends JFrame {
	private JPanel contentPane = new JPanel(new BorderLayout());

	public mCreateTipoMarca() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
		setTitle("Criar Tipo/Marca");
		
		BuildCreateTipoMarca bd = new BuildCreateTipoMarca();
		contentPane.add(bd.containerMain(this));
		
		setContentPane(contentPane);
		pack();
		setLocationRelativeTo(null);
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
