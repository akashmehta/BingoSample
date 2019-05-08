package com.aakash.bingosample;

public class BingoViewModel {

    private String text;
    private int state = TileDetails.STATE_NOT_SELECTED;

    public BingoViewModel(String text, int state) {
        this.text = text;
        this.state = state;
    }

    public BingoViewModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BingoViewModel updateState(int state) {
        return new BingoViewModel(this.text, state);
    }
}
