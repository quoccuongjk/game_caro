package com.example.game_caro.model;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public Player player1;
    public Player player2;
    public Player currentPlayer;
    public List<Cell> listCells = new ArrayList<>();

    public Game() {
        listCells.clear();
        listCells.addAll(listCellForView());
        player1 = new Player(PlayerValue.VALUE_X);
        player2 = new Player(PlayerValue.VALUE_O);
        currentPlayer = player1;
    }

    public boolean isEmpty(int index) {
        return listCells.get(index).isEmpty();
    }

    public void setPlayerAt(int index) {
        listCells.get(index).setPlayer(currentPlayer);
    }

    public void reloadGame(Game storedGame) {
        this.player1 = storedGame.player1;
        this.player2 = storedGame.player2;
        this.currentPlayer = storedGame.currentPlayer;
        listCells.clear();
        listCells.addAll(storedGame.listCells);
    }

    public List<Cell> listCellForView() {
        List<Cell> result = new ArrayList<>();
        for (int i = 0; i < Constant.BOARD_SIZE * Constant.BOARD_SIZE; i++) {
            result.add(new Cell(null));
        }
        return result;
    }

    public boolean isGameEnd() {
        if (hasFiveSameOnHorizontalCells() || hasFiveSameOnVerticalCells() || hasFiveSameOnDiagonalCells()) {
            return true;
        }
        if (isBoardFull()) {
            return true;
        }
        return false;
    }

    private Cell cellAt(int x, int y) {
        int index = x * Constant.BOARD_SIZE + y;
        return listCells.get(index);
    }

    public boolean hasFiveSameOnVerticalCells() {
        printBoard();

        PlayerValue value = currentPlayer.getPlayerValue();
        for (int x = 0; x < Constant.BOARD_SIZE; x++) {
            for (int y = 0; y <= Constant.BOARD_SIZE - 5; y++) {
                if (areEquals(value, cellAt(x, y), cellAt(x, y + 1), cellAt(x, y + 2), cellAt(x, y + 3), cellAt(x, y + 4))) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean hasFiveSameOnHorizontalCells() {
        PlayerValue value = currentPlayer.getPlayerValue();
        for (int x = 0; x <= Constant.BOARD_SIZE - 5; x++) {
            for (int y = 0; y < Constant.BOARD_SIZE; y++) {
                if (areEquals(value, cellAt(x, y), cellAt(x + 1, y), cellAt(x + 2, y), cellAt(x + 3, y), cellAt(x + 4, y))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasFiveSameOnDiagonalCells() {
        PlayerValue value = currentPlayer.getPlayerValue();
        for (int x = 0; x < Constant.BOARD_SIZE - 5; x++) {
            for (int temp = 0; temp < 4; temp++) {
                try {
                    if (areEquals(value, cellAt(x + temp, x), cellAt(x + temp + 1, x + 1), cellAt(x + temp + 2, 2 + x), cellAt(x + temp + 3, 3 + x), cellAt(x + temp + 4, 4 + x))) {
                        return true;
                    }
                } catch (Exception e) {

                }

            }
        }

        for (int y = 0; y < Constant.BOARD_SIZE - 5; y++) {
            for (int temp = 0; temp < 4; temp++) {
                try {
                    if (areEquals(value, cellAt(y, y + temp), cellAt(y + 1, y + temp + 1), cellAt(2 + y, y + temp + 2), cellAt(3 + y, y + temp + 3), cellAt(4 + y, y + temp + 4))) {
                        return true;
                    }
                } catch (Exception e) {

                }

            }
        }
        for (int y = 0; y <= Constant.BOARD_SIZE - 5; y++) {
            for (int x = 4; x < Constant.BOARD_SIZE; x++) {
                for (int temp = 0; temp <= 4; temp++) {
                    try {
                        if (areEquals(value, cellAt(x + temp, y), cellAt(x + temp - 1, y + 1), cellAt(x + temp - 2, y + 2), cellAt(x + temp - 3, y + 3),
                                cellAt(x + temp - 4, y + 4))) {
                            return true;
                        }
                    } catch (Exception e) {

                    }

                }
            }
        }

        return false;
    }

    public boolean isBoardFull() {
//        for (int i = 0; i < Constant.BOARD_SIZE; i++) {
//            for (int j = 0; j < Constant.BOARD_SIZE; j++) {
//                if (cells[i][j] == null || cells[i][j].isEmpty()) {
//                    return false;
//                }
//            }
//        }
        return false;
    }

    public boolean areEquals(PlayerValue value, Cell... cells) {
        for (Cell cell : cells) {
            if (cell == null || cell.isEmpty() || !cell.getPlayer().isSamePlayer(currentPlayer) || !cell.getPlayer().isSamePlayer(value)) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        currentPlayer = player1;
        for (int i = 0; i < Constant.BOARD_SIZE * Constant.BOARD_SIZE; i++) {
            listCells.get(i).setPlayer(null);
        }
    }

    public void printBoard() {
        String log = "\n";
        for (int i = 0; i < Constant.BOARD_SIZE; i++) {
            log += "[";
            for (int j = 0; j < Constant.BOARD_SIZE; j++) {
                log += cellAt(i, j).getPrintValue() + " ";
            }
            log += "]\n";
        }
        Log.d("CUONG", "currentBoard = " + log);
    }
}
