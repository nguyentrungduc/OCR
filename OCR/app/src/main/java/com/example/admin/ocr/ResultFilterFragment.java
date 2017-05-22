package com.example.admin.ocr;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFilterFragment extends Fragment {


    public ResultFilterFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRF)
    TextView tvRF;
    @BindView(R.id.imvBack)
    ImageView imvBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_result_filter, container, false);
        ButterKnife.bind(this,view );
        tvTitle.setText(getArguments().getString("title"));
        tvRF.setText(getArguments().getString("kq"));
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        return view;
    }

}
