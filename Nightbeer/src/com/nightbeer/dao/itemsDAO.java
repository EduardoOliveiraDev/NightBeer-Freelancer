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

	private Connection connection;
	
	public itemsDAO() {
		this.connection = new connectSQL().getConnect();	
	}
	
	public void saveItems(items objeto) {
		try {
			String sql = "INSERT INTO items (produto, tipo, marca, estoque, preco)"
					+ "VALUES(?,?,?,?,?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, objeto.getProduto());
			stmt.setString(2, objeto.getTipo());
			stmt.setString(3, objeto.getMarca());
			stmt.setInt(4, objeto.getEstoque());
			stmt.setDouble(5, objeto.getPreco());
			
			stmt.execute();
			
			stmt.close();
			
			JOptionPane.showMessageDialog(null, "Item salvo com sucesso");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao salvar o produto" + e);
		}
		
		
	}
	
	public List<items>listar(){
		// Set, Map e Hash (Java Util)
		List<items> lista = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM items";
			PreparedStatement stmt = connection.prepareStatement(sql);
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
			// Retornar Exception - Crie uma bussines exception, jogando um erro personalizado. Pilha de execução 
			JOptionPane.showMessageDialog(null, "Erro ao criar a lista" + e);
		}
		
		// Retornar Exception
		return lista;
	}
}
