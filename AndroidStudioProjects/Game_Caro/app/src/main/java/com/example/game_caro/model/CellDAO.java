package com.example.game_caro.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface CellDAO {
    @Insert
    void insert (Cell cell);
    @Query("SELECT * FROM Cell")
    List<Cell> getListCell();

}
