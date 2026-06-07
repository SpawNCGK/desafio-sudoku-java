# 🧩 Desafio: Jogo de Sudoku Interativo em Java

Este repositório contém a resolução do desafio prático de desenvolvimento de um jogo de Sudoku interativo via console, integrado à trilha de Java da [DIO](https://www.dio.me/). O objetivo principal foi aplicar estruturas de dados multidimensionais (matrizes) e lógica de validação complexa.

## 🎯 Funcionalidades Implementadas

O sistema conta com um menu interativo e robusto que cobre os seguintes requisitos:
1. **Iniciar Novo Jogo:** Inicializa o tabuleiro limpando jogadas anteriores e carregando os números fixos iniciais passados via argumentos do sistema.
2. **Colocar Número:** Permite ao usuário jogar informando o número e as coordenadas (linha e coluna). O sistema bloqueia automaticamente qualquer tentativa de sobrescrever posições fixas ou já preenchidas.
3. **Remover Número:** Remove uma jogada do usuário através das coordenadas, emitindo um alerta de erro caso o usuário tente remover um número fixo do jogo.
4. **Verificar Jogo:** Renderiza o tabuleiro no console de forma organizada, diferenciando visualmente os números fixos das jogadas do usuário (marcadas com `*`).
5. **Verificar Status do Jogo:** Exibe o estado atual (Não Iniciado, Incompleto ou Completo) e roda o motor de validação para caçar números em posições conflitantes.
6. **Limpar:** Reseta o progresso do usuário, mantendo intactos os números fixos do início do jogo.
7. **Finalizar Jogo:** Avalia se o tabuleiro foi totalmente preenchido sem nenhum conflito de regras para decretar a vitória.

## 🛠️ Conceitos Computacionais Aplicados

* **Matrizes Bidimensionais (`int[][]` e `boolean[][]`):** Uso de matrizes $9 \times 9$ paralelas. Uma armazena os valores do tabuleiro e a outra serve como máscara de isolamento para proteger e travar os números fixos iniciais.
* **Algoritmo de Validação por Quadrantes:** Implementação de lógica matemática para mapear e varrer submatrizes $3 \times 3$ (os quadrantes do Sudoku), garantindo que a regra de não-repetição local seja cumprida, além das validações tradicionais de linhas e colunas.
* **Argumentos de Linha de Comando (`String[] args`):** Captura dinâmica de strings no formato `L,C,V` (Linha, Coluna, Valor) na inicialização do programa para definir o cenário inicial do jogo.

## 💻 Como Executar e Passar Argumentos no IntelliJ

1. Clone o repositório para sua máquina.
2. Abra o projeto Maven no IntelliJ.
3. Vá em **Run ➡️ Edit Configurations...**
4. No campo **Program arguments**, insira os números fixos iniciais separados por espaço (Exemplo: `0,0,5 0,1,3 1,0,6 4,4,9`).
5. Execute a classe `Main.java`.

---
Portfólio desenvolvido por Augusto César Nascimento. Foco em Backend, Estruturas de Dados e Qualidade de Código.