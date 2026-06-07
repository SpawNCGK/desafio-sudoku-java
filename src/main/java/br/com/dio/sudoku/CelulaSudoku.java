package br.com.dio.sudoku;

import javax.swing.*;
import java.awt.*;

public class CelulaSudoku extends JPanel {
    private JLabel labelPrincipal = new JLabel("", SwingConstants.CENTER);
    private JPanel painelRascunho = new JPanel(new GridLayout(3, 3));
    private JLabel[] labelsRascunho = new JLabel[10]; // Índices de 1 a 9

    private boolean ehFixo;

    public CelulaSudoku(int valorInicial, boolean ehFixo) {
        this.ehFixo = ehFixo;
        setLayout(new CardLayout()); // Permite alternar dinamicamente entre visualização normal e rascunho
        setFocusable(true);

        // 1. Estilização do Número Principal (Grande)
        labelPrincipal.setFont(new Font("Arial", Font.BOLD, 26));
        if (ehFixo) {
            labelPrincipal.setForeground(new Color(0, 51, 102)); // Azul escuro para fixos
            setBackground(new Color(230, 242, 255));
        } else {
            labelPrincipal.setForeground(Color.BLACK); // Preto para jogadas do usuário
            setBackground(Color.WHITE);
        }

        // 2. Estilização do Painel de Rascunho (Gradezinha 3x3 interna)
        painelRascunho.setOpaque(false);
        for (int i = 1; i <= 9; i++) {
            labelsRascunho[i] = new JLabel("", SwingConstants.CENTER);
            labelsRascunho[i].setFont(new Font("Arial", Font.PLAIN, 9));
            labelsRascunho[i].setForeground(Color.GRAY);
            painelRascunho.add(labelsRascunho[i]);
        }

        // Adiciona as duas visões dentro do card
        add(labelPrincipal, "PRINCIPAL");
        add(painelRascunho, "RASCUNHO");

        if (valorInicial != 0) {
            setValorPrincipal(valorInicial);
        } else {
            limparCelula();
        }

        // Feedback visual ao clicar no quadrado para digitar
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (!ehFixo) setBackground(new Color(255, 255, 204)); // Amarelo claro
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!ehFixo) setBackground(Color.WHITE);
            }
        });

        // Clique ativa o foco na célula
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestFocusInWindow();
            }
        });
    }

    public void setValorPrincipal(int valor) {
        labelPrincipal.setText(String.valueOf(valor));
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "PRINCIPAL");
    }

    public void alternarRascunho(int numero) {
        labelPrincipal.setText(""); // Remove número principal se houver
        String textoAtual = labelsRascunho[numero].getText();

        // Se o número já estava no rascunho, remove; se não estava, coloca
        if (textoAtual.isEmpty()) {
            labelsRascunho[numero].setText(String.valueOf(numero));
        } else {
            labelsRascunho[numero].setText("");
        }

        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "RASCUNHO");
    }

    public void limparCelula() {
        labelPrincipal.setText("");
        for (int i = 1; i <= 9; i++) {
            labelsRascunho[i].setText("");
        }
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "PRINCIPAL");
    }
}