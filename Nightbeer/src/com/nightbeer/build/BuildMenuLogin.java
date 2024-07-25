package com.nightbeer.build;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import com.nightbeer.dao.dados;
import com.nightbeer.view.mAdmin;
import com.nightbeer.view.mPrincipal;

public class BuildMenuLogin {
    private BuildMethods buildMethod = new BuildMethods();
    
    private Color colorButton = buildMethod.colorButton;
    private Color colorButtonGrey = buildMethod.colorButtonGrey;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorTextBlack = buildMethod.colorTextBlack;
    private Color colorWhiteClear = buildMethod.colorWhiteClear;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;
    private Color colorRed = buildMethod.colorButtonRed;
    
    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
    
    private JPanel contentPane;
    private JPanel containerNavBar;
    private JButton buttonClose;
    private JLabel labelTitle;

    private JPanel containerComponentes;
    private JPanel containerBox1; //
    private JPanel containerBox2; //
    private JTextField textFieldUser;
    private JTextField textFieldPasswordShow;
    private JPasswordField textFieldPassword;
    private JLabel labelPasswordError;
    
    private JPanel panelMain; 
    private JButton buttonSend;
    
    public JPanel buildLoginPanel(JFrame frame) {
        blockingPanel(frame);
        initialize(frame);
        
        contentPane = buildMethod.createPanel(26, 26, new BorderLayout(), colorBlackBackground, 0, 0, 0, 0);
        contentPane.add(panelMain);
        return contentPane;
    }
    
    public JPanel blockingPanel(JFrame frame) {
        panelMain = new JPanel(new BorderLayout());
        panelMain.add(navBarContainer(), BorderLayout.NORTH);
        panelMain.add(loginComponents(), BorderLayout.CENTER);
        return panelMain;
    }
    
    public JPanel navBarContainer() {
        containerNavBar = buildMethod.createPanel(26, 3, new BorderLayout(), colorBlackBackground, 0, 0, 0, 10);
        labelTitle = buildMethod.createLabel("Tela de login", 6, 2.6, SwingConstants.LEFT, colorWhiteClear, colorBlackBackground, FontRobotoPlainSmall, 0, 0, 0, 0);
        
        buttonClose = buildMethod.createButton("X", 2.4, 2.6, SwingConstants.CENTER, colorWhiteClear, colorBlackBackground);
        buttonClose.setFont(FontRobotoPlainSmall);
        
        containerNavBar.add(labelTitle, BorderLayout.WEST);
        containerNavBar.add(buttonClose, BorderLayout.EAST);
        return containerNavBar;
    }
    
    public JPanel loginComponents() {
        containerComponentes = buildMethod.createPanel(30, 27, new FlowLayout(), colorBackgroundWhite, 0, 0, 0, 0);

        textFieldUser = buildMethod.createTextField("Usuario", 20, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
        textFieldPasswordShow = buildMethod.createTextField("", 20, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
        textFieldPassword = buildMethod.createPasswordField("", 20, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
        labelPasswordError = buildMethod.createLabel("Senha errada, tente novamente.", 13, 3, SwingConstants.LEFT, colorBackgroundWhite, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
        buttonSend = buildMethod.createButton("Enviar", 5, 4, SwingConstants.CENTER, colorWhiteClear, colorButton);
        
        // Placeholder para textFieldUser
        addPlaceholder(textFieldUser, "Usuario");
        addPlaceholder(textFieldPasswordShow, "Senha");

        // Configuração inicial do textFieldPasswordShow
        configurePasswordShow();

        containerBox1 = buildMethod.createPanel(20, 12, new FlowLayout(FlowLayout.RIGHT), colorBackgroundWhite, 30, 0, 0, 0);
        containerBox1.add(textFieldUser);
        containerBox1.add(textFieldPasswordShow);
        
        containerBox2 = buildMethod.createPanel(20, 3.5, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        containerBox2.add(labelPasswordError, BorderLayout.WEST);
        containerBox2.add(buttonSend, BorderLayout.EAST);
        
        containerComponentes.add(containerBox1);
        containerComponentes.add(containerBox2);


        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passwordString = new String(textFieldPassword.getPassword());
                labelPasswordError.setVisible(passwordString.isEmpty()); // Exibe erro se a senha estiver vazia
            }
        });

        return containerComponentes;
    }

    private void configurePasswordShow() {
        textFieldPasswordShow.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	transformTextFieldToPasswordField();
            }
        });

        textFieldPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
            	if (new String(textFieldPassword.getPassword()).isEmpty()) {
            		transformPasswordFieldToTextField();
				}
            }
        });
    }
    
    public void transformTextFieldToPasswordField() {
    	containerBox1.remove(textFieldPasswordShow);
    	containerBox1.add(textFieldPassword);
    	
    	containerBox1.revalidate(); // Recalcula o layout
    	containerBox1.repaint(); // Redesenha o painel
    	textFieldPassword.requestFocus();
    }
    
    public void transformPasswordFieldToTextField() {
    	containerBox1.add(textFieldPasswordShow);
    	containerBox1.remove(textFieldPassword);
    	
    	containerBox1.revalidate(); // Recalcula o layout
    	containerBox1.repaint(); // Redesenha o painel
    }

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
     
    @SuppressWarnings("deprecation")
    public void initialize(JFrame frame) {
        buttonClose.addActionListener(e -> frame.dispose());
        
        buttonClose.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonClose.setBackground(colorRed);
            }

            public void mouseExited(MouseEvent e) {
                buttonClose.setBackground(colorBlackBackground);
            }
        });
        
        buttonSend.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	buttonSend.setBackground(colorButtonGrey);
            }

            public void mouseExited(MouseEvent e) {
            	buttonSend.setBackground(colorButton);
            }
        });
        
        if (buttonSend != null) {
            buttonSend.addActionListener(e -> {
                dados msDados = new dados();
                String user = textFieldUser.getText();
                String password = new String(textFieldPassword.getPassword());
                if (!msDados.validUser(user, password)) {
                	textFieldPassword.setText("");
                	textFieldUser.setText("Usuario");
                	textFieldUser.setForeground(Color.GRAY);
                    textFieldPasswordShow.setText("Senha");
                    textFieldPasswordShow.setForeground(Color.GRAY);
                    transformTextFieldToPasswordField();
                    labelTitle.requestFocus();
                    
                    labelPasswordError.setForeground(new Color(200, 0, 0));
                    labelPasswordError.setVisible(true); // Exibe mensagem de erro
                } else {
                    mAdmin mAdmin = null;
                    try {
                        mAdmin = new mAdmin();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    if (mAdmin != null) {
                        mAdmin.setVisible(true);
                        mAdmin.setBounds(BuildMethods.bounds);
                    }

                    // Fecha o menu de login
                    frame.dispose();
                    
                    // Fecha o menu principal
                    mPrincipal.getInstance().getFrame().dispose();
                }
            });
        } else {
            System.err.println("Botão buttonSend não foi inicializado corretamente.");
        }
        
        frame.getRootPane().setDefaultButton(buttonSend);
    }
}
