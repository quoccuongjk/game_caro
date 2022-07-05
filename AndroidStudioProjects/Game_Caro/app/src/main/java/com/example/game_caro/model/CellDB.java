package com.example.game_caro.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Cell.class, version = 1)
@TypeConverters({PlayerConverters.class})
public abstract class CellDB extends RoomDatabase {
    private static final String DATABASE_NAME = "cell.db";
    private static CellDB instance;

    public static synchronized CellDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CellDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CellDAO cellDAO();

}

