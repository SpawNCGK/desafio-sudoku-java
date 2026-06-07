package br.com.dio.sudoku;

import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Main {
    private static int[][] tabuleiro = new int[9][9];
    private static boolean[][] ehFixo = new boolean[9][9];

    private static final String STATUS_NAO_INICIADO = "Não Iniciado";
    private static final String STATUS_INCOMPLETO = "Incompleto";
    private static final String STATUS_COMPLETO = "Completo";
    private static String statusAtual = STATUS_NAO_INICIADO;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        if (args.length > 0) {
            inicializarTabuleiroComArgs(args);
            statusAtual = STATUS_INCOMPLETO;
        }

        do {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    inicializarTabuleiroComArgs(args);
                    statusAtual = STATUS_INCOMPLETO;
                    exibirTabuleiro();
                    break;

                case 2:
                    if (statusAtual.equals(STATUS_NAO_INICIADO)) {
                        System.out.println("Inicie o jogo primeiro (Opção 1)!");
                        break;
                    }
                    System.out.print("Digite o número (1-9): ");
                    int num = scanner.nextInt();
                    System.out.print("Índice Horizontal (Linha 0-8): ");
                    int linha = scanner.nextInt();
                    System.out.print("Índice Vertical (Coluna 0-8): ");
                    int coluna = scanner.nextInt();
                    colocarNumero(linha, coluna, num);
                    break;

                case 3:
                    System.out.print("Índice Horizontal (Linha 0-8): ");
                    int lRemover = scanner.nextInt();
                    System.out.print("Índice Vertical (Coluna 0-8): ");
                    int cRemover = scanner.nextInt();
                    removerNumero(lRemover, cRemover);
                    break;

                case 4:
                    exibirTabuleiro();
                    break;

                case 5:
                    verificarStatusEErros();
                    break;

                case 6:
                    limparJogadasUsuario();
                    break;

                case 7:
                    if (finalizarJogo()) {
                        System.out.println("🏆 PARABÉNS! Você completou o Sudoku com sucesso!");
                        opcao = 7;
                    } else {
                        System.out.println("⚠️ O jogo possui erros ou ainda está incompleto. Continue preenchendo!");
                    }
                    break;

                case 8:
                    System.out.println("Abrindo o Sudoku Gráfico... Divirta-se!");
                    // Passa a matriz lógica atual e a máscara de fixos para construir a tela
                    SwingUtilities.invokeLater(() -> {
                        SudokuGrafico tela = new SudokuGrafico(tabuleiro, ehFixo);
                        tela.setVisible(true);
                    });
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 7);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== SUDOKU MENU ===");
        System.out.println("1. Iniciar novo jogo");
        System.out.println("2. Colocar um novo número");
        System.out.println("3. Remover um número");
        System.out.println("4. Verificar jogo");
        System.out.println("5. Verificar status do jogo");
        System.out.println("6. Limpar jogadas");
        System.out.println("7. Finalizar o jogo");
        System.out.println("8. Abrir Interface Gráfica (Swing com Rascunho)");
    }

    private static void inicializarTabuleiroComArgs(String[] args) {
        tabuleiro = new int[9][9];
        ehFixo = new boolean[9][9];

        for (String arg : args) {
            try {
                String[] partes = arg.split(",");
                int l = Integer.parseInt(partes[0]);
                int c = Integer.parseInt(partes[1]);
                int v = Integer.parseInt(partes[2]);

                if (l >= 0 && l < 9 && c >= 0 && c < 9 && v >= 1 && v <= 9) {
                    tabuleiro[l][c] = v;
                    ehFixo[l][c] = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao ler argumento: " + arg + ". Use o formato L,C,V (Ex: 0,3,5)");
            }
        }
        System.out.println("Jogo inicializado com os números fixos!");
    }

    private static void exibirTabuleiro() {
        System.out.println("\n    0 1 2   3 4 5   6 7 8");
        System.out.println("  -------------------------");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < 9; j++) {
                if (tabuleiro[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(tabuleiro[i][j] + (ehFixo[i][j] ? " " : "*"));
                }
                if ((j + 1) % 3 == 0 && j < 8) System.out.print("| ");
            }
            System.out.println("|");
            if ((i + 1) % 3 == 0 && i < 8) System.out.println("  |-------+-------+-------|");
        }
        System.out.println("  -------------------------");
    }

    private static void colocarNumero(int linha, int coluna, int numero) {
        if (linha < 0 || linha > 8 || coluna < 0 || coluna > 8 || numero < 1 || numero > 9) {
            System.out.println("Posição ou número inválido!");
            return;
        }
        if (ehFixo[linha][coluna] || tabuleiro[linha][coluna] != 0) {
            System.out.println("⚠️ Esta posição já está preenchida e não pode ser alterada!");
            return;
        }
        tabuleiro[linha][coluna] = numero;
        System.out.println("Número inserido!");
    }

    private static void removerNumero(int linha, int coluna) {
        if (linha < 0 || linha > 8 || coluna < 0 || coluna > 8) {
            System.out.println("Posição inválida!");
            return;
        }
        if (ehFixo[linha][coluna]) {
            System.out.println("❌ Erro: Este é um número fixo do jogo e NÃO pode ser removido!");
            return;
        }
        tabuleiro[linha][coluna] = 0;
        System.out.println("Número removido!");
    }

    private static void limparJogadasUsuario() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!ehFixo[i][j]) {
                    tabuleiro[i][j] = 0;
                }
            }
        }
        System.out.println("Todas as suas jogadas foram limpas. Apenas os números fixos permanecem.");
    }

    private static void verificarStatusEErros() {
        boolean temErro = verificarSeContemErros();

        boolean completo = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tabuleiro[i][j] == 0) {
                    completo = false;
                    break;
                }
            }
        }

        if (!statusAtual.equals(STATUS_NAO_INICIADO)) {
            statusAtual = completo ? STATUS_COMPLETO : STATUS_INCOMPLETO;
        }

        System.out.println("Status atual do jogo: [" + statusAtual + "]");
        System.out.println("Validação: " + (temErro ? "❌ Contém números em posições conflitantes!" : "✅ Sem erros até o momento."));
    }

    private static boolean verificarSeContemErros() {
        // 1. Validação de Linhas e Colunas
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valor = tabuleiro[i][j];
                if (valor != 0) {
                    for (int c = j + 1; c < 9; c++) {
                        if (tabuleiro[i][c] == valor) return true;
                    }
                    for (int l = i + 1; l < 9; l++) {
                        if (tabuleiro[l][j] == valor) return true;
                    }
                }
            }
        }

        // 2. Validação dos Quadrantes 3x3
        for (int rowQuadrant = 0; rowQuadrant < 9; rowQuadrant += 3) {
            for (int colQuadrant = 0; colQuadrant < 9; colQuadrant += 3) {
                boolean[] visitados = new boolean[10];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int val = tabuleiro[rowQuadrant + r][colQuadrant + c];
                        if (val != 0) {
                            if (visitados[val]) {
                                return true;
                            }
                            visitados[val] = true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean finalizarJogo() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tabuleiro[i][j] == 0) return false;
            }
        }
        return !verificarSeContemErros();
    }
}