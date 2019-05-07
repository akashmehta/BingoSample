package com.aakash.bingosample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BingoAdapter extends RecyclerView.Adapter<BingoAdapter.BingoHolder> {

    private final ArrayList<String> bingoItems;

    public BingoAdapter(ArrayList<String> bingoItems) {
        this.bingoItems = bingoItems;
    }

    @NonNull
    @Override
    public BingoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BingoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bingo_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull BingoHolder bingoHolder, int i) {
        View itemView = bingoHolder.itemView;
        TextView bingoItem = itemView.findViewById(R.id.tvBingoItem);
        bingoItem.setText(bingoItems.get(i));
    }

    @Override
    public int getItemCount() {
        return bingoItems.size();
    }

    public class BingoHolder extends RecyclerView.ViewHolder {

        public BingoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
