package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.nightbeer.dao.purchasesHistoricDAO;
import com.nightbeer.model.editingTable;
import com.nightbeer.model.historic;

public class BuildHistoricBuy {
    private BuildMethods buildMethods = new BuildMethods();
    private editingTable editingTable = new editingTable();
    
    private Color colorBackgroundWhite = buildMethods.colorBackgroundWhite;
    private Color colorBlackBackground = buildMethods.colorBackgroundBlack;
    
    private Color colorTextBlack = buildMethods.colorTextBlack;
    private Color colorWhiteClear = buildMethods.colorWhiteClear;
    private Color colorRed = buildMethods.colorButtonRed;
    
    private Color colorButtonClose = buildMethods.colorButtonClose;
    private Color colorButton = buildMethods.colorButton;
    
    private Font FontRobotoPlainSmall = buildMethods.FontRobotoPlain16;
    private Font FontRobotoPlainMedium = buildMethods.FontRobotoPlain22;

    
    private JPanel containerMain;
    private JPanel containerNavBar;
    private JLabel labelTitleNavBar;
    private JButton buttonExit;

    private JPanel containerSearch;
    private JTextField textfieldSearch;
    private JButton buttonSend;
    private JButton buttonBack;

    private JPanel containerTableHistoric;
    private JTable historicBuyTable;
    private DefaultTableModel historicDados;

    private JPanel containerTableShoppingCart;
    private JTable TableShoppinCart;
    private DefaultTableModel dadosTableShoppinCart;

    public ImageIcon iconBack = buildMethods.createImage("../images/iconGoBack.png", 25, 25);
    public ImageIcon iconHistoric  = buildMethods.createImage("../images/iconHistoric.png", 25, 25);
    
    public JPanel containerMain(JFrame frame) {
        containerMain = buildMethods.createPanel(50, 70, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);

        containerMain.add(navbar(frame), BorderLayout.NORTH);
        containerMain.add(containerSearch(), BorderLayout.CENTER);
        containerMain.add(containerTableHistoric(), BorderLayout.SOUTH);

        return containerMain;
    }

    public JPanel navbar(JFrame frame) {
        containerNavBar = buildMethods.createPanel(50, 3.5, new BorderLayout(), colorBlackBackground, 0, 0, 0, 0);
        labelTitleNavBar = buildMethods.createLabel("Histórico de compras", 10, 3, SwingConstants.LEFT, colorWhiteClear, colorBlackBackground, FontRobotoPlainSmall, 0, 0, 0, 10);
        buttonExit = buildMethods.createButton("X", 2.5, 3.5, SwingConstants.CENTER, colorWhiteClear, colorBlackBackground);

        buttonExit.addActionListener(e -> frame.dispose());
    	buttonExit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonExit.setBackground(colorRed);
            }

            public void mouseExited(MouseEvent e) {
                buttonExit.setBackground(colorBlackBackground);
            }
        });

        containerNavBar.add(buttonExit, BorderLayout.EAST);
        containerNavBar.add(labelTitleNavBar, BorderLayout.WEST);
        return containerNavBar;
    }

    public JPanel containerSearch() {
        containerSearch = buildMethods.createPanel(60, 10, new BorderLayout(), colorBackgroundWhite, 30, 30, 15, 30);

        textfieldSearch = buildMethods.createTextField("", 40, 0, SwingConstants.LEFT, Color.GRAY , colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);

        buttonBack = buildMethods.createButton("", 3, 3, SwingConstants.CENTER, colorTextBlack, colorButton);
        buttonBack.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 15, colorBackgroundWhite));
        buttonBack.setIcon(iconBack);
        buttonBack.setEnabled(false);
        buttonBack.setOpaque(false);
        
        buttonSend = buildMethods.createButton("", 4, 3, SwingConstants.CENTER, colorTextBlack, colorButton);
        buttonSend.setBorder(BorderFactory.createMatteBorder(0, 15, 0, 0, colorBackgroundWhite));
        buttonSend.setIcon(iconHistoric);

        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = historicBuyTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Pega o ID da coluna "ID" (index 0) da linha selecionada
                    Object id = historicBuyTable.getValueAt(selectedRow, 0); // Assumindo que a coluna ID é a primeira (index 0)

                    String jsonString = (String) historicBuyTable.getValueAt(selectedRow, 2); // Assumindo que a coluna HashMap é a terceira (index 2)
                    Map<Integer, Map<String, Object>> hashMap = purchasesHistoricDAO.convertStringToHashMap(jsonString);
                    containerMain.remove(containerTableHistoric);
                    containerMain.add(containerTableShoppingCart(), BorderLayout.SOUTH);
                    updateHashMapTable(hashMap);
                    containerMain.revalidate();
                    containerMain.repaint();

