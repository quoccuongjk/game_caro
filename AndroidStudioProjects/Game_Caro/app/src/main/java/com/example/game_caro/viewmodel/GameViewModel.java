package com.example.game_caro.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.game_caro.model.Cell;
import com.example.game_caro.model.CellDAO;
import com.example.game_caro.model.CellDB;
import com.example.game_caro.model.Constant;
import com.example.game_caro.model.Game;
import com.example.game_caro.model.Player;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GameViewModel extends ViewModel {
    private Game game;
    public MutableLiveData<Boolean> isEndGame = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPlayer = new MutableLiveData<>();
    public MutableLiveData<String> winner = new MutableLiveData<>();

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void init() {
        game = new Game();
        isPlayer.postValue(false);
    }
    public void init1(String strjson) {
        game = restartGame(strjson);
        if(game.currentPlayer == game.player1) {
            isPlayer.postValue(false);
        } else {
            isPlayer.postValue(true);
        }
    }
    public boolean isEmpty(int index) {
        int[] rc = convertIndexToRC(index);
        int r = rc[0];
        int c = rc[1];
        return game.cells[r][c].isEmpty();
    }

    public void onClickedAtCell(int index) {
        int[] rc = convertIndexToRC(index);
        int r = rc[0];
        int c = rc[1];
        game.cells[r][c].setPlayer(game.currentPlayer);
        if (game.isGameEnd()) {
            isEndGame.postValue(true);
            if (game.currentPlayer == game.player1) {
                winner.postValue("player X");

            } else {
                winner.postValue("player O");
            }
            if (game.isBoardFull()) {
                winner.postValue("no one");
            }

        }
        switchPlayer();
    }

    private int[] convertIndexToRC(int index) {
        int[] output = new int[2];
        int r = index / Constant.BOARD_SIZE;
        int c = index % Constant.BOARD_SIZE;
        output[0] = r;
        output[1] = c;
        return output;
    }

    public void switchPlayer() {
        if (game.currentPlayer == game.player1) {
            isPlayer.postValue(true);
            game.currentPlayer = game.player2;
        } else {
            game.currentPlayer = game.player1;
            isPlayer.postValue(false);
        }
    }
    public void resetGame() {
        game.reset();
        isEndGame.postValue(false);
        isPlayer.postValue(false);
    }
    public String saveCell() {
        Cell[][] cells = game.cells;
        Player player = game.currentPlayer;
        Game saveGame = new Game(player,cells );
        Gson gson = new Gson();
        String strjson = gson.toJson(saveGame);
        return strjson;
    }
    public Game restartGame(String strjson) {
        Gson gson = new Gson();
        Game game = gson.fromJson(strjson,Game.class);
        return game;

    }
}
