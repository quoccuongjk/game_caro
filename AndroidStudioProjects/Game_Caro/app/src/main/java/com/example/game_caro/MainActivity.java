package com.example.game_caro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game_caro.model.Cell;
import com.example.game_caro.model.Constant;
import com.example.game_caro.model.Game;
import com.example.game_caro.model.GameAdapter;
import com.example.game_caro.viewmodel.GameViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GameAdapter gameAdapter;
    private RecyclerView recyclerView;
    private GameViewModel gameViewModel;
    TextView tvPlayer1, tvPlayer2;
    Button btnSave,btnRestart,btnNewGame;
    SharedPreferences sharedPreferences;
    private List<Cell> cellList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameViewModel = new GameViewModel();
        sharedPreferences = getSharedPreferences("dataGame",MODE_PRIVATE);
        tvPlayer1 = findViewById(R.id.player1);
        tvPlayer2 = findViewById(R.id.player2);
        btnSave =findViewById(R.id.btnSave);
        btnRestart = findViewById(R.id.btnRestart);
        btnNewGame = findViewById(R.id.btnNewGame);
        gameViewModel.init();
        recyclerView = findViewById(R.id.rcv_game);
        gameAdapter = new GameAdapter(gameViewModel.getGame().listCellForView(), index -> {
            if (!gameViewModel.isEmpty(index)) {
                return;
            }
            gameViewModel.onClickedAtCell(index);
            gameAdapter.notifyItemChanged(index);

        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,9);
        recyclerView.setLayoutManager(gridLayoutManager);
        gameViewModel.isPlayer.observe(this, isPlayer -> {
            if (!isPlayer) {
                tvPlayer1.setBackgroundResource(R.color.teal_200);
                tvPlayer2.setBackgroundResource(R.color.white);
            } else {
                tvPlayer2.setBackgroundResource(R.color.teal_200);
                tvPlayer1.setBackgroundResource(R.color.white);
            }
        });
        recyclerView.setAdapter(gameAdapter);
        gameViewModel.winner.observe(this, s -> Toast.makeText(MainActivity.this,s, Toast.LENGTH_SHORT).show());
        gameViewModel.isEndGame.observe(this, isEndGame -> {
            if (isEndGame) {

                gameViewModel.resetGame();
                gameAdapter.notifyDataSetChanged();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("data",gameViewModel.saveCell());
                editor.commit();
                gameViewModel.resetGame();
                gameAdapter.notifyDataSetChanged();
                btnRestart.setEnabled(true);
                btnSave.setEnabled(false);
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strgson = sharedPreferences.getString("data","");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                gameViewModel.init1(strgson);
                gameAdapter = new GameAdapter(gameViewModel.getGame().listCellForView(), index -> {
                    if (!gameViewModel.isEmpty(index)) {
                        return;
                    }
                    gameViewModel.onClickedAtCell(index);
                    gameAdapter.notifyItemChanged(index);
                });
                editor.remove("data");
                editor.commit();
                gameViewModel.restartGame(strgson);
                btnSave.setEnabled(true);
                btnRestart.setEnabled(false);
            }
        });
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.resetGame();
                gameAdapter.notifyDataSetChanged();
                btnSave.setEnabled(true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("data");
                editor.commit();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameViewModel.isEndGame.removeObservers(this);
        gameViewModel.isPlayer.removeObservers(this);
        gameViewModel.winner.removeObservers(this);
    }
}