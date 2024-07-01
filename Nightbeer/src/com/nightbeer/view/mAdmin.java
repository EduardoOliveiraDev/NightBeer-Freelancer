package com.nightbeer.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.nightbeer.build.BuildMAdmin;
import com.nightbeer.build.BuildMethods;
import com.nightbeer.build.BuildNavBar;

@SuppressWarnings("serial")
public class mAdmin extends JFrame{

	private JPanel contentPane;
	private BuildMAdmin buildMAdmin = new BuildMAdmin(); 
	private static mAdmin instance;
   
	public mAdmin() {
		
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
                buildMAdmin.listar();
            }
        });
	}
    
	public void BuildingViewMadmin() {
		BuildNavBar navBar = new BuildNavBar();
		navBar.panelWest("  NightBeer Admin");
		navBar.panelEast(navBar.iconBack);
		navBar.getFrame(this);
	    contentPane.add(navBar.containerNavBar(), BorderLayout.NORTH);
	    
	    // Adding container center in mAdmin
	    contentPane.add(buildMAdmin.containerCenter(), BorderLayout.CENTER);
	    
	   // Adding container east in mAdmin
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
