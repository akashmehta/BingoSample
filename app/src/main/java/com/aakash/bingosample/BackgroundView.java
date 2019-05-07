package com.aakash.bingosample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;

public class BackgroundView extends View {
    Paint paint;

    private ArrayList<ArrayList<Pair<Integer, Integer>>> paths= new ArrayList();

    public BackgroundView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            for (int j = 1; j < paths.get(i).size(); j++) {
                Pair<Integer, Integer> point1 = paths.get(i).get(j-1);
                Pair<Integer, Integer> point2 = paths.get(i).get(j);
                canvas.drawLine(point1.first, point1.second, point2.first, point2.second, paint);
            }
            invalidate();
        }
    }

    public void setItemArrays(ArrayList<ArrayList<Pair<Integer, Integer>>> pathList) {
        this.paths = pathList;
        invalidate();
    }

    public void clearPathList() {
        this.paths.clear();
        invalidate();
    }
}
