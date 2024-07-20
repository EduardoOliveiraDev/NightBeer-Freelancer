package com.nightbeer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.nightbeer.jdbc.connectionSQL;

public class brandsDAO {

    private Connection connection;

    public brandsDAO() {
        this.connection = new connectionSQL().getConnect();
    }
	
    public void saveBrand(String tipo, String marca) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement
        		("INSERT INTO marca (marca, marca_tipo) VALUES(?,?)");
    	try {
            stmt.setString(1, marca);
            stmt.setString(2, tipo);
            stmt.execute();

            JOptionPane.showMessageDialog(null, "Marca salva com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a Marca");
        } finally {
            stmt.close();

		}
    }

    public void editBrand(String tipo, String marcaAntiga, String marcaNova) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement
        		("UPDATE marca SET marca = ? WHERE marca_tipo = ? AND marca = ?");
        
        PreparedStatement stmtItem = connection.prepareStatement
        		("UPDATE items SET marca = ? WHERE marca = ?");

    	try {
            stmt.setString(1, marcaNova);
            stmt.setString(2, tipo);
            stmt.setString(3, marcaAntiga);
            stmt.execute();

            stmtItem.setString(1, marcaNova);
            stmtItem.setString(2, marcaAntiga);
            stmtItem.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Marca editada com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar a Marca");
            System.out.println(e);
        } finally {
            stmt.close();
            stmtItem.close();
		}
    }

    public void deleteBrand(String tipo, String marca) throws SQLException {
        PreparedStatement stmtCountItems = connection.prepareStatement
        		("SELECT COUNT(*) AS total FROM items WHERE tipo = ? AND marca = ?");;
        		
        PreparedStatement stmtDeleteMarca = connection.prepareStatement
        		("DELETE FROM marca WHERE marca_tipo = ? AND marca = ?");

        try {
            stmtCountItems.setString(1, tipo);
            stmtCountItems.setString(2, marca);
            ResultSet rs = stmtCountItems.executeQuery();
            
            int totalItems = 0;
            if (rs.next()) {
                totalItems = rs.getInt("total");
            }
            
            if (totalItems > 0) {
                JOptionPane.showMessageDialog(null, "Não é possível deletar a marca. Existem " + totalItems + " itens associados a esta marca e tipo.");
            } else {
                stmtDeleteMarca.setString(1, tipo);
                stmtDeleteMarca.setString(2, marca);
                
                int rowsAffected = stmtDeleteMarca.executeUpdate(); 
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Marca deletada com sucesso");
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma marca encontrada para deletar");
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar a marca: ");
            e.printStackTrace();
        } finally {
        	stmtCountItems.close();
        	stmtDeleteMarca.close();
        }
    }

    public List<String> listBrand() throws SQLException {
        List<String> marcas = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement
        		("SELECT marca FROM marca ORDER BY marca ASC");
        
        ResultSet rs = stmt.executeQuery();
        try {
            while (rs.next()) {
                marcas.add(rs.getString("marca"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar marcas");
        } finally {
        	rs.close();
			stmt.close();
		}
        return marcas;
    }
    
    public List<String> listBrandsForTypes(String tipo) throws SQLException {
        List<String> marcas = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement
        		("SELECT DISTINCT marca FROM marca WHERE marca_tipo = ? ORDER BY marca ASC");
        		
        try {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    marcas.add(rs.getString("marca"));
                }
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "Erro ao listar marcas e tipos");
        }
        return marcas;
    }
}
