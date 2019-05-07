package com.aakash.bingosample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvBingo;
    BackgroundView bgView;

    String[] array1 = new String[] {"A", "F", "C", "D"};
    String[] array2 = new String[] {"A", "F", "C", "G"};

    ArrayList<String[]> arrays = new ArrayList<>();
    ArrayList<Pair<Integer,Integer>> coordinates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrays.add(array1);
        arrays.add(array2);
        final ArrayList<String> charList = new ArrayList();
        for (int i = 65; i < 65 + 15 ; i++) {
            char item = (char)i;
            charList.add(String.valueOf(item));
        }

        bgView = findViewById(R.id.bgView);
        rvBingo = findViewById(R.id.rvBingoItems);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvBingo.setLayoutManager(layoutManager);
        rvBingo.setAdapter(new BingoAdapter(charList));

        rvBingo.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < charList.size(); i++) {
                    final int finalI = i;
                    layoutManager.findViewByPosition(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bgView.clearPathList();
                            ArrayList<ArrayList<Pair<Integer,Integer>>> paths = new ArrayList();
                            for (int i = 0; i < arrays.size(); i++) {
                                ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
                                for (int j = 0; j < arrays.get(i).length; j++) {
                                    Pair<Integer, Integer> coordinates = getCoordinate(layoutManager.findViewByPosition(charList.indexOf(arrays.get(i)[j])));
                                    pairs.add(coordinates);
                                }
                                paths.add(pairs);
                            }

                            ArrayList<ArrayList<Pair<Integer,Integer>>> viewPaths = new ArrayList();

                            for (int j = 0; j < arrays.size(); j++) {
                                for (int k = 0; k < arrays.get(j).length; k++) {
                                    if (arrays.get(j)[k].contains(charList.get(finalI))) {
                                        viewPaths.add(paths.get(j));
                                    }
                                }
                            }
                            bgView.setItemArrays(viewPaths);

                        }
                    });
                }

                for (int i = 0; i < charList.size(); i++) {
                    Pair<Integer, Integer> coordinates = getCoordinate(layoutManager.findViewByPosition(i));
                    MainActivity.this.coordinates.add(coordinates);
                }


                ArrayList<ArrayList<Pair<Integer,Integer>>> paths = new ArrayList();
                for (int i = 0; i < arrays.size(); i++) {
                    ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
                    for (int j = 0; j < arrays.get(i).length; j++) {
                        Pair<Integer, Integer> coordinates = getCoordinate(layoutManager.findViewByPosition(charList.indexOf(arrays.get(i)[j])));
                        pairs.add(coordinates);
                    }
                    paths.add(pairs);
                }
                bgView.setItemArrays(paths);
            }
        });
    }

    private Pair<Integer, Integer> getCoordinate(View view) {
        return new Pair((int)view.getX() + view.getWidth() / 2, (int)view.getY() + view.getHeight()/ 2 );
    }

}
