package com.example.game_caro.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.game_caro.viewmodel.GameViewModel;

@Entity
public class Cell {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo
    private Player player;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cell(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public String getValue() {
        if (isEmpty()) {
            return "";
        }
        return player.getPlayerValue() == PlayerValue.VALUE_X ? "X" : "O";
    }

    public String getPrintValue() {
        if (isEmpty()) {
            return "-";
        }
        return player.getPlayerValue() == PlayerValue.VALUE_X ? "X" : "O";
    }

    public boolean isEmpty() {
        return player == null || player.getPlayerValue() == PlayerValue.VALUE_EMPTY;
    }
}
