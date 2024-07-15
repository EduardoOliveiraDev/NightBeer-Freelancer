package com.nightbeer.build;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.text.AbstractDocument;

import com.nightbeer.dao.brandsDAO;
import com.nightbeer.dao.itemsDAO;
import com.nightbeer.dao.typesDAO;
import com.nightbeer.model.items;
import com.nightbeer.view.mAdmin;
import com.nightbeer.view.mCreateTipoMarca;
import com.nightbeer.view.mPrincipal;

public class BuildMAdmin {

    private BuildMethods buildMethod = new BuildMethods();
    private Color colorTextWhite = buildMethod.colorTextWhite;
    private Color colorTextBlack = buildMethod.colorTextBlack;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;

    private Color colorWhiteClear = buildMethod.colorWhiteClear;

    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
    private Font FontRobotoPlainLarge = buildMethod.FontRobotoPlain28;

    
    private JPanel containerTable;
    private JTable tabela;
    private DefaultTableModel dados;

    private JPanel containerEastEditNorth;
    private JLabel labelTextTitle;
    private JLabel labelTextCodigo;
    private JLabel labelTextProduto;
    private JLabel labelTextTipo;
    private JLabel labelTextMarca;
    private JLabel labelTextEstoque;
    private JLabel labelTextPreco;

    private JLabel labelInfoItemCodigo;
    private JTextField textFieldInfoItemProduto;
    private JComboBox<String> comboBoxInfoItemTipo;
    private JComboBox<String> comboBoxInfoItemMarca;
    private JTextField textFieldInfoItemEstoque;
    private JTextField textFieldInfoItemPreco;

    private JLabel labelTipo;
    private JLabel labelMarca;

    private JPanel containerEastEditSouth;
    private JButton buttonDel;
    private JButton buttonEdit;
    private JButton buttonSave;
    private JButton buttonNew;
    
    private JButton buttonType;
    private JButton buttonBrand;
    private JButton buttonGoBack;
    private JButton buttonAddingEstoque;

    
    public void list() {
        itemsDAO dao = new itemsDAO();
        List<items> lista = dao.listar();
        dados.setNumRows(0);

        lista.forEach(i -> dados.addRow(new Object[] {
                i.getCodigo(),
                i.getProduto(),
                i.getTipo(),
                i.getMarca(),
                i.getEstoque(),
                i.getPreco() 
        }));
    } 
  
    public void listTypes() throws SQLException {
    	typesDAO tDao = new typesDAO();
        List<String> tipos = tDao.listTypes(); 
        comboBoxInfoItemTipo.removeAllItems(); 
        comboBoxInfoItemTipo.addItem("");
        
        tipos.forEach(tipo -> comboBoxInfoItemTipo.addItem(tipo)); 
    }
    
    public void listBrands() throws SQLException {
    	brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listBrand(); 
        comboBoxInfoItemMarca.removeAllItems(); 
        comboBoxInfoItemMarca.addItem("");
        
        marcas.forEach(marca -> comboBoxInfoItemMarca.addItem(marca)); 
    }
    
    public void listBrandsForTypes(String tipo) throws SQLException {
        brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listBrandsForTypes(tipo); 
        comboBoxInfoItemMarca.removeAllItems(); 
        comboBoxInfoItemMarca.addItem("");
        
        marcas.forEach(marca -> comboBoxInfoItemMarca.addItem(marca)); 
    }
    
    public JPanel containerCenter() throws SQLException {
        JPanel containerCenter = buildMethod.createPanel(65, 100, new BorderLayout(), colorBackgroundWhite, 0,0,25,25);
        
        containerTable();

        containerCenter.add(new BuildSearchBar(tabela).containerSearch(), BorderLayout.NORTH);
        containerCenter.add(containerTable, BorderLayout.CENTER);

        return containerCenter;
    }

