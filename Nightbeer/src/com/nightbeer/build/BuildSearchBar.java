package com.nightbeer.build;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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
        comboBoxType.addItem("Tipos"); // Placeholder text
        
        tipos.forEach(tipo -> comboBoxType.addItem(tipo)); 
    }
    
    public void listBrands() throws SQLException {
        brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listBrand(); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("Marcas"); // Placeholder text
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
    
    public void listBrandsForTypes(String tipo) throws SQLException {
        brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listBrandsForTypes(tipo); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("Marcas"); // Placeholder text
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
    
    @SuppressWarnings("unchecked")
    public JPanel containerSearchMain() throws SQLException {
        containerSearch = buildMethod.createPanel(100, 7, null, colorBackgroundWhite, 0,0,0,0);
        containerSearch.setLayout(new BoxLayout(containerSearch, BoxLayout.Y_AXIS));
        containerSearch.add(Box.createVerticalGlue());
        
        textFieldSearch = buildMethod.createTextField("", 38, 4, SwingConstants.LEFT, Color.GRAY, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
        textFieldSearch.setText("Pesquisar..."); // Configura o texto padrão 
        comboBoxType = (JComboBox<String>) buildMethod.createComboBox("", 10, 4, Color.GRAY, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
        comboBoxBrand = (JComboBox<String>) buildMethod.createComboBox("", 10, 4, Color.GRAY, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
        buttonClearFilter = buildMethod.createButton("", 3, 4, SwingConstants.CENTER, colorTextBlack, colorBackgroundWhite);
        comboBoxType.setMaximumRowCount(8); 
        comboBoxBrand.setMaximumRowCount(8); 
        buttonClearFilter.setIcon(iconClearFilter);
        
        listTypes();
        listBrands(); 
        containerSearchMainFunction();

        searchPanelComponents = buildMethod.createPanel(100, 5, new FlowLayout(FlowLayout.LEFT), colorBackgroundWhite, 0,0,0,0);
        searchPanelComponents.add(textFieldSearch);
        searchPanelComponents.add(comboBoxType);
        searchPanelComponents.add(comboBoxBrand);
        searchPanelComponents.add(buttonClearFilter);
        containerSearch.add(searchPanelComponents);
        return containerSearch;
    }
    
    private void containerSearchMainFunction() {
        comboBoxType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTipo = (String) comboBoxType.getSelectedItem();
                
                if (selectedTipo != null && !selectedTipo.equals("Tipos")) {
                    try {
                        listBrandsForTypes(selectedTipo);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        listBrands();
                    } catch (SQLException e1) {
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
        
        comboBoxType.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selectedTipo = (String) comboBoxType.getSelectedItem();
                if (selectedTipo != null && !selectedTipo.equals("Tipos")) {
                    updateLists();
                    comboBoxType.setSelectedItem(selectedTipo);
                }
            }
        });

        comboBoxBrand.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selectedMarca = (String) comboBoxBrand.getSelectedItem();
                String selectedTipo = (String) comboBoxType.getSelectedItem();
                
                if (!selectedMarca.equals("Marcas")) {
                    comboBoxType.setSelectedItem("Tipos");
                }
                updateLists();
                comboBoxType.setSelectedItem(selectedTipo);
                comboBoxBrand.setSelectedItem(selectedMarca);
            }
        });
        
        textFieldSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                applyFilters();
            }
        });
        
        textFieldSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldSearch.getText().equals("Pesquisar...")) {
                    textFieldSearch.setText("");
                    textFieldSearch.setForeground(Color.BLACK); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldSearch.getText().isEmpty()) {
                    textFieldSearch.setText("Pesquisar...");
                    textFieldSearch.setForeground(Color.GRAY); // Define a cor cinza para o texto de placeholder
                }
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
                
                comboBoxType.setSelectedItem("Tipos");
                comboBoxBrand.setSelectedItem("Marcas");
                textFieldSearch.setText("Pesquisar...");
                textFieldSearch.setForeground(Color.GRAY); // Define a cor cinza para o texto de placeholder
                applyFilters();
            }
        });
    }
    
    private void applyFilters() {
        if (tabela != null) {
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelo);
            tabela.setRowSorter(trs);

            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            String selectedTipo = (String) comboBoxType.getSelectedItem();
            if (selectedTipo != null && !selectedTipo.equals("Tipos")) {
                filters.add(RowFilter.regexFilter("(?i)" + selectedTipo, 2));
            }

            String selectedMarca = (String) comboBoxBrand.getSelectedItem();
            if (selectedMarca != null && !selectedMarca.equals("Marcas")) {
                filters.add(RowFilter.regexFilter("(?i)" + selectedMarca, 3));
            }

            String textFilter = textFieldSearch.getText().trim();
            if (!textFilter.isEmpty() && !textFilter.equals("Pesquisar...")) {
                filters.add(RowFilter.regexFilter("(?i)" + textFilter, 0, 1, 2, 3));
            }

            trs.setRowFilter(RowFilter.andFilter(filters));
        }
    }
    
    public void updateLists() {
        try {
            listTypes();
            String selectedTipo = (String) comboBoxType.getSelectedItem();
            if (selectedTipo != null && !selectedTipo.equals("Tipos")) {
                listBrandsForTypes(selectedTipo);
            } else {
                listBrands();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar listas");
        }
    }
}
