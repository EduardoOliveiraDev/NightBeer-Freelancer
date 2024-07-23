package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;

public class BuildPaymentMethod {
    private BuildMethods buildMethods = new BuildMethods();
    private Color colorTextWhite = buildMethods.colorTextWhite;
    private Color colorTextBlack = buildMethods.colorTextBlack;
    private Color colorBlackBackground = buildMethods.colorBackgroundBlack;
    private Color colorBackgroundWhite = buildMethods.colorBackgroundWhite;

    private Color colorWhiteClear = buildMethods.colorWhiteClear;

    private Color colorButtonGreen = buildMethods.colorButtonGreen;
    private Color colorButtonRed = buildMethods.colorButtonRed;

    private Font FontRobotoPlainSmall = buildMethods.FontRobotoPlain16;
    private Font FontRobotoPlainMedium = buildMethods.FontRobotoPlain22;

    private JPanel contentPane;

    private JPanel containerNavbar;
    private JLabel labelTitleNavBar;
    private JButton buttonClose;

    private JPanel containerBody;

    private JPanel containerBodyMethodPayment;
    private JLabel labelTitleMethodPayment;
    private JCheckBox checkBoxDinheiro;
    private JTextField textFieldValor;
    private JCheckBox checkBoxPix;
    private JCheckBox checkBoxCartaoCred;
    private JCheckBox checkBoxCartaoDeb;

    private JPanel containerBodyTotalPayment;
    private JPanel containerListItems;
    private JTable tabelaItemsBuyCart;
    private DefaultTableModel dadosItemsBuyCart;
    private JLabel labelTitleTotalPayment;
    private JLabel labelTitleTotalPaymentRemaining;

    private JPanel containerFooter;
    private JButton buttonConfirm;

    private double totalCust;

    public void receberTabela(DefaultTableModel modelo) {
        this.dadosItemsBuyCart = modelo;
    }

    public void receberPriceTotal(double total) {
    	this.totalCust = total;
    }
    
    public JPanel containerMain(JFrame frame) {
        contentPane = buildMethods.createPanel(40, 40, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);

        contentPane.add(headerContainer(frame), BorderLayout.NORTH);
        contentPane.add(bodyContainer(), BorderLayout.CENTER);
        contentPane.add(footerContainer(), BorderLayout.SOUTH);
        return contentPane;
    }

    public JPanel headerContainer(JFrame frame) {
        containerNavbar = buildMethods.createPanel(40, 3, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);

        buttonClose = buildMethods.createButton("X", 2, 3, SwingConstants.CENTER, colorWhiteClear, colorButtonRed);
        buttonClose.addActionListener(e -> frame.dispose());

        labelTitleNavBar = buildMethods.createLabel("Tela de pagamento", 20, 3, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 10);

        containerNavbar.add(labelTitleNavBar, BorderLayout.WEST);
        containerNavbar.add(buttonClose, BorderLayout.EAST);
        return containerNavbar;
    }

    public JPanel bodyContainer() {
        containerBody = buildMethods.createPanel(0, 0, new GridLayout(1, 2), colorBackgroundWhite, 10, 30, 10, 30);
        containerBody.add(containerBodyMethodPayment());
        containerBody.add(containerBodyTotalPayment());
        return containerBody;
    }

