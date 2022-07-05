package com.example.game_caro.model;

import androidx.annotation.NonNull;

public class Player {
    private PlayerValue playerValue;

    public Player(PlayerValue playerValue) {
        this.playerValue = playerValue;
    }

    public Player(String vl) {
        if (vl.equalsIgnoreCase("-")) {
            playerValue = PlayerValue.VALUE_EMPTY;
        } else {
            if (vl.equalsIgnoreCase("X")) {
                playerValue = PlayerValue.VALUE_X;
            } else {
                playerValue = PlayerValue.VALUE_O;
            }
        }
    }

    public PlayerValue getPlayerValue() {
        return playerValue;
    }

    @NonNull
    @Override
    public String toString() {
        return playerValue == PlayerValue.VALUE_X ? "X" : (playerValue == PlayerValue.VALUE_O ? "O" : "-");
    }

    public boolean isSamePlayer(Player player) {
        return this.playerValue == player.playerValue;
    }

    public boolean isSamePlayer(PlayerValue playerValue) {
        return this.playerValue == playerValue;
    }
}
