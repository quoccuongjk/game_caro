package com.example.game_caro.model;

import androidx.room.TypeConverter;

public class PlayerConverters {
    @TypeConverter
    public static Player stringToPlayer(String value) {
        return new Player(value);
    }

    @TypeConverter
    public static String playerToString(Player player) {
        return player == null? null: player.toString();
    }
}