    public JPanel containerBodyMethodPayment() {
        containerBodyMethodPayment = buildMethods.createPanel(0, 0, new FlowLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        labelTitleMethodPayment = buildMethods.createLabel("Metodo de pagamento", 18, 6, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0, 0, 0, 0);

        checkBoxPix = buildMethods.createJCheckbox("Pix", 18, 4, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0, 0, 0, 10);
        checkBoxCartaoCred = buildMethods.createJCheckbox("Credito", 18, 4, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0, 0, 0, 10);
        checkBoxCartaoDeb = buildMethods.createJCheckbox("Debito", 18, 4, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0, 0, 0, 10);

        checkBoxDinheiro = buildMethods.createJCheckbox("Dinheiro", 11.8, 4, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0, 0, 0, 10);
        textFieldValor = buildMethods.createTextField("", 6, 4, SwingConstants.CENTER, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
        
        containerBodyMethodPayment.add(labelTitleMethodPayment, BorderLayout.NORTH);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(checkBoxPix);
        buttonGroup.add(checkBoxCartaoCred);
        buttonGroup.add(checkBoxCartaoDeb);
        buttonGroup.add(checkBoxDinheiro);

        containerBodyMethodPaymentFunction();

        containerBodyMethodPayment.add(checkBoxPix);
        containerBodyMethodPayment.add(checkBoxCartaoCred);
        containerBodyMethodPayment.add(checkBoxCartaoDeb);
        containerBodyMethodPayment.add(checkBoxDinheiro);
        containerBodyMethodPayment.add(textFieldValor);

        return containerBodyMethodPayment;
    }

    public void containerBodyMethodPaymentFunction() {
        checkBoxPix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBoxPix.isSelected()) {
                    blockTextFieldValor();
                }
            }
        });

        checkBoxCartaoCred.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBoxCartaoCred.isSelected()) {
                    blockTextFieldValor();
                }
            }
        });

        checkBoxCartaoDeb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBoxCartaoDeb.isSelected()) {
                    blockTextFieldValor();
                }
            }
        });

        checkBoxDinheiro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBoxDinheiro.isSelected()) {
                    textFieldValor.setOpaque(true);
                    textFieldValor.setEditable(true);
                    textFieldValor.setText("Valor");
                    
                    labelTitleTotalPaymentRemaining.setText("Troco: R$");
                    labelTitleTotalPaymentRemaining.setOpaque(true);
                }
            }
        });

        textFieldValor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (textFieldValor.getText().equalsIgnoreCase("Valor")) {
                    textFieldValor.setText("");
                }
            }
        });
        
        textFieldValor.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                wrongChangeCash();
            }
        });

    }

    public void blockTextFieldValor() {
        textFieldValor.setText("");
        textFieldValor.setOpaque(false);
        textFieldValor.setEditable(false);
        
        labelTitleTotalPaymentRemaining.setText("");
        labelTitleTotalPaymentRemaining.setOpaque(false);
    }

    public JPanel containerBodyTotalPayment() {
        containerBodyTotalPayment = buildMethods.createPanel(0, 0, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        containerListItems = buildMethods.createPanel(18, 20, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);

        // Dados da tabela original
        Vector<Vector> dataVector = dadosItemsBuyCart.getDataVector();
        int rowCount = dataVector.size();
        int colCount = dataVector.isEmpty() ? 0 : dataVector.get(0).size();
        
        Object[][] data = new Object[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            Vector<Object> rowVector = dataVector.get(i);
            for (int j = 0; j < colCount; j++) {
                data[i][j] = rowVector.get(j);
            }
        }

        String[] originalColumnNames = {"ID", "Produto", "Coluna 1", "Coluna 2", "Coluna 3", "Valor", "Und", "Total"};
        
        // Crie um modelo de tabela personalizado
        DefaultTableModel customModel = new DefaultTableModel(data, originalColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Define se as células são editáveis
            }
        };
        
        tabelaItemsBuyCart = new JTable(customModel);

        // Remova colunas que não deseja exibir
        removeColumnsFromTable(tabelaItemsBuyCart, 2, 3, 4);

        // Defina a largura máxima das colunas
        setColumnMaxWidths(tabelaItemsBuyCart, 40, 200, 60, 40, 60);

        // Defina o alinhamento das células
        setColumnAlignments(tabelaItemsBuyCart, SwingConstants.CENTER, SwingConstants.LEFT, SwingConstants.RIGHT, SwingConstants.RIGHT, SwingConstants.RIGHT);

        JScrollPane scrollPane = new JScrollPane(tabelaItemsBuyCart);
        containerListItems.add(scrollPane, BorderLayout.CENTER);
        labelTitleTotalPayment = buildMethods.createLabel("Total a pagar: R$ 0.00", 18, 3, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0, 0, 0, 10);
        labelTitleTotalPayment.setText("Total a pagar: R$ " + totalCust);
        
        labelTitleTotalPaymentRemaining = buildMethods.createLabel("Troco: R$ 0.00", 18, 4, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainMedium, 0, 0, 0, 10);

        blockTextFieldValor();
        containerBodyTotalPayment.add(containerListItems, BorderLayout.NORTH);
        containerBodyTotalPayment.add(labelTitleTotalPayment, BorderLayout.CENTER);
        containerBodyTotalPayment.add(labelTitleTotalPaymentRemaining, BorderLayout.SOUTH);

        return containerBodyTotalPayment;
    }

    public void wrongChangeCash() {
    	String textValue = textFieldValor.getText();
    	int length = textValue.length();
    	if (!textValue.equalsIgnoreCase("")) {
        	try {
        		textValue = textValue.replace(',', '.');
        	    double inputValor = Double.parseDouble(textValue);
            	double ChangeCash = inputValor - totalCust;
            	labelTitleTotalPaymentRemaining.setText("Troco: R$ " + ChangeCash);
        	} catch (NumberFormatException e) {
        		JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número válido.");
                if (length > 0) {
                    textFieldValor.setText(textValue.substring(0, length - 1));
                }
        	}
		}
    }
    
    // Método para remover colunas da tabela
    private void removeColumnsFromTable(JTable table, int... columnIndices) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = columnIndices.length - 1; i >= 0; i--) {
            int index = columnIndices[i];
            TableColumn column = columnModel.getColumn(index);
            columnModel.removeColumn(column);
        }
    }

    // Método para definir a largura máxima das colunas
    private void setColumnMaxWidths(JTable table, int... maxWidths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < maxWidths.length && i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setMaxWidth(maxWidths[i]);
        }
    }

    // Método para definir o alinhamento das células
    private void setColumnAlignments(JTable table, int... alignments) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < alignments.length && i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(alignments[i]);
            column.setCellRenderer(renderer);
        }
    }
    
    public JPanel footerContainer() {
        containerFooter = buildMethods.createPanel(40, 6, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);
        buttonConfirm = buildMethods.createButton("Confirmar", 8, 4, SwingConstants.CENTER, colorTextWhite, colorButtonGreen);
        buttonConfirm.setFont(FontRobotoPlainMedium);
        buttonConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	confirmBuy();
            }
        });

        containerFooter.add(buttonConfirm, BorderLayout.EAST);
        return containerFooter;
    }
    
    public void confirmBuy() {
        JCheckBox selectedCheckBox = null;
        for (JCheckBox checkBox : new JCheckBox[]{checkBoxPix, checkBoxCartaoCred, checkBoxCartaoDeb, checkBoxDinheiro}) {
            if (checkBox.isSelected()) {
                selectedCheckBox = checkBox;
                break;
            }
        }

        if (selectedCheckBox != null && selectedCheckBox.getText().contentEquals("Dinheiro")) {
            String textValue = textFieldValor.getText();
            textValue = textValue.replace(',', '.');

            // Corrige o texto para garantir no máximo duas casas decimais
            textValue = correctDecimal(textValue);
            textFieldValor.setText(textValue);

            try {
                double inputValor = Double.parseDouble(textValue);
                double changeCash = inputValor - totalCust;

                if (inputValor >= totalCust) {
                    System.out.println("Passou");
                    labelTitleTotalPaymentRemaining.setText("Troco: R$ " + changeCash);
                } else {
                	JOptionPane.showMessageDialog(null, "O valor pago insuficiente.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número válido.");
            }
        } else {
            System.out.println(selectedCheckBox.getText());
        }
    }

    private String correctDecimal(String text) {
        if (text.contains(".")) {
            int index = text.indexOf(".");
            if (text.length() - index - 1 > 2) {
                text = text.substring(0, index + 3);
            }
        }
        return text;
    }
    
    
}