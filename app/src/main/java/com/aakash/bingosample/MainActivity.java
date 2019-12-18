package com.aakash.bingosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvBingo;
    BackgroundView bgView;
    RadioGroup rgTestCases;

    ArrayList<String[]> arrays = new ArrayList<>();
    ArrayList<Pair<Integer,Integer>> tileCoordinate = new ArrayList<>();

    String testCase_3_1 = "AEI, EIM, BFJ, FJN, CGK, GKO, DHL";
    String testCase_3_2 = "ABC, BCD, EFG, FGH, IJK, JKL, MNO";
    String testCase_3_3 = "AFK, DGJ, GJM, CFI, HKN, BGL, EJO";
    String testCase_4_1 = "AEIM, BFJN, CGKO, CDHL, EIMN";
    String testCase_4_2 = "ABCD, EFGH, IJKL, IMNO, EABC";
    String testCase_4_3 = "DGJM";
    String testCase_5_1 = "AEIMN, BFJNO, BFJNM, CGKON, MIEAB, NJFBC, NJFBA, OKGCD, OKGCB";
    String testCase_5_2 = "ABCDH, EFGHL, EFGHD, IJKLH, AEFGH, IEFGH, EIJKL, MIJKL, EABCD";
    String testCase_6_1 = "AEIMNO, MIEABC, CGKONM, NJFBCD, OKGCBA";
    String testCase_6_2 = "ABCDHL, DCBAEI, IJKLHD, AEIJKL, HGFEIM";
    String testCase_6_3 = "ABCDGJM, ABCFE";

    private void setArrays(String testCase) {
        arrays.clear();
        String[] testCasesAr = testCase.split(",");
        for (String s : testCasesAr) {
            String[] tile = new String[s.trim().length()];
            for (int j = 0; j < s.trim().length(); j++) {
                tile[j] = String.valueOf(s.trim().toCharArray()[j]);
            }
            arrays.add(tile);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> charList = new ArrayList<>();
        for (int i = 65; i < 65 + 15 ; i++) {
            char item = (char)i;
            charList.add(String.valueOf(item));
        }

        bgView = findViewById(R.id.bgView);
        rvBingo = findViewById(R.id.rvBingoItems);
        rgTestCases = findViewById(R.id.rgTestCases);
        rgTestCases.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbTC31:
                        setArrays(testCase_3_1);
                        break;
                    case R.id.rbTC32:
                        setArrays(testCase_3_2);
                        break;
                    case R.id.rbTC33:
                        setArrays(testCase_3_3);
                        break;
                    case R.id.rbTC41:
                        setArrays(testCase_4_1);
                        break;
                    case R.id.rbTC42:
                        setArrays(testCase_4_2);
                        break;
                    case R.id.rbTC43:
                        setArrays(testCase_4_3);
                        break;
                    case R.id.rbTC51:
                        setArrays(testCase_5_1);
                        break;
                    case R.id.rbTC52:
                        setArrays(testCase_5_2);
                        break;
                    case R.id.rbTC61:
                        setArrays(testCase_6_1);
                        break;
                    case R.id.rbTC62:
                        setArrays(testCase_6_2);
                        break;
                    case R.id.rbCustom:
                        setArrays(testCase_6_3);
                        break;
                }
            }
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        rvBingo.setLayoutManager(layoutManager);
        rvBingo.setAdapter(new BingoAdapter(charList));

        rvBingo.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < charList.size(); i++) {
                    final int finalI = i;
                    if (layoutManager.findViewByPosition(i) != null) {
                        layoutManager.findViewByPosition(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bgView.clearPathList();
                                ArrayList<ArrayList<Pair<Integer,Integer>>> paths = new ArrayList<>();
                                for (int i = 0; i < arrays.size(); i++) {
                                    ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
                                    for (int j = 0; j < arrays.get(i).length; j++) {
                                        pairs.add(tileCoordinate.get(charList.indexOf(arrays.get(i)[j])));
                                    }
                                    paths.add(pairs);
                                }
                                ArrayList<ArrayList<Pair<Integer,Integer>>> viewPaths = new ArrayList<>();
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
                }

                for (int i = 0; i < charList.size(); i++) {
                    if (layoutManager.findViewByPosition(i) != null) {
                        Pair<Integer, Integer> coordinates = getCoordinate(layoutManager.findViewByPosition(i));
                        tileCoordinate.add(coordinates);
                    }
                }

            }
        });
    }

    private Pair<Integer, Integer> getCoordinate(View view) {
        return new Pair<>((int)view.getX() + view.getWidth() / 2, (int)view.getY() + view.getHeight()/ 2 );
    }

}
