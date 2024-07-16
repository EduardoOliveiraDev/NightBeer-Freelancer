package com.nightbeer.build;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.text.AbstractDocument;

import com.nightbeer.dao.itemsDAO;
import com.nightbeer.dao.purchasesHistoricDAO;
import com.nightbeer.model.items;
import com.nightbeer.view.mHistoricTableBuy;
import com.nightbeer.view.mPrincipal;

public class BuildMPrincipal {
    private BuildMethods buildMethod = new BuildMethods();
    private Color colorTextWhite = buildMethod.colorTextWhite;
    private Color colorTextBlack = buildMethod.colorTextBlack;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;
    
    private Color colorWhiteClear = buildMethod.colorWhiteClear;
    
    private Color colorButtonGreen = buildMethod.colorButtonGreen;
    private Color colorButtonRed = buildMethod.colorButtonRed;
    
    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
    private Font FontRobotoPlainMedium = buildMethod.FontRobotoPlain28;

    private JPanel containerTableItems;
    private JTable tabelaItems;
    private DefaultTableModel dadosItems;
    
    private JPanel containerInfoItem;
    private JLabel labelTextCodigo;
    private JLabel labelTextProduto;
    private JLabel labelTextTipo;
    private JLabel labelTextMarca;
    private JLabel labelTextEstoque;
    private JLabel labelTextPreco;
    private JLabel labelTextQuantidade;
    private JTextField textFieldInfoItemCodigo;
    private JLabel labelInfoItemProduto;
    private JLabel labelInfoItemTipo;
    private JLabel labelInfoItemMarca;
    private JLabel labelInfoItemEstoque;
    private JLabel labelInfoItemPreco;
    private JSpinner SpinnerInfoItemQuantidade;
    private int previosValue;
    
    private JButton buttonAddingItemForTableBuy;
    private JButton buttonRemoveItemForTableBuy;
    
    private JPanel containerTableBuy;
    private JTable tabelaBuy;
    private JButton buttonPreviusBuy;
    private DefaultTableModel dadosBuy;
    
    private JPanel containerRequestButtons;
    private JLabel labelTotalBuyText;
    private JLabel labelTotalBuyPrice;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    
    private Timer returnItemsTimer;
    private JFrame frame;
    
    public BuildMPrincipal() {
        returnItemsTimer = new Timer(600000, e -> { // 600.000 = 10 minutos
            returnItemsToStock();
            refreshTotalPrice(); 
            returnItemsTimer.stop(); 
        });
        returnItemsTimer.setRepeats(false);

    }
    
    public void listItems() {
        itemsDAO dao = new itemsDAO();
        List<items> lista = dao.listar();
        dadosItems.setNumRows(0);

        lista.forEach(i -> dadosItems.addRow(new Object[] {
            i.getCodigo(),
            i.getProduto(),
            i.getTipo(),
            i.getMarca(),
            i.getEstoque(),
            i.getPreco()
        }));
    }

