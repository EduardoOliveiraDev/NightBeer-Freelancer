package com.nightbeer.model;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class editingTable {

    // Método para remover colunas da tabela
    public void removeColumnsFromTable(JTable table, int... columnIndices) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = columnIndices.length - 1; i >= 0; i--) {
            int index = columnIndices[i];
            TableColumn column = columnModel.getColumn(index);
            columnModel.removeColumn(column);
        }
    }

    // Método para definir a largura máxima das colunas
    public void setColumnMaxWidths(JTable table, int... maxWidths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < maxWidths.length && i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setMaxWidth(maxWidths[i]);
        }
    }

    // Método para definir o alinhamento das células
    public void setColumnAlignments(JTable table, int... alignments) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < alignments.length && i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(alignments[i]);
            column.setCellRenderer(renderer);
        }
    }
    
	
}
