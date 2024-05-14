package com.nightbeer.buildmethods;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Build extends JFrame implements ActionListener{

	public Color cBtnClose = new Color(232, 147, 244);
	public Color cBtn = new Color(50, 50, 50);
	public Color cBackground = new Color(32, 32, 32);
	public Color cText = new Color(255, 255, 255);
	public Color cLine = new Color(0, 0, 0);
	
	public int x;
	public int y;
	public int normalWidth;
	public int normalHeight;
	public boolean maximized = false;
    
    //
    // create build's method's
    //
	
	//
	// Button
	public JButton createButton(String textButton, int x, int y, int width, int height, int fontSize) {
		JButton jButton = new JButton(textButton);
		jButton.setHorizontalAlignment(SwingConstants.CENTER);
		jButton.setFont(new Font("Tahoma", Font.PLAIN, textButton.equalsIgnoreCase("x") ? 20 : fontSize));
		jButton.setForeground(cText);
		jButton.setFocusPainted(false);
		jButton.setBackground(cBtn);
		jButton.setBorder(null);
		jButton.setBounds(x, y, width, height);
		jButton.setPreferredSize(new Dimension(width, height));
		
		return jButton;
	}

	//
	// TextField
    public JTextField createTextField(String textField, int x, int y, int width, int height) {
        JTextField JTextField = new JTextField();
        JTextField.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        JTextField.setForeground(cText);
        JTextField.setBackground(cBtn);
        JTextField.setOpaque(true);
        JTextField.setBorder(null);
        JTextField.setBounds(x, y, width, height);

        return JTextField;
    }
    
    //
    // Label
	public JLabel createLabel(String textLabel, int x, int y, int width, int height, int fontSize) {
		JLabel JLabel = new JLabel(textLabel);
		JLabel.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
		JLabel.setForeground(cText);
		JLabel.setBackground(cBackground);
		JLabel.setOpaque(true);
		JLabel.setBorder(null);
		JLabel.setBounds(x, y, width, height);
		
		return JLabel;
	}

    //
    //


	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
