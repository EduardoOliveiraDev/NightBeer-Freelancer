package com.nightbeer.build;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.text.FlowView.FlowStrategy;

import com.nightbeer.dao.itemsDAO;
import com.nightbeer.model.items;
import com.nightbeer.view.mLogIn;
import com.nightbeer.view.mPreviusTableBuy;
import com.nightbeer.view.mPrincipal;

public class BuildMPrincipal {
    private BuildMethods buildMethod = new BuildMethods();
    private BuildPreviusBuy buildPreviusBuy = new BuildPreviusBuy();
    private Color colorTextWhite = buildMethod.colorTextWhite;
    private Color colorTextBlack = buildMethod.colorTextBlack;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;
    
    private Color colorWhiteClear = buildMethod.colorWhiteClear;
    
    private Color colorButton = buildMethod.colorButton;
    private Color colorButtonGreen = buildMethod.colorButtonGreen;
    private Color colorButtonRed = buildMethod.colorButtonRed;
    
    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
    private Font FontRobotoPlainMedium = buildMethod.FontRobotoPlain28;

    private JPanel containerTable;
    private JTable tabela;
    private DefaultTableModel dados;
    
    private JPanel containerInfoItem;
    private JLabel labelTextCodigo;
    private JLabel labelTextProduto;
    private JLabel labelTextTipo;
    private JLabel labelTextMarca;
    private JLabel labelTextEstoque;
    private JLabel labelTextPreco;
    private JLabel labelTextQuantidade;
    private JLabel labelInfoItemCodigo;
    private JLabel labelInfoItemProduto;
    private JLabel labelInfoItemTipo;
    private JLabel labelInfoItemMarca;
    private JLabel labelInfoItemEstoque;
    private JLabel labelInfoItemPreco;
    private JSpinner SpinnerInfoItemQuantidade;
    private int previosValue;
    
    private JPanel containerInfoItemEast;
    private JButton buttonAddingItemForTableBuy;
    private JButton buttonRemoveItemForTableBuy;
    
    private JPanel containerEastTable;
    private JTable tabelaBuy;
    private JButton buttonPreviusBuy;
    private DefaultTableModel dadosBuy;
    
    private JPanel containerEastRequest;
    private JLabel labelTotalBuyText;
    private JLabel labelTotalBuyPrice;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    
    private Timer returnItemsTimer;
    private List<Object[]> itemList = new ArrayList<>();
    private JFrame frame;
    
    private DefaultTableModel tabelaBuyModel; // Modelo da tabela tabelaBuy
    
    public BuildMPrincipal() {
        frame = new JFrame();
        dadosBuy = new DefaultTableModel(new Object[]{"Código", "Descrição", "Quantidade", "Preço"}, 0);

        returnItemsTimer = new Timer(600000, e -> { // 600.000 = 10 minutos
            returnItemsToStock();
            refreshTotalPrice(); 
            returnItemsTimer.stop(); 
        });
        returnItemsTimer.setRepeats(false);

        buildPreviusBuy.containerMain(frame);
        buildPreviusBuy = new BuildPreviusBuy();
    }
    
