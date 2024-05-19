package com.nightbeer.dao;

import com.nightbeer.jdbc.connectSQL;
import com.nightbeer.model.items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class itemsDAO {

	private Connection conn;
	
	public itemsDAO() {
		this.conn = new connectSQL().getConnect();	
	}
	
	public void saveItems(items obj) {
		try {
			
			String sql = "insert into items (produto, tipo, marca, estoque, preco)"
					+ "values(?,?,?,?,?)";
			
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, obj.getProduto());
			stmt.setString(2, obj.getTipo());
			stmt.setString(3, obj.getMarca());
			stmt.setInt(4, obj.getEstoque());
			stmt.setDouble(5, obj.getPreco());
			
			stmt.execute();
			
			stmt.close();
			
			JOptionPane.showMessageDialog(null, "Item salvo com sucesso");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao salvar o produto" + e);
		}
		
		
	}
	
	public items Search(String produto) {
		listar();
		try {
			String sql = "select * from items where produto =?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, produto);
			ResultSet rs = stmt.executeQuery();
			items obj = new items();
			if (rs.next()) {
				obj.setCodigo(rs.getInt("codigo"));
				obj.setProduto(rs.getString("produto"));
				obj.setTipo(rs.getString("tipo"));
				obj.setMarca(rs.getString("marca"));
				obj.setEstoque(rs.getInt("estoque"));
				obj.setPreco(rs.getDouble("preco"));
				
			}
			
			return obj;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "erro ao buscar" +  e);
		}
		return null;
	}
	
	public List<items>listar(){
		List<items> lista = new ArrayList<>();
		
		try {
			String sql = "select * from items";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
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
		return null;
	}
}
