package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.nightbeer.dao.itemsDAO;
import com.nightbeer.model.items;

public class BuildMAdmin {

	private BuildMethods buildMethod = new BuildMethods();
    private JTable tabela;
    private DefaultTableModel dados;
	private Color colorButton = buildMethod.colorButton;   
	private JTextField textFieldSearch = buildMethod.createTextField("", 40, 4);
	private JComboBox<?> comboBoxType = buildMethod.createComboBox("", 10, 4);
	private JComboBox<?> comboBoxBrand = buildMethod.createComboBox("", 10, 4);
	
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
    
	public JPanel containerCenter() {
    	
        JPanel containerCenter = buildMethod.createPanel(65, 100);
        containerCenter.setLayout(new BorderLayout());

	    JPanel containerTable = buildMethod.createPanel(100, 66);
	    containerTable.setLayout(new BorderLayout());
	    containerTable.setBackground(colorButton);   
	        
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
		containerTable.add(new JScrollPane(tabela), BorderLayout.CENTER);
		
		JPanel containerSearch = buildMethod.createPanel(100, 8);
		containerSearch.setLayout(new BoxLayout(containerSearch, BoxLayout.Y_AXIS));
		containerSearch.add(Box.createVerticalGlue());
	        
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		searchPanel.add(textFieldSearch);
		searchPanel.add(comboBoxType);
		searchPanel.add(comboBoxBrand);
		containerSearch.add(searchPanel);
		containerSearch.add(Box.createVerticalGlue());
		containerCenter.add(containerSearch, BorderLayout.NORTH);
        
		textFieldSearch.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, colorButton));
		textFieldSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
				TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelo);
				tabela.setRowSorter(trs);
				trs.setRowFilter(RowFilter.regexFilter("(?i)" + textFieldSearch.getText(), 0, 1, 2, 3));
			}
		});
	                 
        containerCenter.add(containerSearch, BorderLayout.NORTH);
        containerCenter.add(containerTable, BorderLayout.CENTER);
       
        return containerCenter;
    }

    public JPanel containerEast() {
		
		JPanel containerEast = buildMethod.createPanel(35, 100);
		containerEast.setLayout(new FlowLayout());
		containerEast.setBackground(colorButton);

		JPanel containerEastEdit = buildMethod.createPanel(32, 70);
		containerEastEdit.setLayout(new FlowLayout());
			
		containerEast.add(containerEastEdit);	
		return containerEast;
	}


	
}
