package com.example.admin.ocr;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

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
    private static String TAG = ResultFragment.class.toString();

    public ResultFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, view);
        tvOCR.setText( Utilities.hanldingString(getArguments().getString("data")));
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

                                }
                            });
                    boomButton.addBuilder(builder);
                    break;
                }
                case 2: {
                    TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                            .normalImageRes(R.drawable.filter).normalColor(Color.parseColor("#FFAAA6"))
                            .normalText("Lọc max").listener(new OnBMClickListener() {
                                @Override
                                public void onBoomButtonClick(int index) {
                                    Log.d(TAG, "hiihi");
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

                                }
                            });
                    boomButton.addBuilder(builder);
                    break;
                }
            }


        }
    }





}
