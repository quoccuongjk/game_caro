package com.example.game_caro.viewmodel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.game_caro.model.Cell;
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
        sharedPreferences = context.getSharedPreferences("dataGame", MODE_PRIVATE);
    }
    public List<Cell> listCellForView() {
        return game.listCells;
    }
    public boolean isEmpty(int index) {
        return game.isEmpty(index);
    }

    public void onClickedAtCell(int index) {
        game.setPlayerAt(index);

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
        editor.putString("data",jsonString);
        editor.apply();
    }
    public void restartGame() {
        String strJson = sharedPreferences.getString("data", "");
        Gson gson = new Gson();
        Game gameRestart = gson.fromJson(strJson,Game.class);
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
