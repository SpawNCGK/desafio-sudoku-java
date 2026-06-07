package br.com.dio.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SudokuGrafico extends JFrame {

    // O tabuleiro visual será composto por 81 células customizadas
    private CelulaSudoku[][] celulasVisuais = new CelulaSudoku[9][9];
    private int[][] tabuleiroLogico = new int[9][9];
    private boolean[][] ehFixo = new boolean[9][9];

    public SudokuGrafico(int[][] tabuleiroInicial, boolean[][] fixosIniciais) {
        this.tabuleiroLogico = tabuleiroInicial;
        this.ehFixo = fixosIniciais;

        // Configurações básicas da Janela Principal
        setTitle("Sudoku Backend & QA Challenge");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel central do Tabuleiro 9x9
        JPanel painelTabuleiro = new JPanel(new GridLayout(9, 9));
        painelTabuleiro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Inicializa e estiliza cada quadrado do jogo
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valorInicial = tabuleiroLogico[i][j];
                boolean fixo = ehFixo[i][j];

                celulasVisuais[i][j] = new CelulaSudoku(valorInicial, fixo);

                // Bordas mais grossas para separar os blocos 3x3 clássicos do Sudoku
                int top = (i % 3 == 0) ? 2 : 1;
                int left = (j % 3 == 0) ? 2 : 1;
                int bottom = (i == 8) ? 2 : 1;
                int right = (j == 8) ? 2 : 1;
                celulasVisuais[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.DARK_GRAY));

                // Adiciona o detector de teclado para preencher números ou rascunhos
                configurarEventosTeclado(i, j);

                painelTabuleiro.add(celulasVisuais[i][j]);
            }
        }

        add(painelTabuleiro, BorderLayout.CENTER);

        // Barra inferior de status e instruções
        JLabel labelInfo = new JLabel(" Clique em uma célula. Use Números para jogar | Shift + Número para Rascunho", SwingConstants.CENTER);
        labelInfo.setFont(new Font("Arial", Font.BOLD, 12));
        labelInfo.setPreferredSize(new Dimension(600, 30));
        add(labelInfo, BorderLayout.SOUTH);
    }

    private void configurarEventosTeclado(final int linha, final int coluna) {
        CelulaSudoku celula = celulasVisuais[linha][coluna];

        celula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (ehFixo[linha][coluna]) return; // Impede alterar os números fixos do jogo

                char caractere = e.getKeyChar();

                // Aceita apenas dígitos de 1 a 9
                if (caractere >= '1' && caractere <= '9') {
                    int valorDigitado = Character.getNumericValue(caractere);

                    // SE SEGURAR SHIFT: Ativa o modo RASCUNHO (Note)
                    if (e.isShiftDown()) {
                        celula.alternarRascunho(valorDigitado);
                    } else {
                        // JOGADA NORMAL: Preenche o quadrado grande
                        tabuleiroLogico[linha][coluna] = valorDigitado;
                        celula.setValorPrincipal(valorDigitado);
                    }
                }
                // Se apertar Backspace ou Delete, limpa a célula por completo
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    tabuleiroLogico[linha][coluna] = 0;
                    celula.limparCelula();
                }
            }
        });
    }
}