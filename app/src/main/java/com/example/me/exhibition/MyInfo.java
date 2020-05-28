package com.example.me.exhibition;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyInfo extends Fragment {

//    private String context;
//    private TextView mTextView;

    public MyInfo(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_info,container,false);
//        mTextView = (TextView)view.findViewById(R.id.fragment3);
//        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
//        context = "myInfo";
//        mTextView.setText(context);
        return view;
    }
}