//                  change textfield in id info cart
                    textfieldSearch.setText("Carrinho: #" + id);
                    textfieldSearch.setEditable(false);
                    textfieldSearch.setBackground(colorBackgroundWhite);
                    textfieldSearch.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, colorBackgroundWhite));
                    textfieldSearch.setForeground(colorTextBlack);
                    textfieldSearch.setFont(FontRobotoPlainMedium);

                    buttonBack.setEnabled(true);
                    buttonBack.setOpaque(true);
                    buttonSend.setEnabled(false);
                    buttonSend.setOpaque(false);
                }
            }
        });
        
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    containerMain.remove(containerTableShoppingCart);
                    containerMain.add(containerTableHistoric(), BorderLayout.SOUTH);
                    try {
						listHistoric();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                    containerMain.revalidate();
                    containerMain.repaint();
                    
                    textfieldSearch.setText("");
                    buttonSend.setEnabled(true);
                    buttonSend.setOpaque(true);
                    buttonBack.setEnabled(false);
                    buttonBack.setOpaque(false);
                    
//                  change id info cart in textfield
                    textfieldSearch.setEditable(true);
                    textfieldSearch.setBackground(colorWhiteClear);
                    textfieldSearch.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 10, colorWhiteClear));
                    textfieldSearch.setForeground(colorTextBlack);
                    textfieldSearch.setFont(FontRobotoPlainSmall);
            }
        });

        textfieldSearch.setText("Pesquisar...");
        textfieldSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textfieldSearch.getText().equals("Pesquisar...")) {
                	textfieldSearch.setText("");
                	textfieldSearch.setForeground(Color.BLACK); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textfieldSearch.getText().isEmpty()) {
                	textfieldSearch.setText("Pesquisar...");
                	textfieldSearch.setForeground(Color.GRAY); // Define a cor cinza para o texto de placeholder
                }
            }
        });
        
        textfieldSearch.addKeyListener(new KeyAdapter() {
        	public void keyReleased(KeyEvent evt) {
        		applyFilters();
            }
		});
        
        containerSearch.add(buttonBack, BorderLayout.WEST);
        containerSearch.add(textfieldSearch, BorderLayout.CENTER);
        containerSearch.add(buttonSend, BorderLayout.EAST);

        return containerSearch;
    }

    @SuppressWarnings("serial")
	public JPanel containerTableHistoric() {
        containerTableHistoric = buildMethods.createPanel(60, 59, new BorderLayout(), colorBackgroundWhite, 15, 30, 30, 30);


        
        String[] columnBuy = {"ID", "Data", "Carrinho", "Total", "Metodo de Pagamento"};
        historicDados = new DefaultTableModel(columnBuy, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historicBuyTable = new JTable(historicDados);
        historicBuyTable.setPreferredScrollableViewportSize(buildMethods.createResponsive(32, 60));
        historicBuyTable.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < historicBuyTable.getColumnCount(); i++) {
            TableColumn column = historicBuyTable.getColumnModel().getColumn(i);
            column.setResizable(false);
        }

        editingTable.setColumnMaxWidths(historicBuyTable, 60, 140, 600, 80, 160);
        editingTable.setColumnAlignments(historicBuyTable, SwingConstants.CENTER, SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.RIGHT, SwingConstants.RIGHT);
        
        historicBuyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        
        containerTableHistoric.add(new JScrollPane(historicBuyTable), BorderLayout.CENTER);

        try {
            listHistoric();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return containerTableHistoric;
    }

    @SuppressWarnings("serial")
	public JPanel containerTableShoppingCart() {
        if (containerTableShoppingCart == null) {
            containerTableShoppingCart = buildMethods.createPanel(60, 59, new BorderLayout(), colorBackgroundWhite, 30, 30, 30, 30);

            String[] columnShoppingCart = {"ID", "Produto", "Valor", "Und", "Total"};
            dadosTableShoppinCart = new DefaultTableModel(columnShoppingCart, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            TableShoppinCart = new JTable(dadosTableShoppinCart);
            
            editingTable.setColumnMaxWidths(TableShoppinCart, 60, 800, 140, 80, 140);
            editingTable.setColumnAlignments(TableShoppinCart, SwingConstants.CENTER, SwingConstants.LEFT, SwingConstants.RIGHT, SwingConstants.CENTER, SwingConstants.RIGHT);
            
            TableShoppinCart.setPreferredScrollableViewportSize(buildMethods.createResponsive(32, 60));
            TableShoppinCart.getTableHeader().setReorderingAllowed(false);

            for (int i = 0; i < TableShoppinCart.getColumnCount(); i++) {
                TableColumn column = TableShoppinCart.getColumnModel().getColumn(i);
                column.setResizable(false);
            }

            containerTableShoppingCart.add(new JScrollPane(TableShoppinCart), BorderLayout.CENTER);
        }
        return containerTableShoppingCart;
    }
 
    public void listHistoric() throws SQLException {
        purchasesHistoricDAO hDAO = new purchasesHistoricDAO();
        List<historic> listHistoric = hDAO.listHistoric();

        historicDados.setRowCount(0);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        for (historic i : listHistoric) {
            String formattedDate = i.getDate().format(dateFormat);
            historicDados.addRow(new Object[]{
                i.getId(),
                formattedDate,
                i.getHashmapJSON(),
                i.getTotal(),
                i.getMetodoPagamento()
            });
        }
    }

    private void updateHashMapTable(Map<Integer, Map<String, Object>> hashMap) {
        DefaultTableModel hashMapTableModel = (DefaultTableModel) TableShoppinCart.getModel();
        hashMapTableModel.setRowCount(0);

        DecimalFormat decimalFormat = new DecimalFormat("#");

        for (Map.Entry<Integer, Map<String, Object>> entry : hashMap.entrySet()) {
            Map<String, Object> details = entry.getValue();
            Object[] rowData = new Object[dadosTableShoppinCart.getColumnCount()];
            
            rowData[0] = decimalFormat.format(details.get("ID"));
            rowData[1] = details.get("Produto");
            rowData[2] = "R$ " + details.get("Valor");
            rowData[3] = details.get("Und");
            rowData[4] = "R$ " + details.get("Total");
            
            hashMapTableModel.addRow(rowData);
        }
    }

    private void applyFilters() {
        if (historicBuyTable != null) {
            DefaultTableModel modelo = (DefaultTableModel) historicBuyTable.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelo);
            historicBuyTable.setRowSorter(trs);

            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            String textFilter = textfieldSearch.getText().trim();
            if (!textFilter.isEmpty()) {
                filters.add(RowFilter.regexFilter("(?i)" + textFilter, 0));
            }

            trs.setRowFilter(RowFilter.andFilter(filters));
        }
        
        if (TableShoppinCart != null) {
            DefaultTableModel modelo = (DefaultTableModel) TableShoppinCart.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(modelo);
            TableShoppinCart.setRowSorter(trs);

            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            String textFilter = textfieldSearch.getText().trim();
            if (!textFilter.isEmpty()) {
                filters.add(RowFilter.regexFilter("(?i)" + textFilter, 0, 1, 2, 3));
            }

            trs.setRowFilter(RowFilter.andFilter(filters));
        }
    }
}