    private double calcTotalCust() {
        double sumTotalPriceOfItems = 0.0;
        int rowCount = dadosBuy.getRowCount();
        int columnIndex = 7; 
        
        for (int row = 0; row < rowCount; row++) {
            Object value = dadosBuy.getValueAt(row, columnIndex);
            if (value instanceof Double) {
            	sumTotalPriceOfItems += (Double) value;
            } else if (value instanceof String) {
                try {
                	sumTotalPriceOfItems += Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return sumTotalPriceOfItems;
    }

    private void refreshTotalPrice() {
        double sumTotalPriceOfItems = calcTotalCust();
        labelTotalBuyPrice.setText(String.format("R$ " + sumTotalPriceOfItems));
    }
    
	public JPanel containerCenter() throws SQLException {
        JPanel containerCenter = buildMethod.createPanel(65, 100, new BorderLayout(), colorBackgroundWhite, 0,0,0,25);
        containerCenter.add(new BuildSearchBar(tabelaItems).containerSearchMain(), BorderLayout.NORTH);
        containerCenter.add(containerTableItems(), BorderLayout.CENTER);
        containerCenter.add(containerLabelsInfoItem(), BorderLayout.SOUTH);
        return containerCenter;
    }

	@SuppressWarnings("serial")
	public JPanel containerTableItems() {
        containerTableItems = buildMethod.createPanel(100, 64, new BorderLayout(), colorBackgroundWhite, 0,0,0,0);

        String[] colunasItems = {"Código", "Produto", "Tipo", "Marca", "Estoque", "Preço"};
        dadosItems = new DefaultTableModel(colunasItems, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaItems = new JTable(dadosItems);
        tabelaItems.getTableHeader().setReorderingAllowed(false);
        for (int i = 0; i < tabelaItems.getColumnCount(); i++) {
            TableColumn column = tabelaItems.getColumnModel().getColumn(i);
            column.setResizable(false);
        }

        containerTableItems.add(new JScrollPane(tabelaItems), BorderLayout.CENTER);
        
        tabelaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = tabelaItems.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabelaItems.getSelectedRow();
                    if (selectedRow != -1) {
                    	tabelaBuy.clearSelection();
                    	
                        Object Codigo = tabelaItems.getValueAt(selectedRow, 0);
                        Object Produto = tabelaItems.getValueAt(selectedRow, 1);
                        Object Tipo = tabelaItems.getValueAt(selectedRow, 2);
                        Object Marca = tabelaItems.getValueAt(selectedRow, 3);
                        Object Estoque = tabelaItems.getValueAt(selectedRow, 4);
                        Object Preco = tabelaItems.getValueAt(selectedRow, 5);
                        
                        textFieldInfoItemCodigo.setText(Codigo.toString());
                        labelInfoItemProduto.setText(Produto.toString());
                        labelInfoItemTipo.setText(Tipo.toString());
                        labelInfoItemMarca.setText(Marca.toString());
                        labelInfoItemEstoque.setText(Estoque.toString());
                        labelInfoItemPreco.setText("R$ " + Preco.toString());
                        
                        SpinnerInfoItemQuantidade.setValue(1);
                        SpinnerNumberModel numberQuantidade = (SpinnerNumberModel) SpinnerInfoItemQuantidade.getModel();
                        numberQuantidade.setMaximum(Integer.parseInt(Estoque.toString()));
                        numberQuantidade.setMinimum(1);
                    }
                }
            }
        });
		return containerTableItems;
	}
	
