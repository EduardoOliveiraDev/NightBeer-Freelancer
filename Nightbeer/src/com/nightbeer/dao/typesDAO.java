package com.nightbeer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.nightbeer.jdbc.connectionSQL;

public class typesDAO {

    private Connection connection;

    public typesDAO() {
        this.connection = new connectionSQL().getConnect();
    }
 	 
    public void saveTypes(String newType) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement
        		("INSERT INTO tipo (tipo) VALUES(?)");

    	try {
            stmt.setString(1, newType);
            stmt.execute();

            JOptionPane.showMessageDialog(null, "Tipo salvo com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o Tipo");
        } finally {
            stmt.close();
		}
    }
    
    public void editType(String oldTipo, String newTipo) throws SQLException {
        PreparedStatement stmtTipo = connection.prepareStatement
        		("UPDATE tipo SET tipo = ? WHERE tipo = ?");
        
        PreparedStatement stmtMarca = connection.prepareStatement
        		("UPDATE marca SET marca_tipo = ? WHERE marca_tipo = ?");
        
        PreparedStatement stmtItem = connection.prepareStatement
        		("UPDATE items SET tipo = ? WHERE tipo = ?");
    	
        try {
            stmtTipo.setString(1, newTipo);
            stmtTipo.setString(2, oldTipo);
            stmtTipo.executeUpdate();
            
            stmtMarca.setString(1, newTipo);
            stmtMarca.setString(2, oldTipo);
            stmtMarca.executeUpdate();
            
            stmtItem.setString(1, newTipo);
            stmtItem.setString(2, oldTipo);
            stmtItem.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Tipo e marcas associadas atualizadas com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o tipo e marcas associadas");
            e.printStackTrace();
        } finally {
            stmtMarca.close();
            stmtTipo.close();
            stmtItem.close();
		}
    }

    public void deleteType(String tipo) throws SQLException {
        PreparedStatement stmtCountItems = connection.prepareStatement(
                "SELECT COUNT(*) AS total FROM items WHERE tipo = ?");

        PreparedStatement stmtCountBrands = connection.prepareStatement(
                "SELECT COUNT(*) AS total FROM marca WHERE marca_tipo = ?");

        PreparedStatement stmtDeleteTipo = connection.prepareStatement(
                "DELETE FROM tipo WHERE tipo = ?");

        try {
            stmtCountItems.setString(1, tipo);
            ResultSet rsItems = stmtCountItems.executeQuery();

            int totalItems = 0;
            if (rsItems.next()) {
                totalItems = rsItems.getInt("total");
            }

            stmtCountBrands.setString(1, tipo);
            ResultSet rsBrands = stmtCountBrands.executeQuery();

            int totalBrands = 0;
            if (rsBrands.next()) {
                totalBrands = rsBrands.getInt("total");
            }

            if (totalItems > 0) {
                JOptionPane.showMessageDialog(null, "Não é possível deletar o tipo. Existem " + totalItems + " itens associados a este tipo.");
            } else if (totalBrands > 0) {
                JOptionPane.showMessageDialog(null, "Não é possível deletar o tipo. Existem " + totalBrands + " marcas associadas a este tipo.");
            } else {
                stmtDeleteTipo.setString(1, tipo);
                int rowsAffected = stmtDeleteTipo.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Tipo deletado com sucesso");
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum tipo encontrado para deletar");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar tipo");
            e.printStackTrace();
        } finally {
            stmtCountItems.close();
            stmtCountBrands.close();
            stmtDeleteTipo.close();
        }
    }
  
    public List<String> listTypes() throws SQLException {
        List<String> tipos = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT DISTINCT tipo FROM tipo ORDER BY tipo ASC");
        
        try {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tipos.add(rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close(); // Fechar o statement no bloco finally para garantir que seja sempre fechado
        }
        
        return tipos;
    }
	
    
}
