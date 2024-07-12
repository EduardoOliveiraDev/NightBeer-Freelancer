package com.nightbeer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.nightbeer.jdbc.connectSQL;

public class brandsDAO {

    private Connection connection;

    public brandsDAO() {
        this.connection = new connectSQL().getConnect();
    }
	
    public void saveBrand(String tipo, String marca) {
        try {
            String sql = "INSERT INTO marca (marca, marca_tipo) VALUES(?,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, marca);
            stmt.setString(2, tipo);
            
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Marca salva com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a Marca" + e);
        }
    }

    public void editBrand(String tipo, String marcaAntiga, String marcaNova) {
        try {
            String sql = "UPDATE marca SET marca = ? WHERE marca_tipo = ? AND marca = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, marcaNova);
            stmt.setString(2, tipo);
            stmt.setString(3, marcaAntiga);

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Marca editada com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar a Marca" + e);
            System.out.println(e);
        }
    }

    public void deleteBrand(String tipo, String marca) {
        try {
            String sql = "DELETE FROM marca WHERE marca_tipo = ? AND marca = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, tipo);
            stmt.setString(2, marca);
            
            int rowsAffected = stmt.executeUpdate(); // Captura o número de linhas afetadas
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Marca deletada com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma marca encontrada para deletar");
            }
            
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar a Marca: " + e.getMessage());
            e.printStackTrace(); // Adiciona o rastreamento da pilha para depuração
        }
    }
    
    public List<String> listarMarcas() {
        List<String> marcas = new ArrayList<>();

        try {
            String sql = "SELECT marca FROM marca";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                marcas.add(rs.getString("marca"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar marcas: " + e);
        }
        return marcas;
    }
    
    public List<String> listarMarcasPorTipo(String tipo) {
        List<String> marcas = new ArrayList<>();
        String sql = "SELECT DISTINCT marca FROM marca WHERE marca_tipo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    marcas.add(rs.getString("marca"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marcas;
    }



}
