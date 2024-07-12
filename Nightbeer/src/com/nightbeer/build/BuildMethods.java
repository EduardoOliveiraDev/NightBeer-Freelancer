package com.nightbeer.build;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BuildMethods{
	
	public Color colorButtonGreen = createColor(9, 140, 61);
	public Color colorButtonRed = createColor(208, 48, 48);
	public Color colorButtonLightGrey = createColor(160, 160, 160);
	public Color colorButtonGrey = createColor(100, 100, 100);
	
	// Color's
	public Color colorButtonClose = createColor(232, 147, 244);
	public Color colorButton = createColor(50, 50, 50);
	public Color colorTextWhite = createColor(255, 255, 255);
	public Color colorTextBlack = createColor(0, 0, 0);
	public Color colorBackgroundBlack = createColor(32, 32, 32);
	public Color colorBackgroundWhite = createColor(203, 203, 203); //203
	
	public Color colorWhiteClear = createColor(255, 255, 255);

	
	public Font FontRobotoPlain16 = createFont("Roboto", Font.BOLD, 16);
	public Font FontRobotoPlain18 = createFont("Roboto", Font.BOLD, 18); 
	public Font FontRobotoPlain22 = createFont("Roboto", Font.BOLD, 22);
	public Font FontRobotoPlain28 = createFont("Roboto", Font.BOLD, 28);

	
    public static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static Rectangle bounds = env.getMaximumWindowBounds();
    
    public Color createColor(int red, int green, int blue) {
    	return new Color(red, green, blue);
    }
    
    public Font createFont(String FontName, int FontStyle, int FontSize) {
    	return new Font(FontName, FontStyle, FontSize);
    }
    
    public JPanel createPanel(double width, double height, LayoutManager layout, Color BackgroundColor, int top, int right, int bottom, int left) {
    	JPanel JPanel = new JPanel();
    	JPanel.setLayout(layout);
    	JPanel.setBackground(BackgroundColor);
    	JPanel.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, BackgroundColor));
    	JPanel.setPreferredSize(createResponsive(width, height));
    	
    	return JPanel;
    }
    
	public JButton createButton(String textButton, double width, double height, int textPosition, Color FontColor, Color BackgroundColor) {
		JButton jButton = new JButton(textButton);
		jButton.setHorizontalAlignment(textPosition);
		jButton.setForeground(FontColor);
		jButton.setFocusPainted(false);
		jButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		jButton.setBackground(BackgroundColor);
		jButton.setPreferredSize(createResponsive(width, height));
		
		return jButton;
	}

    public JTextField createTextField(String textField, double width, double height, int textPosition, Color FontColor, Color BackgroundColor, Font FontFamiliy, int top, int right, int bottom, int left) {
        JTextField JTextField = new JTextField();
        JTextField.setHorizontalAlignment(textPosition);
        JTextField.setForeground(FontColor);
        JTextField.setBackground(BackgroundColor);
        JTextField.setOpaque(true);
        JTextField.setFont(FontFamiliy);
        JTextField.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, BackgroundColor));
        JTextField.setPreferredSize(createResponsive(width, height));

        return JTextField;
    }
    
    public JPasswordField createPasswordField(String textField, double width, double height, int textPosition, Color FontColor, Color BackgroundColor) {
    	JPasswordField JPasswordField = new JPasswordField();
    	JPasswordField.setHorizontalAlignment(textPosition);
    	JPasswordField.setForeground(FontColor);
    	JPasswordField.setBackground(BackgroundColor);
    	JPasswordField.setOpaque(true);
    	JPasswordField.setBorder(new EmptyBorder(0, 0, 0, 0));
    	JPasswordField.setPreferredSize(createResponsive(width, height));
    	return JPasswordField;
    }
    
	public JLabel createLabel(String textLabel, double width, double height, int textPosition, Color FontColor, Color BackgroundColor, Font FontFamiliy, int top, int right, int bottom, int left) {
		JLabel JLabel = new JLabel(textLabel);
		JLabel.setHorizontalAlignment(textPosition);
		JLabel.setForeground(FontColor);
		JLabel.setBackground(BackgroundColor);
		JLabel.setOpaque(true);
		JLabel.setFont(FontFamiliy);
		JLabel.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, BackgroundColor));
		JLabel.setPreferredSize(createResponsive(width, height));
		
		return JLabel;
	}
	
    public JSpinner createSpinner(double width, double height, Color fontColor, Color backgroundColor, Font fontFamily) {
        JSpinner jSpinner = new JSpinner();
        jSpinner.setOpaque(true);
        jSpinner.setBorder(new EmptyBorder(0, 0, 0, 0));
        jSpinner.setPreferredSize(createResponsive(width, height));
        
        JComponent editor = jSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBackground(backgroundColor);
            textField.setForeground(fontColor);
            textField.setFont(fontFamily);
        }
        
        return jSpinner;
    }
	
    public JComboBox<?> createComboBox(String textField, double width, double height, Color FontColor, Color BackgroundColor, Font FontFamiliy, int top, int right, int bottom, int left) {
        JComboBox<?> jComboBox = new JComboBox<>();
        jComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jComboBox.setForeground(FontColor);
        jComboBox.setBackground(BackgroundColor);
        jComboBox.setOpaque(true);
        jComboBox.setFont(FontFamiliy);
        jComboBox.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, BackgroundColor));
        jComboBox.setPreferredSize(createResponsive(width, height));
        jComboBox.setMaximumRowCount(4);

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
