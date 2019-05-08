package com.aakash.bingosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    final ArrayList<BingoViewModel> viewModels = new ArrayList<>();
    final ArrayList<String> charList = new ArrayList<>();
    ArrayList<ArrayList<TileDetails>> paths = new ArrayList<>();

    private boolean isTileInPath(int index) {
        for (String[] path:
             arrays) {
            for (String tile:
                 path) {
                if (tile.contains(viewModels.get(index).getText())) return true;
            }
        }
        return false;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paths.clear();
        // TODO need to upcate paths array when arrays will be updated
        arrays.add(array1);
        arrays.add(array2);
        arrays.add(array3);
        arrays.add(array4);
        arrays.add(array5);
        arrays.add(array6);
        for (int i = 65; i < 65 + 15 ; i++) {
            viewModels.add(new BingoViewModel(String.valueOf((char)i)));
            charList.add(String.valueOf((char)i));
        }

        bgView = findViewById(R.id.bgView);
        rvBingo = findViewById(R.id.rvBingoItems);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvBingo.setLayoutManager(layoutManager);
        rvBingo.setAdapter(new BingoAdapter(viewModels));

        rvBingo.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < viewModels.size(); i++) {
                    final int finalI = i;
                    layoutManager.findViewByPosition(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isTileInPath(finalI)) {
                                bgView.clearPathList();
                            }
                            switch (finalI) {
                                case 0:
                                    viewModels.set(finalI, viewModels.get(finalI).updateState(TileDetails.STATE_NOT_SELECTED));
                                    break;
                                case 1:
                                    viewModels.set(finalI, viewModels.get(finalI).updateState(TileDetails.STATE_SELECTED));
                                    break;
                                case 2:
                                    viewModels.set(finalI, viewModels.get(finalI).updateState(TileDetails.STATE_RIGHT_OPTION));
                                    break;
                                case 3:
                                    viewModels.set(finalI, viewModels.get(finalI).updateState(TileDetails.STATE_WRONG_OPTION));
                                    break;
                                case 4:
                                    viewModels.set(finalI, viewModels.get(finalI).updateState(TileDetails.STATE_SELECTED_RIGHT_OPTION));
                                    break;
                                case 5:
                                    viewModels.set(finalI, viewModels.get(finalI).updateState(TileDetails.STATE_SELECTED_WRONG_OPTION));
                                    break;
                            }
                            rvBingo.getAdapter().notifyItemChanged(finalI);
                            ArrayList<ArrayList<TileDetails>> viewPaths = new ArrayList();
                            for (int j = 0; j < arrays.size(); j++) {
                                for (int k = 0; k < arrays.get(j).length; k++) {
                                    if (arrays.get(j)[k].contains(viewModels.get(finalI).getText())) {
                                        paths.get(j).set(k, paths.get(j).get(k).updateState(TileDetails.STATE_SELECTED));
                                        viewPaths.add(paths.get(j));
                                    }
                                }
                            }
                            bgView.setItemArrays(viewPaths);
                        }
                    });
                }

                for (int i = 0; i < charList.size(); i++) {
                    tileCoordinate.add(getCoordinate(layoutManager.findViewByPosition(i)));
                }

                // TODO update it when primary paths list changes.
                for (int i = 0; i < arrays.size(); i++) {
                    ArrayList<TileDetails> tiles = new ArrayList<>();
                    for (int j = 0; j < arrays.get(i).length; j++) {
                        tiles.add(tileCoordinate.get(charList.indexOf(arrays.get(i)[j])));
                    }
                    paths.add(tiles);
                }

            }
        });
    }

    private TileDetails getCoordinate(View view) {
        return new TileDetails((int)view.getX() + view.getWidth() / 2, (int)view.getY() + view.getHeight()/ 2 );
    }

}
