package com.nightbeer.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import com.nightbeer.build.BuildMAdmin;
import com.nightbeer.build.BuildMethods;
import com.nightbeer.build.BuildNavBar;

@SuppressWarnings("serial")
public class mAdmin extends JFrame{

	private JPanel contentPane;
	private BuildMAdmin buildMAdmin = new BuildMAdmin(); 
	private BuildNavBar navBar = new BuildNavBar();
	private static mAdmin instance;
   
	public mAdmin() throws SQLException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("NightBeer Config");
        setResizable(false);
        setUndecorated(true);
        
        contentPane = new JPanel(new BorderLayout());
        BuildingViewMadmin();
         
		setContentPane(contentPane);
		instance = this;
          
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                try {
					buildMAdmin.listItems();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
            
        });
        
        navBar.addCloseButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);;
            }
        });
        
        navBar.addAcessButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					navBar.replaceFunctionButton();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
    
	public void BuildingViewMadmin() throws SQLException {
		navBar.panelLogoAndTitle("  NightBeer Admin");
		navBar.panelButtons(navBar.iconBack);
		navBar.getFrame(this);
	    contentPane.add(navBar.containerNavBar(), BorderLayout.NORTH);
	    contentPane.add(buildMAdmin.containerCenter(), BorderLayout.CENTER);
	    contentPane.add(buildMAdmin.containerEast(), BorderLayout.EAST);
	}
	
	public static mAdmin getInstance() {
	    return instance;
	}
	
	public JFrame getFrame() {
	    return this;
	}
	
	public boolean getVisibleFrame() {
		return isVisible();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mAdmin frame = new mAdmin();
					frame.setBounds(BuildMethods.bounds);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
