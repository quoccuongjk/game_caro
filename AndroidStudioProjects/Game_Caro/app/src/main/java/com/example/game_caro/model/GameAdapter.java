package com.example.game_caro.model;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game_caro.OnClick;
import com.example.game_caro.R;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameHolder> {
    private List<Cell> cellList = new ArrayList<>();
    private OnClick onClick;
    public void setData(List<Cell> cells){
        cellList = cells;
    }
    public GameAdapter(List<Cell> cellList,OnClick onClick) {
        this.cellList.clear();
        this.cellList.addAll(cellList);
        notifyDataSetChanged();
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell,parent,false);
        return new GameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, @SuppressLint("RecyclerView") int position) {
        Cell cell = cellList.get(position);
        holder.tv_cell.setText(cell.getValue());
        holder.tv_cell.setOnClickListener(view -> onClick.onClickCell(position));
    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }
}

class GameHolder extends RecyclerView.ViewHolder {
    TextView tv_cell;
    public GameHolder(@NonNull View itemView) {
        super(itemView);
        tv_cell = itemView.findViewById(R.id.tv_cell);
    }
}
