package com.nightbeer.build;

import java.awt.*;

import javax.swing.*;

public class BuildMethods{
	
	public Color colorButtonClose = createColor(232, 147, 244);
	public Color colorButton = createColor(50, 50, 50);
	public Color colorBackground = createColor(32, 32, 32);
	public Color colorText = createColor(255, 255, 255);
	public Color colorLine = createColor(0, 0, 0);
	
	public Font FontRobotoPlainSmall = createFont("Roboto", Font.BOLD, 16);
	public Font FontRobotoPlainMedium = createFont("Roboto", Font.BOLD, 18);
	public Font FontRobotoPlainLarge = createFont("Roboto", Font.BOLD, 22);
	
    public static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static Rectangle bounds = env.getMaximumWindowBounds();
    
    public Color createColor(int red, int green, int blue) {
    	return new Color(red, green, blue);
    }
    
    public Font createFont(String FontName, int FontStyle, int FontSize) {
    	return new Font(FontName, FontStyle, FontSize);
    }
    
    public JPanel createPanel(double width, double height) {
    	JPanel JPanel = new JPanel();
    	JPanel.setBorder(null);
    	JPanel.setPreferredSize(createResponsive(width, height));
    	
    	return JPanel;
    }
    
	public JButton createButton(String textButton, double width, double height) {
		JButton jButton = new JButton(textButton);
		jButton.setHorizontalAlignment(SwingConstants.CENTER);
		jButton.setForeground(colorText);
		jButton.setFocusPainted(false);
		jButton.setBackground(colorButton);
		jButton.setBorder(null);
		jButton.setPreferredSize(createResponsive(width, height));
		
		return jButton;
	}

    public JTextField createTextField(String textField, double width, double height) {
        JTextField JTextField = new JTextField();
        JTextField.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField.setForeground(colorText);
        JTextField.setBackground(colorButton);
        JTextField.setOpaque(true);
        JTextField.setBorder(null);
        JTextField.setPreferredSize(createResponsive(width, height));

        return JTextField;
    }
    
    public JPasswordField createPasswordField(String textField, double width, double height) {
    	JPasswordField JPasswordField = new JPasswordField();
    	JPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
    	JPasswordField.setForeground(colorText);
    	JPasswordField.setBackground(colorButton);
    	JPasswordField.setOpaque(true);
    	JPasswordField.setBorder(null);
    	JPasswordField.setPreferredSize(createResponsive(width, height));
    	return JPasswordField;
    }
    
	public JLabel createLabel(String textLabel, double width, double height) {
		JLabel JLabel = new JLabel(textLabel);
		JLabel.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel.setForeground(colorText);
		JLabel.setBackground(colorBackground);
		JLabel.setOpaque(true);
		JLabel.setPreferredSize(createResponsive(width, height));
		
		return JLabel;
	}
	
    public JComboBox<?> createComboBox(String textField, double width, double height) {
        JComboBox<?> jComboBox = new JComboBox<>();
        jComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jComboBox.setForeground(colorText);
        jComboBox.setBackground(colorButton);
        jComboBox.setOpaque(true);
        jComboBox.setBorder(null);
        jComboBox.setPreferredSize(createResponsive(width, height));

        return jComboBox;
    }

	public ImageIcon createImage(String locate, int width, int height) {
		ImageIcon icon = new ImageIcon(getClass().getResource(locate));
		
		Image image = icon.getImage();
		
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage);
		
		return newIcon;
	}
	
	public JLabel createIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(getClass().getResource(path));
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
		return label;
	}
	
	
    public Dimension createResponsive(double widthScreenPercentual, double heightScreenPercentual) {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int widthScreen = (int) (screenSize.width * (widthScreenPercentual/100));
        int heightScreen = (int) (screenSize.height * (heightScreenPercentual/100));
        return new Dimension(widthScreen, heightScreen);
    }
    
	
}
