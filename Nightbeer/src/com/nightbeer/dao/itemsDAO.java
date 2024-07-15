package com.nightbeer.dao;

import com.nightbeer.jdbc.connectionSQL;
import com.nightbeer.model.items;

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
        this.connection = new connectionSQL().getConnect();
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
            e.printStackTrace();
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
    
    public items getItemById(int id) throws SQLException {
    	items item = null;
    	ResultSet rs = null;
    	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM items WHERE codigo = ?");
    	
    	try {
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
    		
            if (rs.next()) {
                item = new items();
                item.setCodigo(rs.getInt("codigo"));
                item.setProduto(rs.getString("produto"));
                item.setTipo(rs.getString("tipo"));
                item.setMarca(rs.getString("marca"));
                item.setEstoque(rs.getInt("estoque"));
                item.setPreco(rs.getDouble("preco"));
            }
    		
    	} catch (SQLException ex) {
            System.out.println("Erro ao buscar item por ID: " + ex.getMessage());
            throw ex; 
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
            	connection.close();
            }
        }
    	return item;
    }

}
