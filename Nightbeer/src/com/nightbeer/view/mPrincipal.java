package com.nightbeer.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.*;

import com.nightbeer.build.BuildMPrincipal;
import com.nightbeer.build.BuildMethods;
import com.nightbeer.build.BuildNavBar;

@SuppressWarnings("serial")
public class mPrincipal extends JFrame{
	private BuildMPrincipal buildMPrincipal = new BuildMPrincipal();
	private BuildNavBar navBar = new BuildNavBar();
	
	private JPanel contentPane;
	private static mPrincipal instance;
	
	public mPrincipal() throws SQLException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("NightBeer System");
        setResizable(false);
        setUndecorated(true);
		
		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);
        BuildingViewMPrincipal();
        
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                try {
					buildMPrincipal.listItems();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                try {
					buildMPrincipal.listItems();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            public void windowClosing(WindowEvent e) {
                try {
					buildMPrincipal.returnItemsToStock();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });

        navBar.addCloseButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					buildMPrincipal.returnItemsToStock();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                System.exit(0);
            }
        });
        
        navBar.addAcessButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buildMPrincipal.returnItemsToStock();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					navBar.replaceFunctionButton();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
        
		instance = this;
	}
	
	public void BuildingViewMPrincipal() throws SQLException {    
		navBar.panelLogoAndTitle("  NightBeer Lounge");
		navBar.panelButtons(navBar.iconUser);
		navBar.getFrame(this);
	    contentPane.add(navBar.containerNavBar(), BorderLayout.NORTH);
	    contentPane.add(buildMPrincipal.containerCenter(), BorderLayout.CENTER);
	    contentPane.add(buildMPrincipal.containerTableBuyAndRequest(), BorderLayout.EAST);
	}
	
	public static mPrincipal getInstance() {
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
					mPrincipal frame = new mPrincipal();
					frame.setBounds(BuildMethods.bounds);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
