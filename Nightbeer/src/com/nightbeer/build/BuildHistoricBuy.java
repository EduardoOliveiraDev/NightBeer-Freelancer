package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.nightbeer.dao.itemsDAO;
import com.nightbeer.model.items;

public class BuildPreviusBuy {
    private BuildMethods buildMethods = new BuildMethods();

    private Color colorBackgroundWhite = buildMethods.colorBackgroundWhite;
    private Color colorBlackBackground = buildMethods.colorBackgroundBlack;

    private Color colorButtonClose = buildMethods.colorButtonClose;

    private Font FontRobotoPlainSmall = buildMethods.FontRobotoPlain16;

    private JPanel containerMain;
    private JPanel containerNavBar;
    private JLabel labelTitleNavBaar;
    private JButton buttonExit;

    private JPanel containerTable;
    private JTable previusBuyTable;
    private DefaultTableModel previusDados;

    public JPanel containerMain(JFrame frame) {
        containerMain = buildMethods.createPanel(50, 70, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);

        containerMain.add(navbar(frame), BorderLayout.NORTH);
        containerMain.add(containerTable(), BorderLayout.CENTER);
        return containerMain;
    }

    public JPanel navbar(JFrame frame) {
        containerNavBar = buildMethods.createPanel(50, 3.5, new BorderLayout(), colorBlackBackground, 0, 0, 0, 0);
        labelTitleNavBaar = buildMethods.createLabel("Compra anterior", 10, 3, SwingConstants.LEFT, colorBackgroundWhite, colorBlackBackground, FontRobotoPlainSmall, 0, 0, 0, 10);
        buttonExit = buildMethods.createButton("X", 2.5, 3.5, SwingConstants.CENTER, colorBlackBackground, colorButtonClose);

        buttonExit.addActionListener(e -> frame.dispose());

        containerNavBar.add(buttonExit, BorderLayout.EAST);
        containerNavBar.add(labelTitleNavBaar, BorderLayout.WEST);
        return containerNavBar;
    }

    public JPanel containerTable() {
        containerTable = buildMethods.createPanel(60, 40, new BorderLayout(), colorBackgroundWhite, 30, 30, 30, 30);
        String[] columnBuy = {"Codigo", "Produto", "Tipo", "Marca", "Estoque", "Preço", "Quantidade", "Preço total"};
        previusDados = new DefaultTableModel(columnBuy, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        previusDados.addRow(new Object[]{1, "Essencia Morango", "Essencia", "Ziggy", 30, 10, 2, 20});
        
        
        previusBuyTable.setPreferredScrollableViewportSize(buildMethods.createResponsive(32, 60));
        previusBuyTable.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < previusBuyTable.getColumnCount(); i++) {
            TableColumn column = previusBuyTable.getColumnModel().getColumn(i);
            column.setResizable(false);
        }

        containerTable.add(previusBuyTable.getTableHeader(), BorderLayout.PAGE_START);
        containerTable.add(previusBuyTable, BorderLayout.CENTER);

        return containerTable;
    }

}
