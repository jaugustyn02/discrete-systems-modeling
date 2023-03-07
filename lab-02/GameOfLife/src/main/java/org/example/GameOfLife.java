package org.example;

import java.util.Scanner;

public class GameOfLife {
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        System.out.println("Board size: " + rows+" x " + cols);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Number of generations: ");
        int numOfGenerations = scanner.nextInt();

        boolean[][] board = new boolean[rows][cols];
        initialize(board);

        System.out.println("\nInitial board:");
        print(board);

        for (int i = 1; i <= numOfGenerations; i++) {
            System.out.println("Generation " + i + ":");
            board = nextGeneration(board);
            print(board);
        }
    }

    public static void initialize(boolean[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Math.random() > 0.5;
            }
        }
    }

    public static void print(boolean[][] board) {
        for (boolean[] row : board) {
            for (boolean cell : row) {
                System.out.print(cellToString(cell) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String cellToString(boolean cell){
        if(cell) return "#";
        return "-";
    }

    public static boolean[][] nextGeneration(boolean[][] board) {
        boolean[][] newBoard = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int aliveNeighbors = getNumOfAliveNeighbors(board, i, j);
                if (board[i][j]) {
                    newBoard[i][j] = aliveNeighbors >= 2 && aliveNeighbors <= 3;
                } else {
                    newBoard[i][j] = aliveNeighbors == 3;
                }
            }
        }
        return newBoard;
    }

    public static int getNumOfAliveNeighbors(boolean[][] board, int row, int col) {
        int count = 0;
        for (int i = row-1; i <= row+1; i++) {
            for (int j = col-1; j <= col+1; j++) {
                if (i == row && j == col)
                    continue;
                if (i >= 0 && i < board.length && j >= 0 && j < board[0].length && board[i][j]) {
                    ++count;
                }
            }
        }
        return count;
    }
}