    public JPanel containerTable() {
        containerTable = buildMethod.createPanel(100, 66, new BorderLayout(), colorBackgroundWhite, 0,0,0,0);
        String[] colunas = {"Código", "Produto", "Tipo", "Marca", "Estoque", "Preço"};
        dados = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            } 
        };
        tabela = new JTable(dados);
        tabela.getTableHeader().setReorderingAllowed(false);
        containerTable.add(new JScrollPane(tabela), BorderLayout.CENTER);

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            TableColumn column = tabela.getColumnModel().getColumn(i);
            column.setResizable(false);
        }
        

        return containerTable;
    }

    public JPanel containerEast() throws SQLException {
        JPanel containerEast = buildMethod.createPanel(35, 100, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);

        containerEastEditNorth();
        containerEastEditSouth();
        
        containerEast.add(containerEastEditNorth);
        containerEast.add(containerEastEditSouth);

        return containerEast;
    }

    public JPanel containerEastEditNorth() throws SQLException {
        containerEastEditNorth = buildMethod.createPanel(32, 29, new FlowLayout(FlowLayout.RIGHT), colorBackgroundWhite, 12,0,0,0);

        labelTextTitle = buildMethod.createLabel("Edição de itens", 29, 6, SwingConstants.CENTER, colorTextBlack, colorBackgroundWhite, FontRobotoPlainLarge, 0,0,0,0);
        labelTextCodigo = buildMethod.createLabel("Código", 10.3, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextProduto  = buildMethod.createLabel("Produto", 31.5, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextTipo  = buildMethod.createLabel("Tipo", 14.2, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextMarca  = buildMethod.createLabel("Marca", 14.1, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextEstoque  = buildMethod.createLabel("Estoque", 8.8, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextPreco  = buildMethod.createLabel("Preço", 10.3, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);

        labelInfoItemCodigo  = buildMethod.createLabel("", 10.3, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,0,0,10);
        textFieldInfoItemProduto  = buildMethod.createTextField("", 31.5, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,0,0,10);
        comboBoxInfoItemTipo  =  (JComboBox<String>) buildMethod.createComboBox("", 15.6, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,0,0,0);
        comboBoxInfoItemMarca  = (JComboBox<String>) buildMethod.createComboBox("", 15.6, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,0,0,0);
        textFieldInfoItemEstoque  = buildMethod.createTextField("", 10.3, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        textFieldInfoItemPreco = buildMethod.createTextField("", 10.3, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        
        ((AbstractDocument) textFieldInfoItemProduto.getDocument()).setDocumentFilter(new LimitDocumentFilter(255));
        ((AbstractDocument) textFieldInfoItemEstoque.getDocument()).setDocumentFilter(new LimitDocumentFilter(11));
        
        buttonGoBack = buildMethod.createButton("<", 2.2, 2.5, SwingConstants.CENTER, colorBackgroundWhite, colorBlackBackground);
        buttonGoBack.setVisible(false);
        buttonGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				resetForm();
			}
		});
        
        buttonAddingEstoque = buildMethod.createButton("+", 1.3, 2, SwingConstants.CENTER, colorTextBlack, colorWhiteClear);
        buttonAddingEstoque.setFont(FontRobotoPlainSmall);
        buttonAddingEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabela.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Selecione um item na tabela para adicionar ao estoque.");
                    return;
                }

                String estoqueText = textFieldInfoItemEstoque.getText().trim();
                String quantidadeText = JOptionPane.showInputDialog(mAdmin.getInstance().getFrame(), "Informe a quantidade para adicionar ou remover:", "Controle de Estoque", JOptionPane.PLAIN_MESSAGE);

                if (estoqueText.isEmpty() || quantidadeText == null || quantidadeText.isEmpty()) {
                    JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Por favor, preencha todos os campos.");
                    return;
                }

                try {
                    int estoqueAtual = Integer.parseInt(estoqueText);
                    int quantidadeAdicionar = Integer.parseInt(quantidadeText);
                    int novoEstoque = estoqueAtual + quantidadeAdicionar;

                    textFieldInfoItemEstoque.setText(String.valueOf(novoEstoque));

                     itemsDAO dao = new itemsDAO();
                     int codigo = Integer.parseInt(labelInfoItemCodigo.getText());
                     dao.updateEstoque(codigo, novoEstoque);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Por favor, insira um número válido para a quantidade.");
                }
            }
        });


        
        textFieldInfoItemProduto.setEditable(false);
        comboBoxInfoItemTipo.setEnabled(false);
        comboBoxInfoItemMarca.setEnabled(false);
        textFieldInfoItemEstoque.setEditable(false);
        textFieldInfoItemPreco.setEditable(false);
        
        buttonType = buildMethod.createButton("+", 1.3, 2, SwingConstants.CENTER, colorTextBlack, colorWhiteClear);
        buttonType.setFont(FontRobotoPlainSmall);
        buttonBrand = buildMethod.createButton("+", 1.3, 2, SwingConstants.CENTER, colorTextBlack, colorWhiteClear);
        buttonBrand.setFont(FontRobotoPlainSmall);
        buttonType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mCreateTipoMarca mCreateTipoMarca = null;
				try {
					mCreateTipoMarca = new mCreateTipoMarca();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mCreateTipoMarca.setVisible(true);
			}
		});
        buttonBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mCreateTipoMarca mCreateTipoMarca = null;
				try {
					mCreateTipoMarca = new mCreateTipoMarca();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mCreateTipoMarca.setVisible(true);
			}
		});

        listBrands();
        listTypes();
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel selectionModel = tabela.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	setTextItemsInTabela();
                    resetForm();
                }
            }
        });
        
        comboBoxInfoItemTipo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedTipo = (String) comboBoxInfoItemTipo.getSelectedItem();
                    if (selectedTipo != null && !selectedTipo.isEmpty()) {
                        try {
							listBrandsForTypes(selectedTipo);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
                    } else {
                        try {
							listBrands();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} // Lista todas as marcas se nenhum tipo for selecionado
                    }
                }
            }
        });

        containerEastEditNorth.add(buttonGoBack);
        containerEastEditNorth.add(labelTextTitle);

        containerEastEditNorth.add(labelTextCodigo);
        containerEastEditNorth.add(labelTextEstoque);
        
        containerEastEditNorth.add(buttonAddingEstoque);
        
        containerEastEditNorth.add(labelTextPreco);
        containerEastEditNorth.add(labelInfoItemCodigo);
        containerEastEditNorth.add(textFieldInfoItemEstoque);
        containerEastEditNorth.add(textFieldInfoItemPreco);
        containerEastEditNorth.add(labelTextProduto);
        containerEastEditNorth.add(textFieldInfoItemProduto);
        containerEastEditNorth.add(labelTextTipo);
        
        containerEastEditNorth.add(buttonType);
        
        containerEastEditNorth.add(labelTextMarca);
        
        containerEastEditNorth.add(buttonBrand);

        
        containerEastEditNorth.add(comboBoxInfoItemTipo);
        containerEastEditNorth.add(comboBoxInfoItemMarca);

        return containerEastEditNorth;
    }
    
    private JPanel containerEastEditSouth() {
    	containerEastEditSouth = buildMethod.createPanel(31.5, 7, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);

        buttonNew = buildMethod.createButton("Novo", 6, 5, SwingConstants.CENTER, colorTextWhite, colorBlackBackground);
        buttonDel = buildMethod.createButton("Deletar", 6, 5, SwingConstants.CENTER, colorTextWhite, colorBlackBackground);
        buttonSave = buildMethod.createButton("Salvar", 6, 5, SwingConstants.CENTER, colorTextWhite, colorBlackBackground);
        buttonEdit = buildMethod.createButton("Editar", 6, 5, SwingConstants.CENTER, colorTextWhite, colorBlackBackground);
        buttonSave.setEnabled(false);
        
        buttonNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                labelInfoItemCodigo.setText("");
                textFieldInfoItemProduto.setText("");
                comboBoxInfoItemTipo.setSelectedItem("");
                comboBoxInfoItemMarca.setSelectedItem("");
                textFieldInfoItemEstoque.setText("");
                textFieldInfoItemPreco.setText("");

                textFieldInfoItemProduto.setEditable(true);
                comboBoxInfoItemTipo.setEnabled(true);
                comboBoxInfoItemMarca.setEnabled(true);
                textFieldInfoItemEstoque.setEditable(true);
                textFieldInfoItemPreco.setEditable(true);
                buttonGoBack.setVisible(true);

                buttonNew.setEnabled(false);
                buttonDel.setEnabled(false);
                buttonSave.setEnabled(true);
                buttonEdit.setEnabled(false);

            }
        });
        buttonDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoString = labelInfoItemCodigo.getText();
				String produto = textFieldInfoItemProduto.getText();
				String tipo = (String) comboBoxInfoItemTipo.getSelectedItem();
				String marca = (String) comboBoxInfoItemMarca.getSelectedItem();
				String estoque = textFieldInfoItemEstoque.getText();
				String preco = textFieldInfoItemPreco.getText();

				int codigo = Integer.parseInt(codigoString);
				
				if (labelInfoItemCodigo != null && !codigoString.isEmpty()) { 
	        		int response = JOptionPane.showConfirmDialog(mAdmin.getInstance().getFrame(), "Você deseja apagar o produto: \n" +
	        				"\n Codigo: " + codigoString +
	        				"\n Produto: " + produto +
	        				"\n Tipo: " + tipo +
	        				"\n Marca: " + marca +
	        				"\n Estoque: " + estoque +
	        				"\n Preço: " + preco,
	        				"\n Apagar produto", 
	        				JOptionPane.YES_OPTION);
	        		
	        		if (response == JOptionPane.YES_OPTION) {
	        			itemsDAO dao = new itemsDAO();
	        			dao.deleteItem(codigo);
	                    try {
							reload();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	        			JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Produto apagado");
	        		} 
					
					
				} else { 
					JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Selecione um item para deletar");
				}
					

				
				
			}
		});
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String codigoText = labelInfoItemCodigo.getText().trim();
                    String produto = textFieldInfoItemProduto.getText().trim();
                    String tipo = (String) comboBoxInfoItemTipo.getSelectedItem();
                    String marca = (String) comboBoxInfoItemMarca.getSelectedItem();
                    String estoqueText = textFieldInfoItemEstoque.getText().trim();
                    String precoText = textFieldInfoItemPreco.getText().trim().replace("R$", "").trim();
                    precoText = precoText.replace(",", ".");

                    if (produto.isEmpty() || tipo == null || tipo.isEmpty() || marca == null || marca.isEmpty() || estoqueText.isEmpty() || precoText.isEmpty()) {
                        JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Por favor, preencha todos os campos.");
                        return;
                    }

                    int estoque = Integer.parseInt(estoqueText);
                    double preco = Double.parseDouble(precoText);

                    itemsDAO dao = new itemsDAO();
                    items item = new items();
                    item.setProduto(produto);
                    item.setTipo(tipo);
                    item.setMarca(marca);
                    item.setEstoque(estoque);
                    item.setPreco(preco);

                    if (codigoText == null || codigoText.isEmpty()) { // create
                        dao.saveItems(item);
                        JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Item criado com sucesso!");
                    } else { // edit
                        int codigo = Integer.parseInt(codigoText);
                        item.setCodigo(codigo);
                        dao.editItems(item);
                        JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Item atualizado com sucesso!");
                    }

                    reload();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Por favor, preencha todos os campos corretamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mAdmin.getInstance().getFrame(), "Erro ao salvar item: " + ex.getMessage());
                }
            }
        });
        buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                textFieldInfoItemProduto.setEditable(true);
                comboBoxInfoItemTipo.setEnabled(true);
                comboBoxInfoItemMarca.setEnabled(true);
                textFieldInfoItemEstoque.setEditable(true);
                textFieldInfoItemPreco.setEditable(true);
                buttonGoBack.setVisible(true);
                
                buttonNew.setEnabled(false);
                buttonDel.setEnabled(false);
                buttonSave.setEnabled(true);
                buttonEdit.setEnabled(false);
			}
		});
        
        containerEastEditSouth.add(buttonNew);
        containerEastEditSouth.add(buttonDel);
        containerEastEditSouth.add(buttonSave);
        containerEastEditSouth.add(buttonEdit);
        
        return containerEastEditSouth;
    }
    
    public void setTextItemsInTabela() {
        textFieldInfoItemProduto.setEditable(false);
        comboBoxInfoItemTipo.setEnabled(false);
        comboBoxInfoItemMarca.setEnabled(false);
        textFieldInfoItemEstoque.setEditable(false);
        textFieldInfoItemPreco.setEditable(false);
        
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow != -1) {
            Object codigo = tabela.getValueAt(selectedRow, 0);
            Object produto = tabela.getValueAt(selectedRow, 1);
            Object tipo = tabela.getValueAt(selectedRow, 2);
            Object marca = tabela.getValueAt(selectedRow, 3);
            Object estoque = tabela.getValueAt(selectedRow, 4);
            Object preco = tabela.getValueAt(selectedRow, 5);

            labelInfoItemCodigo.setText(codigo.toString());
            textFieldInfoItemProduto.setText(produto.toString());
            comboBoxInfoItemTipo.setSelectedItem(tipo.toString());
            comboBoxInfoItemMarca.setSelectedItem(marca.toString());
            textFieldInfoItemEstoque.setText(estoque.toString());
            textFieldInfoItemPreco.setText("R$ " + preco.toString());
        }
    }

    public void clearForm() {
        labelInfoItemCodigo.setText("");
        textFieldInfoItemProduto.setText("");
        comboBoxInfoItemTipo.setSelectedItem("");
        comboBoxInfoItemMarca.setSelectedItem("");
        textFieldInfoItemEstoque.setText("");
        textFieldInfoItemPreco.setText("");
    	
    }
    
    public void resetForm() {
        textFieldInfoItemProduto.setEditable(false);
        comboBoxInfoItemTipo.setEnabled(false);
        comboBoxInfoItemMarca.setEnabled(false);
        textFieldInfoItemEstoque.setEditable(false);
        textFieldInfoItemPreco.setEditable(false);
        
        buttonNew.setEnabled(true);
        buttonDel.setEnabled(true);
        buttonSave.setEnabled(false);
        buttonEdit.setEnabled(true);
        buttonGoBack.setVisible(false);
    }

    public void reload() throws SQLException {
        list();
        listTypes();
        listBrands();
        clearForm();
        resetForm();
    }
}
