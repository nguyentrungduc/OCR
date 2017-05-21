package com.example.admin.ocr;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    @BindView(R.id.tvOCR)
    EditText tvOCR;
    @BindView(R.id.boom)
    BoomMenuButton boomButton;
    @BindView(R.id.tvR)
    TextView tvRe;
    String t;
    private static String TAG = ResultFragment.class.toString();


    public ResultFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_result, container, false);
        t = Ultis.hanldingString(getArguments().getString("data"));
        ButterKnife.bind(this, view);
        tvOCR.setText(t);
        initBoom();
        return view;
    }

    private void initBoom() {
        boomButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_2);
        boomButton.setButtonEnum(ButtonEnum.TextOutsideCircle);

        for (int i = 0; i < boomButton.getPiecePlaceEnum().pieceNumber(); i++) {
            switch (i) {
                case 0: {
                    TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                            .normalImageRes(R.drawable.filter).normalColor(Color.parseColor("#A8E6CE"))
                            .normalText("Lọc trung vị").listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Matrix m = Ultis.createMatrix(tvOCR.getText().toString());
                                    Matrix kq = Filter.MedianFilter(m);
                                    changeFragment(new ResultFilterFragment(), true,"Lọc trung vi",kq.toString());



                                }
                            });
                    boomButton.addBuilder(builder);
                    break;
                }
                case 1: {
                    TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                            .normalImageRes(R.drawable.filter).normalColor(Color.parseColor("#DCEDC2"))
                            .normalText("Lọc trung bình").listener(new OnBMClickListener() {

                                @Override
                                public void onBoomButtonClick(int index) {
                                    Matrix m = Ultis.createMatrix(tvOCR.getText().toString());
                                    Matrix kq = Filter.AverageFilter(m);
                                    changeFragment(new ResultFilterFragment(), true,"Lọc trung bình",kq.toString());



                                }
                            });
                    boomButton.addBuilder(builder);
                    break;
                }
                case 2: {
                    TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                            .normalImageRes(R.drawable.filter).normalColor(Color.parseColor("#FFAAA6"))
                            .normalText("Lọc min").listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Matrix m = Ultis.createMatrix(tvOCR.getText().toString());
                                    Matrix kq = Filter.MinFilter(m);
                                    changeFragment(new ResultFilterFragment(), true,"Lọc min",kq.toString());



                                }
                            });
                    boomButton.addBuilder(builder);
                    break;
                }
                case 3: {
                    TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                            .normalImageRes(R.drawable.filter).normalColor(Color.parseColor("#FF8C94"))
                            .normalText("Lọc max").listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Matrix m = Ultis.createMatrix(tvOCR.getText().toString());
                                    Matrix kq = Filter.MaxFilter(m);
                                    changeFragment(new ResultFilterFragment(), true,"Lọc max",kq.toString());


                                }
                            });
                    boomButton.addBuilder(builder);
                    break;
                }
            }


        }
    }

    public void changeFragment(Fragment fragment, boolean addToBackStack,String title, String kq){

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("kq",kq);

        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container123, fragment);
        if(addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }





}
