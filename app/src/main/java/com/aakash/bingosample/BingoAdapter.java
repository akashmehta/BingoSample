package com.aakash.bingosample;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BingoAdapter extends RecyclerView.Adapter<BingoAdapter.BingoHolder> {

    private final ArrayList<BingoViewModel> bingoItems;

    BingoAdapter(ArrayList<BingoViewModel> bingoItems) {
        this.bingoItems = bingoItems;
    }

    @Override
    public int getItemViewType(int position) {
        return bingoItems.get(position).getState();
    }

    @NonNull
    @Override
    public BingoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BingoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bingo_item, null));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull BingoHolder bingoHolder, int i) {
        View itemView = bingoHolder.itemView;
        Context context = itemView.getContext();
        TextView bingoItem = itemView.findViewById(R.id.tvBingoItem);
        switch (getItemViewType(i)) {
            case TileDetails.STATE_NOT_SELECTED:
                bingoItem.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
                break;
            case TileDetails.STATE_SELECTED:
                bingoItem.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_item_selected));
                break;
            case TileDetails.STATE_RIGHT_OPTION:
                bingoItem.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_item_right));
                break;
            case TileDetails.STATE_WRONG_OPTION:
                bingoItem.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_item_wrong));
                break;
            case TileDetails.STATE_SELECTED_RIGHT_OPTION:
                bingoItem.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_item_selected_right));
                break;
            case TileDetails.STATE_SELECTED_WRONG_OPTION:
                bingoItem.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_item_selected_wrong));
                break;
        }
        bingoItem.setText(bingoItems.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return bingoItems.size();
    }

    class BingoHolder extends RecyclerView.ViewHolder {

        BingoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
