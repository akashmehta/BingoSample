package com.aakash.bingosample.ui.main;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.aakash.bingosample.BackgroundView;
import com.aakash.bingosample.BingoAdapter;
import com.aakash.bingosample.BingoViewModel;
import com.aakash.bingosample.R;
import com.aakash.bingosample.TileDetails;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_IS_SHOW_CONTENT = "is_show_content";
    private PageViewModel pageViewModel;
    private boolean showContent;

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
    private NestedScrollView nsvContent;

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

    private TileDetails getCoordinate(View view) {
        return new TileDetails((int)view.getX() + view.getWidth() / 2, (int)view.getY() + view.getHeight()/ 2 );
    }

    public static PlaceholderFragment newInstance(int index, boolean showContent) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putBoolean(ARG_IS_SHOW_CONTENT, showContent);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            showContent = getArguments().getBoolean(ARG_IS_SHOW_CONTENT);
        }
        if (!showContent) {
            pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
            pageViewModel.setIndex(index);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page, container, false);
        if (!showContent) {
            final TextView textView = root.findViewById(R.id.section_label);
            pageViewModel.getText().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textView.setText(s);
                }
            });
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (showContent) {

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

            bgView = view.findViewById(R.id.bgView);
            rvBingo = view.findViewById(R.id.rvBingoItems);
            final GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 4);
            rvBingo.setLayoutManager(layoutManager);
            rvBingo.setAdapter(new BingoAdapter(viewModels));
            nsvContent = view.findViewById(R.id.nsvContent);
            nsvContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                    bgView.scrollTo(i, i1);
                }
            });
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
    }
}