    public void listar() {
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
    
	public JPanel containerCenter() {
        JPanel containerCenter = buildMethod.createPanel(65, 100, new BorderLayout(), colorBackgroundWhite, 0,0,0,25);
        
        containerTable();
        containerInfoItem();       
        
        containerCenter.add(new BuildSearchBar(tabela).containerSearch(), BorderLayout.NORTH);
        containerCenter.add(containerTable, BorderLayout.CENTER);
        containerCenter.add(containerInfoItem, BorderLayout.SOUTH);

        return containerCenter;
    }

	public JPanel containerTable() {
        containerTable = buildMethod.createPanel(100, 64, new BorderLayout(), colorBackgroundWhite, 0,0,0,0);

        String[] colunas = {"Código", "Produto", "Tipo", "Marca", "Estoque", "Preço"};
        dados = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(dados);
        tabela.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            TableColumn column = tabela.getColumnModel().getColumn(i);
            column.setResizable(false);
        }

        containerTable.add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        ListSelectionModel selectionModel = tabela.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tabela.getSelectedRow();
                    if (selectedRow != -1) {
                    	tabelaBuy.clearSelection();
                    	
                        Object Codigo = tabela.getValueAt(selectedRow, 0);
                        Object Produto = tabela.getValueAt(selectedRow, 1);
                        Object Tipo = tabela.getValueAt(selectedRow, 2);
                        Object Marca = tabela.getValueAt(selectedRow, 3);
                        Object Estoque = tabela.getValueAt(selectedRow, 4);
                        Object Preco = tabela.getValueAt(selectedRow, 5);
                        
                        labelInfoItemCodigo.setText(Codigo.toString());
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
        
        
		return containerTable;
	}
	
	public JPanel containerInfoItem() {
        containerInfoItem = buildMethod.createPanel(100, 26, new BorderLayout(), colorBackgroundWhite, 25,0,25,0);

        JPanel containerInfoItemCenter = buildMethod.createPanel(30, 26, new FlowLayout(FlowLayout.LEFT), colorBackgroundWhite, 0,0,0,0);
        
        // Label for identify labels of info items
        labelTextCodigo = buildMethod.createLabel("Codigo", 4, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextProduto  = buildMethod.createLabel("Produto", 6, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextTipo  = buildMethod.createLabel("Tipo", 4, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextMarca  = buildMethod.createLabel("Marca", 4, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextEstoque  = buildMethod.createLabel("Estoque", 6, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextPreco  = buildMethod.createLabel("Preço", 10.5, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        labelTextQuantidade = buildMethod.createLabel("Quantidade", 6, 5, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0,0,0,0);
        
        // Label to show items info
        labelInfoItemCodigo = buildMethod.createLabel("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemProduto = buildMethod.createLabel("", 24.5, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,0,0,10);
        labelInfoItemTipo = buildMethod.createLabel("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemMarca = buildMethod.createLabel("", 20, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemEstoque = buildMethod.createLabel("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        labelInfoItemPreco = buildMethod.createLabel("", 10, 4, SwingConstants.RIGHT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0,10,0,0);
        
        SpinnerInfoItemQuantidade = buildMethod.createSpinner(8, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall);
        SpinnerInfoItemQuantidade.setValue(1);
        
        SpinnerInfoItemQuantidade.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedRow = tabela.getSelectedRow();
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
        
        containerInfoItemCenter.add(labelTextCodigo);
        containerInfoItemCenter.add(labelInfoItemCodigo);
        containerInfoItemCenter.add(labelTextProduto);
        containerInfoItemCenter.add(labelInfoItemProduto);
        containerInfoItemCenter.add(labelTextTipo);
        containerInfoItemCenter.add(labelInfoItemTipo);
        containerInfoItemCenter.add(labelTextEstoque);
        containerInfoItemCenter.add(labelInfoItemEstoque);
        containerInfoItemCenter.add(labelTextQuantidade);
        containerInfoItemCenter.add(SpinnerInfoItemQuantidade);
        containerInfoItemCenter.add(labelTextMarca);
        containerInfoItemCenter.add(labelInfoItemMarca);
        containerInfoItemCenter.add(labelTextPreco);
        containerInfoItemCenter.add(labelInfoItemPreco);
        
        containerInfoItemEast = buildMethod.createPanel(18, 26, new GridLayout(2,1), colorBackgroundWhite, 0,0,0,0);
        
        buttonAddingItemForTableBuy = buildMethod.createButton("Adicionar Produto", 10, 6, SwingConstants.CENTER, colorTextWhite, colorButtonGreen);
        buttonAddingItemForTableBuy.setFont(FontRobotoPlainSmall);
        buttonAddingItemForTableBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addItemToCart();
            	clear();
            }
        });
        
        buttonRemoveItemForTableBuy = buildMethod.createButton("Remover Produto", 10, 6, SwingConstants.CENTER, colorTextWhite, colorButtonRed);
        buttonRemoveItemForTableBuy.setFont(FontRobotoPlainSmall);
        buttonRemoveItemForTableBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItemFromCart();
				clear();
			}
		});
        
        JPanel containerInfoItemEast1 = buildMethod.createPanel(18, 13, new FlowLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        containerInfoItemEast1.add(buttonAddingItemForTableBuy);
        
        JPanel containerInfoItemEast2 = buildMethod.createPanel(18, 13, new FlowLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        containerInfoItemEast2.add(buttonRemoveItemForTableBuy);
        
        containerInfoItemEast.add(containerInfoItemEast1);
        containerInfoItemEast.add(containerInfoItemEast2);
        
        containerInfoItem.add(containerInfoItemCenter, BorderLayout.CENTER);
        containerInfoItem.add(containerInfoItemEast, BorderLayout.EAST);
        return containerInfoItem;
	}
	
    public JPanel containerEast() {
        JPanel containerEast = buildMethod.createPanel(35, 100, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);

        containerTableBuy();
        containerBuyRequest();

        containerEast.add(containerEastTable, BorderLayout.NORTH);
        containerEast.add(containerEastRequest, BorderLayout.SOUTH);

        return containerEast;
    }
    
    public JPanel containerTableBuy() {
        containerEastTable = buildMethod.createPanel(32, 63.5, new BorderLayout(), colorBackgroundWhite, 11,0,0,0);
        containerEastTable.setBackground(colorBlackBackground);
        
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
                        
                        labelInfoItemCodigo.setText(Codigo.toString());
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
        
        buttonPreviusBuy = buildMethod.createButton("Reveja a compra anterior", 20, 3, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite);
        buttonPreviusBuy.setFont(FontRobotoPlainSmall);
        buttonPreviusBuy.setVisible(false);
        
        buttonPreviusBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mPreviusTableBuy buy = new mPreviusTableBuy();
				buy.setVisible(true);
			}
		});
        
        containerEastTable.add(new JScrollPane(tabelaBuy));    
        containerEastTable.add(buttonPreviusBuy, BorderLayout.SOUTH);
        return containerEastTable;
    }
 
    public JPanel containerBuyRequest() {
        containerEastRequest = buildMethod.createPanel(32, 25.5, new GridLayout(2,2), colorBackgroundWhite, 21,0,25,0);

        labelTotalBuyText = buildMethod.createLabel("Total", 16, 12.75, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0,0,0,0);
        labelTotalBuyPrice = buildMethod.createLabel("R$ 0.0", 16, 12.75, SwingConstants.RIGHT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0,0,0,0);
        buttonConfirm = buildMethod.createButton("Comprar", 8, 5, SwingConstants.CENTER, colorTextWhite, colorButtonGreen);
        buttonCancel = buildMethod.createButton("Limpar Carrinho", 8, 5, SwingConstants.CENTER, colorTextWhite, colorButtonRed);
        buttonConfirm.setFont(FontRobotoPlainMedium);
        buttonCancel.setFont(FontRobotoPlainMedium);

        buttonConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarCompra();
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

        containerEastRequest.add(labelTotalBuyText);
        containerEastRequest.add(labelTotalBuyPrice);
        containerEastRequest.add(buttonCancel);
        containerEastRequest.add(buttonConfirm);
        return containerEastRequest;
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

                for (int j = 0; j < tabela.getRowCount(); j++) {
                    if ((int) tabela.getValueAt(j, 0) == codigo) {
                        tabela.setValueAt(novoEstoque, j, 4); 
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

                for (int j = 0; j < tabela.getRowCount(); j++) {
                    if ((int) tabela.getValueAt(j, 0) == codigo) {
                        tabela.setValueAt(novoEstoque, j, 4);
                        break;
                    }
                }
            }
            dadosBuy.setRowCount(0);
            refreshTotalPrice();
        }
    }
    
    private void addItemToCart() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow != -1) {
            Object codigo = tabela.getValueAt(selectedRow, 0);
            Object produto = tabela.getValueAt(selectedRow, 1);
            Object tipo = tabela.getValueAt(selectedRow, 2);
            Object marca = tabela.getValueAt(selectedRow, 3);
            Object estoque = tabela.getValueAt(selectedRow, 4);
            Object preco = tabela.getValueAt(selectedRow, 5);

            int quantidade = (int) SpinnerInfoItemQuantidade.getValue();

            double precoTotal = quantidade * (double) preco;

            if (quantidade > (int) estoque) {
                JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Quantidade não pode ser maior que o estoque!");
                return;
            }

            dadosBuy.addRow(new Object[]{codigo, produto, tipo, marca, estoque, preco, quantidade, precoTotal});

            int novoEstoque = (int) estoque - quantidade;
            itemsDAO itemsDAO = new itemsDAO();
			itemsDAO.updateEstoque((int) codigo, novoEstoque);

            tabela.getModel().setValueAt(novoEstoque, selectedRow, 4);

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
            int estoqueAtual = (int) tabela.getValueAt(selectedRowBuy, 4);
            int quantidade = (int) dadosBuy.getValueAt(selectedRowBuy, 6); 
            
            dadosBuy.removeRow(selectedRowBuy);

            itemsDAO itemsDAO = new itemsDAO();
            int novoEstoque = estoqueAtual + quantidade;
            itemsDAO.updateEstoque(codigo, novoEstoque);

            for (int i = 0; i < tabela.getRowCount(); i++) {
                if ((int) tabela.getValueAt(i, 0) == codigo) {
                    tabela.setValueAt(novoEstoque, i, 4);
                    break;
                }
            }

            refreshTotalPrice();
        } else {
            JOptionPane.showMessageDialog(mPrincipal.getInstance().getFrame(), "Nenhum produto selecionado para remover!");
        }
    }

    private void clear() {
    	SpinnerInfoItemQuantidade.setValue(1);
    	labelInfoItemCodigo.setText("");
        labelInfoItemProduto.setText("");
        labelInfoItemTipo.setText("");
        labelInfoItemMarca.setText("");
        labelInfoItemEstoque.setText("");
        labelInfoItemPreco.setText("");
        
    }

    private void confirmarCompra() {
        int response = JOptionPane.showConfirmDialog(frame, "Você deseja confirmar a compra?", "Confirmar compra", JOptionPane.YES_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            buttonPreviusBuy.setVisible(true);
        }
    }
}
