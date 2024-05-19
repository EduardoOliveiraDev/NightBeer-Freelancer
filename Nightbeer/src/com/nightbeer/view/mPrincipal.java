package com.nightbeer.view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import com.nightbeer.buildmethods.Build;
import com.nightbeer.dao.itemsDAO;
import com.nightbeer.model.items;

@SuppressWarnings("serial")
public class mPrincipal extends JFrame{
	
	private JPanel contentPane = new JPanel();;
	private Build buildMethod = new Build();

	private JLabel labelNavBarTitle = buildMethod.createLabel(" Nightbeer Lounge", 600, 30, 22, 0, 20);
	private JButton buttonLogin = buildMethod.createButton("", 0, 0, 5, 5, 14);
	private JButton buttonMinimize = buildMethod.createButton("-", 0, 0, 2, 2, 12);
	private JButton buttonExit = buildMethod.createButton("X", 0, 0, 2, 2, 14); 
	
	private JTextField textFieldSearch = buildMethod.createTextField("", 0, 0, 40, 4);
	private JComboBox<?> comboBoxType = buildMethod.createComboBox("", 0, 0, 10, 4);
	private JComboBox<?> comboBoxBrand = buildMethod.createComboBox("", 0, 0, 10, 4);
	
	
    private JTable tabela;
    private DefaultTableModel dados;
    
    public void listar() {	
    	itemsDAO dao = new itemsDAO();
    	List<items> lista = dao.listar();
    		dados.setNumRows(0);
    		for(items i : lista) {
    			dados.addRow(new Object[] {
    					i.getCodigo(),	
    					i.getProduto(),
    					i.getTipo(),
    					i.getMarca(),
    					i.getEstoque(),
    					i.getPreco()
    					
    			});
    		}
    }
    
	public mPrincipal() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(960, 560));
		setTitle("NightBeer System");
        setResizable(false);
        setUndecorated(true);
        contentPane.setLayout(new BorderLayout());
		
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                WindowActivated(evt);
            }
        });
        
        
        containerNavBar();
        containerCenter();
        containerEast();
       
		setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
	}
	
	public void containerNavBar() {
		createIcon();
	
        //
        // NavBar Items
		labelNavBarTitle.setHorizontalAlignment(SwingConstants.LEFT);
		labelNavBarTitle.setBackground(buildMethod.cBackground);
        
		buttonLogin.setBackground(buildMethod.cBackground);
		buttonLogin.setIcon(iconUserImage);
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        mLogIn mLogin = new mLogIn(mPrincipal.this);
			    mLogin.setVisible(true);
			     
			}
		});
        
		buttonMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});

		
        buttonExit.setBackground(buildMethod.cBtnClose);		
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();	
				}
		});
		
		JPanel painelNavBarWest = new JPanel();
		painelNavBarWest.setLayout(new BorderLayout());
		painelNavBarWest.add(iconLogoField, BorderLayout.WEST);
		painelNavBarWest.setBackground(buildMethod.cBackground);
		painelNavBarWest.add(labelNavBarTitle);
		
		JPanel painelNavBarEast = new JPanel();
		painelNavBarEast.setLayout(new FlowLayout());
		painelNavBarEast.setBackground(buildMethod.cBackground);
			painelNavBarEast.add(buttonLogin, BorderLayout.SOUTH);
			painelNavBarEast.add(buttonMinimize);
			painelNavBarEast.add(buttonExit);
		
		JPanel painelNavBar = new JPanel();
			painelNavBar.setLayout(new BorderLayout());
			painelNavBar.setBackground(buildMethod.cBackground);
			painelNavBar.add(painelNavBarWest, BorderLayout.WEST);
			painelNavBar.add(painelNavBarEast, BorderLayout.EAST);

		
		
		contentPane.add(painelNavBar, BorderLayout.NORTH);
		
	}
	
    public void containerCenter() {
    	initSearch();
    	
        JPanel containerCenter = new JPanel();
        containerCenter.setLayout(new BorderLayout());
        containerCenter.setPreferredSize(buildMethod.createResponsive(65, 100));
        
        JPanel containerSearch = new JPanel();
        containerSearch.setLayout(new BoxLayout(containerSearch, BoxLayout.Y_AXIS));
        containerSearch.setPreferredSize(buildMethod.createResponsive(100, 8));
        
        	containerSearch.add(Box.createVerticalGlue());
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            searchPanel.add(textFieldSearch);
            searchPanel.add(comboBoxType);
            searchPanel.add(comboBoxBrand);
            containerSearch.add(searchPanel);
            containerSearch.add(Box.createVerticalGlue());
            containerCenter.add(containerSearch, BorderLayout.NORTH);
        
        JPanel containerTable = new JPanel();
        containerTable.setLayout(new BorderLayout());
        containerTable.setPreferredSize(buildMethod.createResponsive(100, 66));
        containerTable.setBackground(buildMethod.cBtn);    
	        initTable();
	        containerTable.add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        JPanel containerInfoItem = new JPanel();
        containerInfoItem.setLayout(new BorderLayout());
        containerInfoItem.setBackground(buildMethod.cBtnClose);
        containerInfoItem.setPreferredSize(buildMethod.createResponsive(100, 26));
        
        containerCenter.add(containerSearch, BorderLayout.NORTH);
        containerCenter.add(containerTable, BorderLayout.CENTER);
        containerCenter.add(containerInfoItem, BorderLayout.SOUTH);
        
        contentPane.add(containerCenter, BorderLayout.CENTER);
        pack();
    }
	
    private void initSearch() {
        textFieldSearch.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, buildMethod.cBtn));
        textFieldSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                textFieldSearchReleased(evt);
            }
        });
        textFieldSearch.requestFocus();
    }
      
    private void textFieldSearchReleased(KeyEvent evt) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelo);
        tabela.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + textFieldSearch.getText(), 0, 1, 2, 3));
    }
    
    private void initTable() {
    	String[] colunas = {"Código", "Produto", "Tipo", "Marca", "Estoque", "Preço"};
        dados = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(dados);
        tabela.setPreferredSize(buildMethod.createResponsive(20, 100));
        
        tabela.getTableHeader().setReorderingAllowed(false);
        
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            TableColumn column = tabela.getColumnModel().getColumn(i);
            column.setResizable(false);
        }
    }

    public void containerEast() {
		
		JPanel containerEast = new JPanel();
		containerEast.setLayout(new FlowLayout());
		containerEast.setBackground(buildMethod.cBtn);
		containerEast.setPreferredSize(buildMethod.createResponsive(35, 100));

		contentPane.add(containerEast, BorderLayout.EAST);
	}

	public void createIcon() {
		iconLogoImage = buildMethod.createImage("../images/nightbeerIcon.jpg", 60, 60);
		iconLogoField = new JLabel(iconLogoImage);
		
		iconUserImage = buildMethod.createImage("../images/iconUserLogin.png", 35, 35);
		new JLabel(iconUserImage);
	}

    // method to close Main menu
    public void closeMPrincipal() {
        dispose();
    }

	private void WindowActivated(WindowEvent evt) {
		listar();
	}
    
	// Running jPanel
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mPrincipal frame = new mPrincipal();
					frame.setBounds(Build.bounds);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	//
	//
	//
	//
	private JLabel iconLogoField;
	private ImageIcon iconLogoImage;
	
	private ImageIcon iconUserImage;
	
	
}
