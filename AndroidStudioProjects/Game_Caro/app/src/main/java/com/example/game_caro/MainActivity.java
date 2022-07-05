package com.example.game_caro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game_caro.model.GameAdapter;
import com.example.game_caro.viewmodel.GameViewModel;

public class MainActivity extends AppCompatActivity {
    private GameAdapter gameAdapter;
    private GameViewModel gameViewModel;

    RecyclerView recyclerView;
    TextView tvPlayer1, tvPlayer2;
    Button btnSave, btnRestart, btnNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameViewModel = new GameViewModel();
        gameViewModel.init(this);

        tvPlayer1 = findViewById(R.id.player1);
        tvPlayer2 = findViewById(R.id.player2);
        btnSave = findViewById(R.id.btnSave);
        btnRestart = findViewById(R.id.btnRestart);
        btnNewGame = findViewById(R.id.btnNewGame);

        recyclerView = findViewById(R.id.rcv_game);
        gameAdapter = new GameAdapter(gameViewModel.listCellForView(), index -> {
            if (!gameViewModel.isEmpty(index)) {
                return;
            }
            gameViewModel.onClickedAtCell(index);
            gameAdapter.notifyItemChanged(index);

        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 9);
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
        gameViewModel.winner.observe(this, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show());
        gameViewModel.isEndGame.observe(this, isEndGame -> {
            if (isEndGame) {
                gameViewModel.resetGame();
                gameAdapter.notifyDataSetChanged();
            }
        });
        btnSave.setOnClickListener(view -> {
            gameViewModel.saveGame();
        });

        btnRestart.setOnClickListener(view -> {
            gameViewModel.restartGame();
            gameAdapter.notifyDataSetChanged();
        });

        btnNewGame.setOnClickListener(view -> {
            gameViewModel.resetGame();
            gameAdapter.notifyDataSetChanged();
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