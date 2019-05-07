package com.aakash.bingosample;

public class TileDetails {
    public static final int STATE_NOT_SELECTED = 0;
    public static final int STATE_SELECTED = 1;
    public static final int STATE_RIGHT_OPTION = 2;
    public static final int STATE_WRONG_OPTION = 3;

    private int xVal;
    private int yVal;
    private int state = STATE_NOT_SELECTED;

    public TileDetails(int xVal, int yVal, int state) {
        this.xVal = xVal;
        this.yVal = yVal;
        this.state = state;
    }

    public TileDetails() {
    }

    public TileDetails updateXval(int xVal) {
        return new TileDetails(xVal, this.yVal, this.state);
    }

    public TileDetails updateYval(int yVal) {
        return new TileDetails(this.xVal, yVal, this.state);
    }

    public TileDetails updateState(int state) {
        return new TileDetails(this.xVal, this.yVal, state);
    }

    public TileDetails(int xVal, int yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
    }

    public int getxVal() {
        return xVal;
    }

    public void setxVal(int xVal) {
        this.xVal = xVal;
    }

    public int getyVal() {
        return yVal;
    }

    public void setyVal(int yVal) {
        this.yVal = yVal;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