	public JPanel containerLabelsInfoItem() {
        containerInfoItem = buildMethod.createPanel(100, 26, new BorderLayout(), colorBackgroundWhite, 25,0,25,0);

        JPanel containerInfoItems = buildMethod.createPanel(30, 26, new FlowLayout(FlowLayout.LEFT), colorBackgroundWhite, 0,0,0,0);
        
        // Label for identify labels of info items
        labelTextCodigo = buildMethod.createLabel("Codigo", 4, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextProduto  = buildMethod.createLabel("Produto", 6, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextTipo  = buildMethod.createLabel("Tipo", 4, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextMarca  = buildMethod.createLabel("Marca", 4, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextEstoque  = buildMethod.createLabel("Estoque", 6, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextPreco  = buildMethod.createLabel("Preço", 10.5, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextQuantidade = buildMethod.createLabel("Quantidade", 6, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        
        // Label to show items info
        textFieldInfoItemCodigo = buildMethod.createTextField("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemProduto = buildMethod.createLabel("", 24.5, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,0,0,10);
        labelInfoItemTipo = buildMethod.createLabel("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemMarca = buildMethod.createLabel("", 20, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemEstoque = buildMethod.createLabel("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemPreco = buildMethod.createLabel("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        
        ((AbstractDocument) textFieldInfoItemCodigo.getDocument()).setDocumentFilter(new LimitDocumentFilter(100));
        textFieldInfoItemCodigo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                applyFilters();
            }
        });
        
        SpinnerInfoItemQuantidade = buildMethod.createSpinner(8, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall);
        SpinnerInfoItemQuantidade.setValue(1);
        SpinnerInfoItemQuantidade.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedRow = tabelaItems.getSelectedRow();
                if (selectedRow != -1) { 
                    String estoqueStr = labelInfoItemEstoque.getText();
                    
                    if (!estoqueStr.isEmpty()) {
                        int estoque = Integer.parseInt(estoqueStr); 
                        int novoValor = (int) SpinnerInfoItemQuantidade.getValue();
                        
                        if (novoValor > estoque) {
                            JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Quantidade não pode ser maior que o estoque!");
                            SpinnerInfoItemQuantidade.setValue(previosValue);
                        } else {
                            previosValue = novoValor;
                        }
                    } else {
                        JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Erro ao obter valor do estoque!");
                    }
                }
            }
        });
        
        containerInfoItems.add(labelTextCodigo);
        containerInfoItems.add(textFieldInfoItemCodigo);
        containerInfoItems.add(labelTextProduto);
        containerInfoItems.add(labelInfoItemProduto);
        containerInfoItems.add(labelTextTipo);
        containerInfoItems.add(labelInfoItemTipo);
        containerInfoItems.add(labelTextEstoque);
        containerInfoItems.add(labelInfoItemEstoque);
        containerInfoItems.add(labelTextQuantidade);
        containerInfoItems.add(SpinnerInfoItemQuantidade);
        containerInfoItems.add(labelTextMarca);
        containerInfoItems.add(labelInfoItemMarca);
        containerInfoItems.add(labelTextPreco);
        containerInfoItems.add(labelInfoItemPreco);
        
        containerRequestButtons = buildMethod.createPanel(18, 26, new GridLayout(2,1), colorBackgroundWhite, 0,0,0,0);
        
        buttonAddingItemForTableBuy = buildMethod.createButton("Adicionar Produto", 10, 6, SwingConstants.CENTER, colorTextWhite, colorButtonGreen);
        buttonAddingItemForTableBuy.setFont(FontRobotoPlainSmall);
        buttonAddingItemForTableBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addItemToCart();
            	clearItemInfoLabels();
            }
        });
        
        buttonRemoveItemForTableBuy = buildMethod.createButton("Remover Produto", 10, 6, SwingConstants.CENTER, colorTextWhite, colorButtonRed);
        buttonRemoveItemForTableBuy.setFont(FontRobotoPlainSmall);
        buttonRemoveItemForTableBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItemFromCart();
				clearItemInfoLabels();
			}
		});
        
        JPanel containerButtonAdding = buildMethod.createPanel(18, 13, new FlowLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        containerButtonAdding.add(buttonAddingItemForTableBuy);
        JPanel containerButtonRemove = buildMethod.createPanel(18, 13, new FlowLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        containerButtonRemove.add(buttonRemoveItemForTableBuy);
        
        containerRequestButtons.add(containerButtonAdding);
        containerRequestButtons.add(containerButtonRemove);
        
        containerInfoItem.add(containerInfoItems, BorderLayout.CENTER);
        containerInfoItem.add(containerRequestButtons, BorderLayout.EAST);
        return containerInfoItem;
	}
	
    public JPanel containerTableBuyAndRequest() {
        JPanel containerTableBuyAndRequest = buildMethod.createPanel(35, 100, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
        containerTableBuyAndRequest.add(containerTableBuy(), BorderLayout.NORTH);
        containerTableBuyAndRequest.add(containerBuyRequest(), BorderLayout.SOUTH);
        return containerTableBuyAndRequest;
    }
    
    @SuppressWarnings("serial")
	public JPanel containerTableBuy() {
        containerTableBuy = buildMethod.createPanel(32, 63.5, new BorderLayout(), colorBackgroundWhite, 11,0,0,0);
        containerTableBuy.setBackground(colorBlackBackground);
        
        String[] columnBuy = {"Codigo", "Produto", "Tipo", "Marca", "Estoque", "Preço", "Quantidade", "Preço total"};
        dadosBuy = new DefaultTableModel(columnBuy, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaBuy = new JTable(dadosBuy);
        tabelaBuy.setPreferredScrollableViewportSize(buildMethod.createResponsive(32, 60));
        tabelaBuy.getTableHeader().setReorderingAllowed(false);
        
        for (int i = 0; i < tabelaBuy.getColumnCount(); i++) {
            TableColumn column = tabelaBuy.getColumnModel().getColumn(i);
            column.setResizable(false);
        }
        
        tabelaBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModelBuy();
        
        buttonPreviusBuy = buildMethod.createButton("Historico de Compras", 20, 3, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite);
        buttonPreviusBuy.setFont(FontRobotoPlainSmall);
        
        buttonPreviusBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mHistoricTableBuy buy = new mHistoricTableBuy();
				buy.setVisible(true);
			}
		});
        
        containerTableBuy.add(new JScrollPane(tabelaBuy));    
        containerTableBuy.add(buttonPreviusBuy, BorderLayout.SOUTH);
        return containerTableBuy;
    }
    
    public void selectionModelBuy() {
        ListSelectionModel selectionModelBuy = tabelaBuy.getSelectionModel();
        selectionModelBuy.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabelaBuy.getSelectedRow();
                    if (selectedRow != -1) {
                        Object Codigo = tabelaBuy.getValueAt(selectedRow, 0);
                        Object Produto = tabelaBuy.getValueAt(selectedRow, 1);
                        Object Tipo = tabelaBuy.getValueAt(selectedRow, 2);
                        Object Marca = tabelaBuy.getValueAt(selectedRow, 3);
                        Object Estoque = tabelaBuy.getValueAt(selectedRow, 4);
                        Object Preco = tabelaBuy.getValueAt(selectedRow, 5);
                        Object Quantidade = tabelaBuy.getValueAt(selectedRow, 6);
                        
                        textFieldInfoItemCodigo.setText(Codigo.toString());
                        labelInfoItemProduto.setText(Produto.toString());
                        labelInfoItemTipo.setText(Tipo.toString());
                        labelInfoItemMarca.setText(Marca.toString());
                        labelInfoItemEstoque.setText(Estoque.toString());
                        labelInfoItemPreco.setText(Preco.toString());
                        SpinnerInfoItemQuantidade.setValue(Integer.parseInt(Quantidade.toString()));
                        
                        SpinnerNumberModel numberQuantidade = (SpinnerNumberModel) SpinnerInfoItemQuantidade.getModel();
                        numberQuantidade.setMaximum(Integer.parseInt(Estoque.toString()));
                        numberQuantidade.setMinimum(1);
                    }
                }
            }
        });
    }
    
 
    public JPanel containerBuyRequest() {
        containerRequestButtons = buildMethod.createPanel(32, 25.5, new GridLayout(2,2), colorBackgroundWhite, 21,0,25,0);

        labelTotalBuyText = buildMethod.createLabel("Total", 16, 12.75, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0,0,0,0);
        labelTotalBuyPrice = buildMethod.createLabel("R$ 0.0", 16, 12.75, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0,0,0,0);
        buttonConfirm = buildMethod.createButton("Comprar", 8, 5, SwingConstants.CENTER, colorTextWhite, colorButtonGreen);
        buttonCancel = buildMethod.createButton("Limpar Carrinho", 8, 5, SwingConstants.CENTER, colorTextWhite, colorButtonRed);
        buttonConfirm.setFont(FontRobotoPlainMedium);
        buttonCancel.setFont(FontRobotoPlainMedium);

        buttonConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmBuy();
            } 
        });
        
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(mPrincipal.getInstance().getFrame(), "Você deseja limpar os items do carrinho?", "Confirmar limpeza", JOptionPane.YES_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    removeAllItemsFromCart();
                }
            }
        });

        containerRequestButtons.add(labelTotalBuyText);
        containerRequestButtons.add(labelTotalBuyPrice);
        containerRequestButtons.add(buttonCancel);
        containerRequestButtons.add(buttonConfirm);
        return containerRequestButtons;
    }
    

    private void removeAllItemsFromCart() {
        int rowCount = tabelaBuy.getRowCount();
        if (rowCount > 0) {
            itemsDAO itemsDAO = new itemsDAO();

            for (int i = 0; i < rowCount; i++) {
                int codigo = (int) dadosBuy.getValueAt(i, 0);
                int quantidade = (int) dadosBuy.getValueAt(i, 6);

                int estoqueAtual = itemsDAO.getEstoque(codigo);
                int novoEstoque = estoqueAtual + quantidade;
                itemsDAO.updateEstoque(codigo, novoEstoque);

                for (int j = 0; j < tabelaItems.getRowCount(); j++) {
                    if ((int) tabelaItems.getValueAt(j, 0) == codigo) {
                        tabelaItems.setValueAt(novoEstoque, j, 4); 
                        break;
                    }
                }
            }
            dadosBuy.setRowCount(0);

            refreshTotalPrice();
        } else {
            JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Não há produtos no carrinho para remover!");
        }
    }

    private void resetReturnItemsTimer() {
        returnItemsTimer.restart(); // Reiniciar o timer para mais 10 minutos
    }
    
    public void returnItemsToStock() {
    	if (dadosBuy.getRowCount() > 0) {
    		itemsDAO dao = new itemsDAO();

            for (int i = 0; i < dadosBuy.getRowCount(); i++) {
                int codigo = (int) dadosBuy.getValueAt(i, 0);
                int quantidade = (int) dadosBuy.getValueAt(i, 6);

                int estoqueAtual = dao.getEstoque(codigo);
                int novoEstoque = estoqueAtual + quantidade;
                dao.updateEstoque(codigo, novoEstoque);

                for (int j = 0; j < tabelaItems.getRowCount(); j++) {
                    if ((int) tabelaItems.getValueAt(j, 0) == codigo) {
                        tabelaItems.setValueAt(novoEstoque, j, 4);
                        break;
                    }
                }
            }
            dadosBuy.setRowCount(0);
            refreshTotalPrice();
        }
    }
    
    
    private void addItemToCart() {
        int selectedRow = tabelaItems.getSelectedRow();
        if (selectedRow != -1) {
            Object codigo = tabelaItems.getValueAt(selectedRow, 0);
            Object produto = tabelaItems.getValueAt(selectedRow, 1);
            Object tipo = tabelaItems.getValueAt(selectedRow, 2);
            Object marca = tabelaItems.getValueAt(selectedRow, 3);
            Object estoque = tabelaItems.getValueAt(selectedRow, 4);
            Object preco = tabelaItems.getValueAt(selectedRow, 5);

            int quantidade = (int) SpinnerInfoItemQuantidade.getValue();

            double precoTotal = quantidade * (double) preco;

            dadosBuy.addRow(new Object[]{codigo, produto, tipo, marca, estoque, preco, quantidade, precoTotal});

            int novoEstoque = (int) estoque - quantidade;
            itemsDAO itemsDAO = new itemsDAO();
			itemsDAO.updateEstoque((int) codigo, novoEstoque);

            tabelaItems.getModel().setValueAt(novoEstoque, selectedRow, 4);

            refreshTotalPrice();
            resetReturnItemsTimer();
        } else {
            JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Produto não selecionado!");
        }
    }
   
    private void removeItemFromCart() {
        int selectedRowBuy = tabelaBuy.getSelectedRow();
        if (selectedRowBuy != -1) {
            int codigo = (int) dadosBuy.getValueAt(selectedRowBuy, 0);
            int estoqueAtual = (int) tabelaItems.getValueAt(selectedRowBuy, 4);
            int quantidade = (int) dadosBuy.getValueAt(selectedRowBuy, 6); 
            
            dadosBuy.removeRow(selectedRowBuy);

            itemsDAO itemsDAO = new itemsDAO();
            int novoEstoque = estoqueAtual + quantidade;
            itemsDAO.updateEstoque(codigo, novoEstoque);

            for (int i = 0; i < tabelaItems.getRowCount(); i++) {
                if ((int) tabelaItems.getValueAt(i, 0) == codigo) {
                    tabelaItems.setValueAt(novoEstoque, i, 4);
                    break;
                }
            }

            refreshTotalPrice();
        } else {
            JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Nenhum produto selecionado para remover!");
        }
    }

    private void clearItemInfoLabels() {
    	SpinnerInfoItemQuantidade.setValue(1);
    	textFieldInfoItemCodigo.setText("");
        labelInfoItemProduto.setText("");
        labelInfoItemTipo.setText("");
        labelInfoItemMarca.setText("");
        labelInfoItemEstoque.setText("");
        labelInfoItemPreco.setText("");
    }
    
    private void clearItems() {
    	SpinnerInfoItemQuantidade.setValue(1);
        labelInfoItemProduto.setText("");
        labelInfoItemTipo.setText("");
        labelInfoItemMarca.setText("");
        labelInfoItemEstoque.setText("");
        labelInfoItemPreco.setText("");
        tabelaItems.clearSelection();
    }

    private void confirmBuy() {
        int response = JOptionPane.showConfirmDialog(frame, "Você deseja confirmar a compra?", "Confirmar compra", JOptionPane.YES_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            purchasesHistoricDAO hDAO = new purchasesHistoricDAO();
            double sumTotalPriceOfItems = calcTotalCust();
            Map<Integer, Map<String, Object>> tableData = purchasesHistoricDAO.getShoppingCart(tabelaBuy);
            try {
                tabelaBuy.clearSelection();
                hDAO.saveHistotic(tableData, sumTotalPriceOfItems);

                dadosBuy.setRowCount(0);
                refreshTotalPrice();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao salvar historico de compras");
            }
        }
    }
    
    public void applyFilters() {
    	itemsDAO dao = new itemsDAO();
    	String codigoText = textFieldInfoItemCodigo.getText().trim();
    	
    	if (!codigoText.isEmpty()) {
    	    try {
    	        int codigo = Integer.parseInt(codigoText);
    	        items item = dao.getItemById(codigo);
    	        
    	        if (item != null) {
    	    	    for (int row = 0; row < tabelaItems.getRowCount(); row++) {
    	    	        int codigoNaTabela = (int) tabelaItems.getValueAt(row, 0); 
    	    	        
    	    	        if (codigo == codigoNaTabela) {
    	    	            tabelaItems.setRowSelectionInterval(row, row);
    	    	            tabelaItems.scrollRectToVisible(tabelaItems.getCellRect(row, 0, true)); 
    	    	            return; 
    	    	        }
    	    	    }
    	        } else {
    	        	clearItems();
    	        }
    	    } catch (NumberFormatException | SQLException ex) {
    	        handleException(ex);
    	    }
    	} else {
    	    clearItemInfoLabels();
    	}
    	
    	
    	
    }
    
    private void handleException(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Ocorreu um erro ao processar a requisição.");
        clearItemInfoLabels();
    }
    
}
