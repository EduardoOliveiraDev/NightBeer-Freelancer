package com.nightbeer.dao;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class paymentMethodDAO {
	private static Map<Integer, Map<String, Object>> tabelaData;
	
	public static Map<Integer, Map<String, Object>> getShoppingCart(JTable table) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int rowCount = model.getRowCount();
	    int colCount = model.getColumnCount();
	    tabelaData = new HashMap<>();

	    // Define os nomes das colunas para renomear
	    Map<String, String> columnRenames = new HashMap<>();
	    columnRenames.put("Codigo", "Id");
	    columnRenames.put("Quantidade", "Und");
	    columnRenames.put("Preço", "Valor");
	    columnRenames.put("Preço total", "Total");

	    for (int i = 0; i < rowCount; i++) {
	        Map<String, Object> rowData = new HashMap<>();
	        for (int j = 0; j < colCount; j++) {
	            String columnName = model.getColumnName(j);

	            // Verifica se a coluna deve ser removida
	            if (columnName.equals("Estoque") || columnName.equals("Marca") || columnName.equals("Tipo")) {
	                continue;
	            }

	            // Renomeia a coluna se necessário
	            String newColumnName = columnRenames.getOrDefault(columnName, columnName);

	            // Adiciona o valor ao mapa da linha
	            Object cellValue = model.getValueAt(i, j);
	            rowData.put(newColumnName, cellValue);
	        }
	        tabelaData.put(i, rowData);
	    }

	    return tabelaData;
	}
	
    public Map<Integer, Map<String, Object>> getTabelaData() {
        return tabelaData;
    }
}
