package com.nightbeer.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.*;

import com.nightbeer.dao.dados;
import com.nightbeer.buildmethods.Build;

public class mLogIn extends JFrame implements ActionListener{

	private JPanel contentPane;
	private static mPrincipal mainFrame;
	Build buildMethod = new Build();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mLogIn frame = new mLogIn(mainFrame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public mLogIn(mPrincipal mainFrame) {
		this.mainFrame = mainFrame;
		
		//Create item's
		btnClose = buildMethod.createButton("X", 444, 0, 36, 30, 16);
			btnClose.setBackground(buildMethod.cBtnClose);
		labelUser = buildMethod.createLabel("User", 110, 60, 60, 30, 14);
		textFieldUser = buildMethod.createTextField("Usuario", 110, 90, 260, 30);
		
		labelPassword = buildMethod.createLabel("Password", 110, 130, 60, 30, 14);
		textFieldPassword = buildMethod.createTextField("Password", 110, 160, 260, 30);
		labelPasswordError = buildMethod.createLabel("  wrong password, try again", 110, 180, 200, 30, 12);
			labelPasswordError.setForeground(new Color (200, 0, 0));
			labelPasswordError.setVisible(false);
		
		btnSend = buildMethod.createButton("Send", 310, 205, 60, 30, 16);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 280);
		contentPane = new JPanel();
		contentPane.setBackground(buildMethod.cBackground);
		contentPane.setLayout(null);
		contentPane.setBorder(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		
		setTitle("LogIn");
		setContentPane(contentPane);
		
		
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        textFieldUser.requestFocus();
		    }
		});


		
		textFieldUser.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, buildMethod.cBtn));
		textFieldPassword.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, buildMethod.cBtn));
		
		
		//Adding item's in frame
		contentPane.add(btnClose);
		contentPane.add(labelUser);
		contentPane.add(textFieldUser);
		contentPane.add(labelPassword);
		contentPane.add(textFieldPassword);
		contentPane.add(labelPasswordError);
		contentPane.add(btnSend);		
		
		//adding function to item
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();	
			}
		});
		
        // adding function click enter to send form 
        textFieldPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSendActionPerformed(e);
            }
        });
		
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnSendActionPerformed(evt);
            }
        });
	    
		
        //
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Restaura o estado do frame para normal quando o foco é perdido
                setState(JFrame.NORMAL);
            }
        });
        
        setAlwaysOnTop(true);
        
        //
	}

	private void btnSendActionPerformed(ActionEvent evt) {                                         
	    dados msDados = new dados();
	    if (!msDados.validUser(textFieldUser.getText(),
	        new String(textFieldPassword.getText()))){
	    
	    textFieldUser.setText("");
	    textFieldPassword.setText("");
	    textFieldUser.requestFocusInWindow();
	    labelPasswordError.setVisible(true);
	    
	    return;
	    }
        mAdmin mAdmin = new mAdmin();
        this.setVisible(false);
        mAdmin.setVisible(true);
        mainFrame.dispose();
        
        
	    
	} 
	
	public void actionPerformed(ActionEvent e) {
			
	}

	private JLabel labelPasswordError;
	private JLabel labelUser;
	private JLabel labelPassword;
	
	private JButton btnClose;
	private JButton btnSend;
	
	private JTextField textFieldUser;
	private JTextField textFieldPassword;
}
