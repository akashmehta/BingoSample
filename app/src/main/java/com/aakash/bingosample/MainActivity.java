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

    String[] array1 = new String[] {"A", "F", "C", "D"};
    String[] array2 = new String[] {"A", "F", "C", "G"};
    String[] array3 = new String[] {"A", "B", "F", "J"};
    String[] array4 = new String[] {"A", "E", "J", "O"};
    String[] array5 = new String[] {"A", "E", "I", "M", "J"};
    String[] array6 = new String[] {"A", "F", "J", "K", "N"};

    ArrayList<String[]> arrays = new ArrayList<>();
    ArrayList<TileDetails> tileCoordinate = new ArrayList<>();
    final ArrayList<String> charList = new ArrayList();
    ArrayList<ArrayList<TileDetails>> paths = new ArrayList();

    private boolean isTileInPath(int index) {
        for (String[] path:
             arrays) {
            for (String tile:
                 path) {
                if (tile.contains(charList.get(index))) return true;
            }
        }
        return false;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrays.add(array1);
        arrays.add(array2);
        arrays.add(array3);
        arrays.add(array4);
        arrays.add(array5);
        arrays.add(array6);
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
                            if (!isTileInPath(finalI)) {
                                bgView.clearPathList();
                            }
                            
                            ArrayList<ArrayList<TileDetails>> viewPaths = new ArrayList();
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
                    TileDetails coordinates = getCoordinate(layoutManager.findViewByPosition(i));
                    tileCoordinate.add(coordinates);
                }
                for (int i = 0; i < arrays.size(); i++) {
                    ArrayList<TileDetails> pairs = new ArrayList<>();
                    for (int j = 0; j < arrays.get(i).length; j++) {
                        pairs.add(tileCoordinate.get(charList.indexOf(arrays.get(i)[j])));
                    }
                    paths.add(pairs);
                }

            }
        });
    }

    private TileDetails getCoordinate(View view) {
        return new TileDetails((int)view.getX() + view.getWidth() / 2, (int)view.getY() + view.getHeight()/ 2 );
    }

}
