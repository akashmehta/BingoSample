package com.aakash.bingosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvBingo;
    BackgroundView bgView;

    ArrayList<String[]> arrays = new ArrayList<>();
    ArrayList<Pair<Integer,Integer>> tileCoordinate = new ArrayList<>();

    String testCase = "AFK, DGJ, GJM, CFI, HKN, BGL, EJO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] testCasesAr = testCase.split(",");
        for (int i = 0; i < testCasesAr.length; i++) {
            String[] tile = new String[testCasesAr[i].trim().length()];
            for (int j = 0; j < testCasesAr[i].trim().length(); j++) {
                tile[j] = String.valueOf(testCasesAr[i].trim().toCharArray()[j]);
            }
            arrays.add(tile);
        }

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
                                    pairs.add(tileCoordinate.get(charList.indexOf(arrays.get(i)[j])));
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
                    tileCoordinate.add(coordinates);
                }

            }
        });
    }

    private Pair<Integer, Integer> getCoordinate(View view) {
        return new Pair((int)view.getX() + view.getWidth() / 2, (int)view.getY() + view.getHeight()/ 2 );
    }

}
