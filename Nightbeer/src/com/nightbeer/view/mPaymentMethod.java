package com.nightbeer.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.nightbeer.build.BuildMPrincipal;
import com.nightbeer.build.BuildPaymentMethod;

public class mPaymentMethod extends JFrame {
    private JPanel contentPane = new JPanel(new BorderLayout());
    private BuildMPrincipal buildMPrincipal = new BuildMPrincipal();
    private static mPaymentMethod instance;

    public mPaymentMethod(DefaultTableModel modelo, double total) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setTitle("Método de Pagamento");

        BuildPaymentMethod bp = new BuildPaymentMethod();
        bp.receberTabela(modelo); // Defina o modelo da tabela aqui
        bp.receberPriceTotal(total);
        
        contentPane.add(bp.containerMain(this));

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        instance = this;
    }

    public static mPaymentMethod getInstance() {
        return instance;
    }

    public JFrame getFrame() {
        return this;
    }
    
    public boolean getVisibleFrame() {
        return isVisible();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Exemplo de modelo da tabela
                    DefaultTableModel modelo = new DefaultTableModel(new Object[]{""}, 0);
                    mPaymentMethod frame = new mPaymentMethod(modelo, 0);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
