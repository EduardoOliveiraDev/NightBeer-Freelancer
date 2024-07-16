package com.nightbeer.build;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.nightbeer.dao.brandsDAO;
import com.nightbeer.dao.typesDAO;

public class BuildSearchBar {
    private BuildMethods buildMethod = new BuildMethods();
    private Color colorTextBlack = buildMethod.colorTextBlack;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;
    
    private Color colorWhiteClear = buildMethod.colorWhiteClear;

    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
    
    private JTable tabela;
    
    private JPanel containerSearch;
    private JPanel searchPanelComponents;
    private JTextField textFieldSearch;
    private JButton buttonClearFilter; 
    private JComboBox<String> comboBoxType; 
    private JComboBox<String> comboBoxBrand; 

    public ImageIcon iconClearFilter = buildMethod.createImage("../images/iconClearFilter.png", 35, 35);
    
    public BuildSearchBar(JTable tabela) {
        this.tabela = tabela;
    }
    
    public void listTypes() throws SQLException {
        typesDAO tDao = new typesDAO();
        List<String> tipos = tDao.listTypes(); 
        comboBoxType.removeAllItems(); 
        comboBoxType.addItem("");
        
        tipos.forEach(tipo -> comboBoxType.addItem(tipo)); 
    }
    
    public void listBrands() throws SQLException {
        brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listBrand(); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("");
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
    
    public void listBrandsForTypes(String tipo) throws SQLException {
    	brandsDAO mDao = new brandsDAO();
    	List<String> marcas = mDao.listBrandsForTypes(tipo); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("");
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
    
    @SuppressWarnings("unchecked")
	public JPanel containerSearchMain() throws SQLException {
        containerSearch = buildMethod.createPanel(100, 7, null, colorBackgroundWhite, 0,0,0,0);
        containerSearch.setLayout(new BoxLayout(containerSearch, BoxLayout.Y_AXIS));
        containerSearch.add(Box.createVerticalGlue());
        
        textFieldSearch = buildMethod.createTextField("", 38, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
        comboBoxType = (JComboBox<String>) buildMethod.createComboBox("", 10, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
        comboBoxBrand = (JComboBox<String>) buildMethod.createComboBox("", 10, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
        buttonClearFilter = buildMethod.createButton("", 3, 4, SwingConstants.CENTER, colorTextBlack, colorBackgroundWhite);
        buttonClearFilter.setIcon(iconClearFilter);
        
        inicialiteFunctions();

        searchPanelComponents = buildMethod.createPanel(100, 5, new FlowLayout(FlowLayout.LEFT), colorBackgroundWhite, 0,0,0,0);
        searchPanelComponents.add(textFieldSearch);
        searchPanelComponents.add(comboBoxType);
        searchPanelComponents.add(comboBoxBrand);
        searchPanelComponents.add(buttonClearFilter);
        containerSearch.add(searchPanelComponents);
        return containerSearch;
    }
    
    private void applyFilters() {
        if (tabela != null) {
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelo);
            tabela.setRowSorter(trs);

            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            String selectedTipo = (String) comboBoxType.getSelectedItem();
            if (selectedTipo != null && !selectedTipo.isEmpty()) {
                filters.add(RowFilter.regexFilter("(?i)" + selectedTipo, 2));
            }

            String selectedMarca = (String) comboBoxBrand.getSelectedItem();
            if (selectedMarca != null && !selectedMarca.isEmpty()) {
                filters.add(RowFilter.regexFilter("(?i)" + selectedMarca, 3));
            }

            String textFilter = textFieldSearch.getText().trim();
            if (!textFilter.isEmpty()) {
                filters.add(RowFilter.regexFilter("(?i)" + textFilter, 0, 1, 2, 3));
            }

            trs.setRowFilter(RowFilter.andFilter(filters));
        }
    }

    private void inicialiteFunctions() throws SQLException {
        listTypes();
        listBrands(); 
        comboBoxType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTipo = (String) comboBoxType.getSelectedItem();
                
                if (selectedTipo != null && !selectedTipo.isEmpty()) {
                    try {
						listBrandsForTypes(selectedTipo);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                } else {
                    try {
						listBrands();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }

                applyFilters();
                
            }
        });
        
        comboBoxBrand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
        
        textFieldSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                applyFilters();
            }
        });

        buttonClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					listBrands();
					listTypes();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				comboBoxType.setSelectedItem("");
				comboBoxBrand.setSelectedItem("");
				textFieldSearch.setText("");
				applyFilters();
			}
		});
        
    }
}
