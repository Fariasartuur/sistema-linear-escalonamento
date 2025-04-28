package org.example;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static double[][] sistema;
    static double[] solucao;

    static void insertSistema(int linha, int coluna){
        sistema = new double[linha][coluna];
        for(int i = 0; i < linha; i++){
            System.out.println("Linha " + (i+1));
            for(int j = 0; j < coluna; j++){
                if(j != coluna - 1){
                    System.out.printf("X%d - [%d][%d]:", j+1, i+1, j+1);
                } else{
                    System.out.printf("B - [%d][%d]:", i+1, j+1);
                }
                sistema[i][j] = sc.nextDouble();
            }
        }
    }

    static void printSistema(int linha, int coluna){
        for(int i = 0; i < linha; i++){
            System.out.printf("L%d - ", i+1);
            for(int j = 0; j < coluna; j++){
                double valor = sistema[i][j];

                if (j == coluna - 1) {
                    System.out.printf(Locale.US,"| %.1f", valor);
                } else {
                    System.out.printf(Locale.US,"%.1fX%d ", valor, j+1);
                }
            }
            System.out.println();
        }
    }

    static void escalonamento(int linhaAtual){
        int n = sistema.length;

        if(linhaAtual >= n-1) return;

        double pivot = sistema[linhaAtual][linhaAtual];

        for(int i = linhaAtual + 1; i < n; i++){
            double fator = sistema[i][linhaAtual] / pivot;
            for(int j = linhaAtual; j <= n; j++){
                sistema[i][j] -= fator * sistema[linhaAtual][j];
            }
        }

        escalonamento(linhaAtual + 1);
    }

    static boolean resolver() {
        int n = sistema.length;

        boolean sistemaImpossivel = false;
        boolean sistemaIndeterminado = false;

        for (int i = 0; i < n; i++) {
            boolean todosZeros = true;
            for (int j = 0; j < n; j++) {
                if (Math.abs(sistema[i][j]) > 1e-6) {
                    todosZeros = false;
                    break;
                }
            }
            if (todosZeros) {
                if (Math.abs(sistema[i][n]) > 1e-6) {
                    sistemaImpossivel = true;
                    break;
                } else {
                    sistemaIndeterminado = true;
                }
            }
        }

        if (sistemaImpossivel) {
            System.out.println("O sistema é impossível (SI).");
            return false;
        }

        if (sistemaIndeterminado) {
            System.out.println("O sistema possui infinitas soluções (SPI).");
            return false;
        }

        // Substituição regressiva
        for (int i = n - 1; i >= 0; i--) {
            double soma = sistema[i][n];
            for (int j = i + 1; j < n; j++) {
                soma -= sistema[i][j] * solucao[j];
            }

            double pivo = sistema[i][i];
            if (Math.abs(pivo) < 1e-6) {
                System.out.println("O sistema possui infinitas soluções (SPI).");
                return false;
            }

            solucao[i] = soma / pivo;
        }

        return true; // Resolvemos com sucesso
    }



    static void solucionar(int linha, int coluna){
        solucao = new double[linha];
        System.out.println("Sistema " + linha +"x"+ (coluna - 1) +":");

        insertSistema(linha, coluna);
        System.out.println("Antes do Escalonamento");
        printSistema(linha, coluna);
        escalonamento(0);
        System.out.println("Depois do Escalonamento");
        printSistema(linha, coluna);

        if (resolver()) {
            System.out.println("Solução:");
            for (int i = 0; i < solucao.length; i++) {
                System.out.printf(Locale.US, "X%d = %.2f%n", i+1, solucao[i]);
            }
        }
    }


    public static void main(String[] args) {

        int linha, coluna;
        int j = 1;

        System.out.println("Escolha o tamanho do Sistema Linear:");
        for(int i = 3; i<=10; i++){
            System.out.printf("%d - %dx%d", j, i, i);
            j++;
            System.out.println();
        }

        System.out.print("Opção: ");
        int op = sc.nextInt();

        switch(op){
            case 1: // 3x3
                linha = 3;
                coluna = 4;

                solucionar(linha, coluna);
                break;
            case 2: // 4x4
                linha = 4;
                coluna = 5;

                solucionar(linha, coluna);
                break;
            case 3: // 5x5
                linha = 5;
                coluna = 6;

                solucionar(linha, coluna);
                break;
            case 4: // 6x6
                linha = 6;
                coluna = 7;

                solucionar(linha, coluna);
                break;
            case 5: // 7x7
                linha = 7;
                coluna = 8;

                solucionar(linha, coluna);
                break;
            case 6: // 8x8
                linha = 8;
                coluna = 9;

                solucionar(linha, coluna);
                break;
            case 7: // 9x9
                linha = 9;
                coluna = 10;

                solucionar(linha, coluna);
                break;
            case 8: // 10x10
                linha = 10;
                coluna = 11;

                solucionar(linha, coluna);
                break;
            default:
                System.out.println("Opção Incorreta.");
        }

        sc.close();
    }
}