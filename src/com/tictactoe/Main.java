package com.tictactoe;

import java.util.Scanner;

public class Main {

    private static boolean checkIfWins(String inputGame, char state) {
        // Check Row Wise
        char[][] ticTacToe = new char[3][3];
        int k = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ticTacToe[i][j] = inputGame.charAt(k++);
            }
        }

        // Check Row & Column Wise
        for (int i = 0; i < 3; i++) {
            if (ticTacToe[i][0] == state && ticTacToe[i][1] == state && ticTacToe[i][2] == state) {
                return true;
            }

            if (ticTacToe[0][i] == state && ticTacToe[1][i] == state && ticTacToe[2][i] == state) {
                return true;
            }
        }

        if (ticTacToe[0][0] == state && ticTacToe[1][1] == state && ticTacToe[2][2] == state) {
            return true;
        } else {
            return ticTacToe[0][2] == state && ticTacToe[1][1] == state && ticTacToe[2][0] == state;
        }
    }

    private static String getGameState(String inputGame) {

        boolean isXWins = checkIfWins(inputGame, 'X');
        boolean isOWins = checkIfWins(inputGame, 'O');

        // Count X and Y
        int countX = 0, countO = 0;
        for (int i = 0; i < inputGame.length(); i++) {
            if (inputGame.charAt(i) == 'X') {
                countX++;
            } else if (inputGame.charAt(i) == 'O') {
                countO++;
            }
        }

        if (Math.abs(countX - countO) > 1 || (isXWins && isOWins)) {
            return "Impossible";
        } else if (!isOWins && !isXWins && inputGame.contains("_") || inputGame.contains(" ")) {
            return "Game not finished";
        } else if (!isOWins && !isXWins && !inputGame.contains("_") && !inputGame.contains(" ")) {
            return "Draw";
        } else if (isOWins) {
            return "O wins";
        } else if (isXWins) {
            return "X wins";
        } else {
            return "Impossible";
        }

    }

    private static boolean isOccupied(String inputGame, int coor1, int coor2) {
        char[] values = new char[3];
        int index = 0;
        coor1 = coor1 % 3;
        for (int i = 0; i < inputGame.length(); i++) {
            if ((i + 1) % 3 == coor1) {
                values[index++] = inputGame.charAt(i);
            }
        }

        if (coor2 == 3 && values[0] == '_' || values[0] == ' ') {
            return false;
        } else if (coor2 == 2 && values[1] == '_' || values[1] == ' ') {
            return false;
        } else {
            return (coor2 != 1 || values[2] != '_') && values[2] != ' ';
        }
    }

    private static String updateValues(String inputGame, int coor1, int coor2, char turn) {
        int[] index = new int[3];
        int ind = 0;
        coor1 = coor1 % 3;

        for (int i = 0; i < inputGame.length(); i++) {
            if ((i + 1) % 3 == coor1) {
                index[ind++] = i;
            }
        }

        StringBuilder game = new StringBuilder(inputGame);
        if (coor2 == 3) {
            game.setCharAt(index[0], turn);
        } else if (coor2 == 2) {
            game.setCharAt(index[1], turn);
        } else {
            game.setCharAt(index[2], turn);
        }

        return game.toString();
    }

    private static void printTicTacToe(String inputGame) {
        System.out.println("---------");
        for (int iter = 0; iter < 9; iter += 3) {
            System.out.println(String.format("| %c %c %c |", inputGame.charAt(iter), inputGame.charAt(iter + 1), inputGame.charAt(iter + 2)));
        }
        System.out.println("---------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputGame = "_________";

        printTicTacToe(inputGame);
        String coordinates = null;
        int coor1, coor2;

        String gameState = "Game not finished";
        char turn = 'X';

        do {
            System.out.print("Enter the coordinates: ");
            coordinates = scanner.nextLine();

            if (coordinates.matches("[0-9 ]+")) {
                coor1 = (int) coordinates.charAt(0) - '0';
                coor2 = (int) coordinates.charAt(2) - '0';
                if (coor1 > 3 || coor2 > 3 || coor1 < 1 || coor2 < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    if (isOccupied(inputGame, coor1, coor2)) {
                        System.out.println("This cell is occupied! Choose another one!");
                    } else {
                        inputGame = updateValues(inputGame, coor1, coor2, turn);
                        printTicTacToe(inputGame);

                        if (turn == 'X') {
                            turn = 'O';
                        } else {
                            turn = 'X';
                        }

                        gameState = getGameState(inputGame);
                    }
                }
            }  else {
                System.out.println("You should enter numbers!");
            }
        } while (gameState.equalsIgnoreCase("Game not finished"));


        System.out.println(gameState);
    }
}

