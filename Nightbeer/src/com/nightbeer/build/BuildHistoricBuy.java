package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.nightbeer.dao.purchasesHistoricDAO;
import com.nightbeer.model.historic;

public class BuildHistoricBuy {
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
    private JTable historicBuyTable;
    private DefaultTableModel historicDados;

    public BuildHistoricBuy() {
        String[] columnBuy = {"id", "data", "hashmap", "total"};
        historicDados = new DefaultTableModel(columnBuy, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public JPanel containerMain(JFrame frame) {
        containerMain = buildMethods.createPanel(50, 70, new BorderLayout(), colorBackgroundWhite, 0, 0, 0, 0);

        containerMain.add(navbar(frame), BorderLayout.NORTH);
        containerMain.add(containerTable(), BorderLayout.CENTER);
        return containerMain;
    }

    public JPanel navbar(JFrame frame) {
        containerNavBar = buildMethods.createPanel(50, 3.5, new BorderLayout(), colorBlackBackground, 0, 0, 0, 0);
        labelTitleNavBaar = buildMethods.createLabel("Histórico de compras", 10, 3, SwingConstants.LEFT, colorBackgroundWhite, colorBlackBackground, FontRobotoPlainSmall, 0, 0, 0, 10);
        buttonExit = buildMethods.createButton("X", 2.5, 3.5, SwingConstants.CENTER, colorBlackBackground, colorButtonClose);

        buttonExit.addActionListener(e -> frame.dispose());

        containerNavBar.add(buttonExit, BorderLayout.EAST);
        containerNavBar.add(labelTitleNavBaar, BorderLayout.WEST);
        return containerNavBar;
    }

    public JPanel containerTable() {
        containerTable = buildMethods.createPanel(60, 40, new BorderLayout(), colorBackgroundWhite, 30, 30, 30, 30);

        historicBuyTable = new JTable(historicDados);
        historicBuyTable.setPreferredScrollableViewportSize(buildMethods.createResponsive(32, 60));
        historicBuyTable.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < historicBuyTable.getColumnCount(); i++) {
            TableColumn column = historicBuyTable.getColumnModel().getColumn(i);
            column.setResizable(false);
        }

        containerTable.add(historicBuyTable.getTableHeader(), BorderLayout.PAGE_START);
        containerTable.add(historicBuyTable, BorderLayout.CENTER);

        try {
            listHistoric();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return containerTable;
    }

    public void listHistoric() throws SQLException {
        purchasesHistoricDAO hDAO = new purchasesHistoricDAO();
        List<historic> listHistoric = hDAO.listHistoric();

        historicDados.setRowCount(0);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        for (historic i : listHistoric) {
            String formattedDate = i.getDate().format(dateFormat); 
            historicDados.addRow(new Object[] {
                i.getId(),
                formattedDate,
                i.getHashmapJSON(),
                i.getTotal()
            });
        }
    }
}
