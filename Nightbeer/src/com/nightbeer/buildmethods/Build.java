package com.nightbeer.buildmethods;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Build extends JFrame implements ActionListener{

	public Color cBtnClose = new Color(232, 147, 244);
	public Color cBtn = new Color(50, 50, 50);
	public Color cBackground = new Color(32, 32, 32);
	public Color cText = new Color(255, 255, 255);
	public Color cLine = new Color(0, 0, 0);
	
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int widthScreen = (int) screenSize.getWidth();
    public int heightScreen = (int) screenSize.getHeight();
	
    public static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static Rectangle bounds = env.getMaximumWindowBounds();
    
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
		jButton.setPreferredSize(createResponsive(width, height));
		
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
        JTextField.setPreferredSize(createResponsive(width, height));

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
		JLabel.setPreferredSize(createResponsive(width, height));
		
		return JLabel;
	}
	
	//
	// ComboBox
    public JComboBox<?> createComboBox(String textField, int x, int y, int width, int height) {
        JComboBox<?> jComboBox = new JComboBox<>();
        jComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jComboBox.setForeground(cText);
        jComboBox.setBackground(cBtn);
        jComboBox.setOpaque(true);
        jComboBox.setBorder(null);
        jComboBox.setBounds(x, y, width, height);
        jComboBox.setPreferredSize(createResponsive(width, height));

        return jComboBox;
    }

    //
    // Image
	public ImageIcon createImage(String locate, int width, int height) {
		ImageIcon icon = new ImageIcon(getClass().getResource(locate));
		
		Image image = icon.getImage();
		
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage);
		
		return newIcon;
	}
	
	//
	// Responsive
    public Dimension createResponsive(double widthScreenPercentual, double heightScreenPercentual) {
        int widthScreen = (int) (screenSize.width * (widthScreenPercentual/100));
        int heightScreen = (int) (screenSize.height * (heightScreenPercentual/100));
        return new Dimension(widthScreen, heightScreen);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
