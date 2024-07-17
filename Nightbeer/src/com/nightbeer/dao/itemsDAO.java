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
    
    public int getEstoque(int codigo) throws SQLException {
    	PreparedStatement stmt = connection.prepareStatement
    			("SELECT estoque FROM items WHERE codigo = ?");
    	
        try {
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("estoque");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pegar quantidade do estoque");
        } finally {
			stmt.close();
		}
		return codigo;
    } 
    
    public void updateEstoque(int codigo, int novoEstoque) throws SQLException {
    	PreparedStatement stmt = connection.prepareStatement
    			("UPDATE items SET estoque = ? WHERE codigo = ?");
    	
        try {
            stmt.setInt(1, novoEstoque);
            stmt.setInt(2, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o estoque");
        } finally {
			stmt.close();
		}
    }
    
    public void saveItems(items item) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement
        		("INSERT INTO items (produto, tipo, marca, estoque, preco) VALUES (?, ?, ?, ?, ?)");
        
        try {
            stmt.setString(1, item.getProduto());
            stmt.setString(2, item.getTipo());
            stmt.setString(3, item.getMarca());
            stmt.setInt(4, item.getEstoque());
            stmt.setDouble(5, item.getPreco());
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar Items");

        } finally {
			stmt.close();
		}
    }

    public void editItems(items item) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement
        		("UPDATE items SET produto = ?, tipo = ?, marca = ?, estoque = ?, preco = ? WHERE codigo = ?");
        
        try {
            stmt.setString(1, item.getProduto());
            stmt.setString(2, item.getTipo());
            stmt.setString(3, item.getMarca());
            stmt.setInt(4, item.getEstoque());
            stmt.setDouble(5, item.getPreco());
            stmt.setInt(6, item.getCodigo());
            stmt.executeUpdate();
            
            updateEstoque(item.getCodigo(), item.getEstoque());
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o item");
        }finally {
			stmt.close();
		}
    }
    
    public void deleteItem(int codigo) throws SQLException {
    	PreparedStatement stmt = connection.prepareStatement
        		("DELETE FROM items WHERE codigo = ?");
    	
    	try {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar a Marca");
        } finally {
			stmt.close();
		}
    }
    
    public List<items> listar() throws SQLException {
        List<items> lista = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement
        		("SELECT * FROM items");
        
        ResultSet rs = stmt.executeQuery();
        try {
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
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista");
        } finally {
        	rs.close();
			stmt.close();
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
            System.out.println("Erro ao buscar item por ID");
            throw ex; 
        } finally {
        	rs.close();
        	stmt.close();
        }
        return item;
    }


}
