package com.nightbeer.dao;

import com.nightbeer.jdbc.connectSQL;
import com.nightbeer.model.items;
import com.nightbeer.view.mAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class itemsDAO {

    private Connection connection;

    public itemsDAO() {
        this.connection = new connectSQL().getConnect();
    }
    
    public int getEstoque(int codigo) {
        String sql = "SELECT estoque FROM items WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("estoque");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    } 
    
    public void updateEstoque(int codigo, int novoEstoque) {
        String sql = "UPDATE items SET estoque = ? WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, novoEstoque);
            stmt.setInt(2, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveItems(items item) {
        String sql = "INSERT INTO items (produto, tipo, marca, estoque, preco) VALUES (?, ?, ?, ?, ?)";
        try ( 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getProduto());
            stmt.setString(2, item.getTipo());
            stmt.setString(3, item.getMarca());
            stmt.setInt(4, item.getEstoque());
            stmt.setDouble(5, item.getPreco());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editItems(items item) {
        String sql = "UPDATE items SET produto = ?, tipo = ?, marca = ?, estoque = ?, preco = ? WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getProduto());
            stmt.setString(2, item.getTipo());
            stmt.setString(3, item.getMarca());
            stmt.setInt(4, item.getEstoque());
            stmt.setDouble(5, item.getPreco());
            stmt.setInt(6, item.getCodigo());
            stmt.executeUpdate();
            
            updateEstoque(item.getCodigo(), item.getEstoque());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteItem(int codigo) {
        try {
        	String sql = "DELETE FROM items WHERE codigo = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar a Marca: " + e.getMessage());
            e.printStackTrace(); // Adiciona o rastreamento da pilha para depuração
        }
    }
    
    public List<items> listar() {
        List<items> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM items";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items obj = new items();
                obj.setCodigo(rs.getInt("codigo"));
                obj.setProduto(rs.getString("produto"));
                obj.setTipo(rs.getString("tipo"));
                obj.setMarca(rs.getString("marca"));
                obj.setEstoque(rs.getInt("estoque"));
                obj.setPreco(rs.getDouble("preco"));
                lista.add(obj);
            }
            return lista;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista" + e);
        }

        return lista;
    }

}
