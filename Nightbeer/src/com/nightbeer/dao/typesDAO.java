package com.nightbeer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.nightbeer.jdbc.connectSQL;

public class typesDAO {

    private Connection connection;

    public typesDAO() {
        this.connection = new connectSQL().getConnect();
    }
	
    public void saveTypes(String newType) {
        try {
            String sql = "INSERT INTO tipo (tipo) VALUES(?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, newType);
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Tipo salvo com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o Tipo" + e);
        }
    }
    
    public void editType(String oldTipo, String newTipo) {
        try {
            // Atualiza o tipo na tabela 'tipo'
            String sqlTipo = "UPDATE tipo SET tipo = ? WHERE tipo = ?";
            PreparedStatement stmtTipo = connection.prepareStatement(sqlTipo);
            stmtTipo.setString(1, newTipo);
            stmtTipo.setString(2, oldTipo);
            stmtTipo.executeUpdate();
            stmtTipo.close();
            
            // Atualiza a coluna 'marca_tipo' na tabela 'marca'
            String sqlMarca = "UPDATE marca SET marca_tipo = ? WHERE marca_tipo = ?";
            PreparedStatement stmtMarca = connection.prepareStatement(sqlMarca);
            stmtMarca.setString(1, newTipo);
            stmtMarca.setString(2, oldTipo);
            stmtMarca.executeUpdate();
            stmtMarca.close();
            
            JOptionPane.showMessageDialog(null, "Tipo e marcas associadas atualizadas com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o tipo e marcas associadas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteType(String tipo) {
        try {
            String sql = "DELETE FROM tipo WHERE tipo = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, tipo);
            
            int rowsAffected = stmt.executeUpdate(); // Captura o número de linhas afetadas
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Tipo deletado com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum tipo encontrado para deletar");
            }
            
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar tipo: " + e.getMessage());
            e.printStackTrace(); // Adiciona o rastreamento da pilha para depuração
        }
    }
    
    public void editTypeInTwoTables(String oldType, String newType) {
        try {
            // Update na tabela 'tipo'
            String sqlTipo = "UPDATE tipo SET tipo = ? WHERE tipo = ?";
            PreparedStatement stmtTipo = connection.prepareStatement(sqlTipo);
            stmtTipo.setString(1, newType);
            stmtTipo.setString(2, oldType);
            stmtTipo.executeUpdate();
            stmtTipo.close();

            // Update na tabela 'marca'
            String sqlMarca = "UPDATE marca SET marca_tipo = ? WHERE marca_tipo = ?";
            PreparedStatement stmtMarca = connection.prepareStatement(sqlMarca);
            stmtMarca.setString(1, newType);
            stmtMarca.setString(2, oldType);
            stmtMarca.executeUpdate();
            stmtMarca.close();

            JOptionPane.showMessageDialog(null, "Tipo editado com sucesso em ambas as tabelas.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o tipo em ambas as tabelas: " + e);
            System.out.println(e);
        }
    }
    
    public List<String> listarTipos() {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT DISTINCT tipo FROM tipo";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tipos.add(rs.getString("tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipos;
    }
	
    
}
