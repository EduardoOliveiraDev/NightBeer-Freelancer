package com.nightbeer.dao;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nightbeer.jdbc.connectionSQL;
import com.nightbeer.model.historic;

public class purchasesHistoricDAO {

    private Connection connection;

    public purchasesHistoricDAO() {
        this.connection = new connectionSQL().getConnect();
    }

    public void saveHistotic(Map<Integer, Map<String, Object>> tableData, double totalPreco) throws SQLException {
    	PreparedStatement stmt = connection.prepareStatement
    			("INSERT INTO historico_de_compras (hashmap, total) VALUES (?, ?)");
    	
        try {
        	String hashString = convertHashMapToString(tableData);
        	stmt.setString(1, hashString);
        	stmt.setDouble(2, totalPreco);
	        stmt.executeUpdate();  
        	
        } catch (Exception e) {
        	System.out.println("Erro no historico");
        } finally {
        	stmt.close();
        }
        
    }

    public static Map<Integer, Map<String, Object>> getShoppingCart(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();
        Map<Integer, Map<String, Object>> tableData = new HashMap<>();

        for (int i = 0; i < rowCount; i++) {
            Map<String, Object> rowData = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                String columnName = model.getColumnName(j);
                Object cellValue = model.getValueAt(i, j);
                rowData.put(columnName, cellValue);
            }
            tableData.put(i, rowData);
        }

        return tableData;
    }
    
    private static String convertHashMapToString(Map<Integer, Map<String, Object>> tableData) {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.toJson(tableData);
    }

    public static Map<Integer, Map<String, Object>> convertStringToHashMap(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, Map<String, Object>>>() {}.getType();
        return gson.fromJson(jsonString, type);
    }
    
    public List<historic> listHistoric() throws SQLException {
        List<historic> listHistoric = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement
        		("SELECT * FROM historico_de_compras"); 
        
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                historic obj = new historic();
                obj.setId(rs.getInt("id"));

                LocalDateTime date = rs.getTimestamp("data").toLocalDateTime();
                obj.setDate(date);
                
                obj.setHashmapJSON(rs.getString("hashmap"));
                obj.setTotal(rs.getDouble("total"));
                listHistoric.add(obj);
            }
            return listHistoric;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista" + e);
        } finally {
			rs.close();
			stmt.close();
		}

        return listHistoric;
    }

}
