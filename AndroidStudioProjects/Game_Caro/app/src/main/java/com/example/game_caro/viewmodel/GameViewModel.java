package com.example.game_caro.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.game_caro.model.Cell;
import com.example.game_caro.model.Constant;
import com.example.game_caro.model.Game;
import com.google.gson.Gson;

import java.util.List;

public class GameViewModel extends ViewModel {
    SharedPreferences sharedPreferences;
    private Game game;
    public MutableLiveData<Boolean> isEndGame = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPlayer = new MutableLiveData<>();
    public MutableLiveData<String> winner = new MutableLiveData<>();

    public void init(Context context) {
        game = new Game();
        isPlayer.postValue(false);
        sharedPreferences = context.getSharedPreferences("dataGame", Context.MODE_PRIVATE);
    }

    public List<Cell> listCellForView() {
        return game.listCells;
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

    public void saveGame() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(game);
        editor.putString("data", jsonString);
        editor.apply();
    }

    public void restartGame() {
        String strJson = sharedPreferences.getString("data", "");
        Gson gson = new Gson();
        Game gameRestart = gson.fromJson(strJson, Game.class);
        if (gameRestart == null)
            return;
        game.reloadGame(gameRestart);

        if (game.currentPlayer == game.player1) {
            isPlayer.postValue(false);
        } else {
            isPlayer.postValue(true);
        }
    }
}
