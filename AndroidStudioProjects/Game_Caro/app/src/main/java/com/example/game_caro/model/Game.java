package com.example.game_caro.model;


import java.util.ArrayList;
import java.util.List;

public class Game {
    public Player player1;
    public Player player2;
    public Player currentPlayer;
    public Cell[][] cells;
    public List<Cell> listCells = new ArrayList<>();

    public Game() {
        cells = new Cell[Constant.BOARD_SIZE][Constant.BOARD_SIZE];
        for (int i = 0; i < Constant.BOARD_SIZE; i++) {
            for (int j = 0; j < Constant.BOARD_SIZE; j++) {
                cells[i][j] = new Cell(null);
            }
        }
        listCells.clear();
        listCells.addAll(listCellForView());

        //

        player1 = new Player(PlayerValue.VALUE_X);
        player2 = new Player(PlayerValue.VALUE_O);
        currentPlayer = player1;
    }

    public void reloadGame(Game storedGame) {
        this.player1 = storedGame.player1;
        this.player2 = storedGame.player2;
        this.currentPlayer = storedGame.currentPlayer;
        this.cells = storedGame.cells;
        listCells.clear();
        listCells.addAll(listCellForView());
    }

    private List<Cell> listCellForView() {
        List<Cell> result = new ArrayList<>();
        for (int i = 0; i < Constant.BOARD_SIZE; i++) {
            for (int j = 0; j < Constant.BOARD_SIZE; j++) {
                result.add(cells[i][j]);
            }
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

    public boolean hasFiveSameOnVerticalCells() {
        PlayerValue value = currentPlayer.getPlayerValue();
        for (int x = 0; x < Constant.BOARD_SIZE; x++) {
            for (int y = 0; y <= Constant.BOARD_SIZE - 5; y++) {
                if (areEquals(value, cells[x][y], cells[x][y + 1], cells[x][y + 2], cells[x][y + 3], cells[x][y + 4])) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean hasFiveSameOnHorizontalCells() {
        PlayerValue value = currentPlayer.getPlayerValue();
        for (int x = 0; x <= Constant.BOARD_SIZE-5; x++) {
            for (int y = 0; y < Constant.BOARD_SIZE; y++) {
                if (areEquals(value, cells[x][y], cells[x + 1][y], cells[x + 2][y], cells[x + 3][y], cells[x + 4][y])) {
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
                    if (areEquals(value, cells[x + temp][x], cells[x + temp + 1][x + 1], cells[x + temp + 2][2 + x], cells[x + temp + 3][3 + x], cells[x + temp + 4][4 + x])) {
                        return true;
                    }
                } catch (Exception e) {

                }

            }
        }

        for (int y = 0; y < Constant.BOARD_SIZE - 5; y++) {
            for (int temp = 0; temp < 4; temp++) {
                try {
                    if (areEquals(value, cells[y][y + temp], cells[y + 1][y + temp + 1], cells[2 + y][y + temp + 2], cells[3 + y][y + temp + 3], cells[4 + y][y + temp + 4])) {
                        return true;
                    }
                } catch (Exception e) {

                }

            }
        }
        for (int y = 0 ; y <= Constant.BOARD_SIZE-5; y++) {
            for (int x = 4; x < Constant.BOARD_SIZE; x++) {
                for (int temp = 0; temp <= 4; temp++) {
                    try {
                        if (areEquals(value, cells[x + temp][y+0], cells[x + temp - 1][y+1], cells[x + temp - 2][y+2], cells[x + temp - 3][y+3], cells[x + temp - 4][y+4])) {
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
        for (int i = 0; i < Constant.BOARD_SIZE; i++) {
            for (int j = 0; j < Constant.BOARD_SIZE; j++) {
                if (cells[i][j] == null || cells[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean areEquals(PlayerValue value, Cell... cells) {
        for (Cell cell : cells) {
            if (cell == null || cell.isEmpty() || cell.getPlayer() != currentPlayer || cell.getPlayer().getPlayerValue() != value) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        currentPlayer = player1;
        for (int i = 0; i < Constant.BOARD_SIZE; i++) {
            for (int j = 0; j < Constant.BOARD_SIZE; j++) {
                cells[i][j].setPlayer(null);
            }
        }
        listCells.clear();
        listCells.addAll(listCellForView());
    }
}
