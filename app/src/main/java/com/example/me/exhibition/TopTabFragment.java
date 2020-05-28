package com.example.me.exhibition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopTabFragment extends Fragment {
    private TextView tv;

    private String mTitle;

    public TopTabFragment(){}

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.frament_tab,container,false);
//        tv=view.findViewById(R.id.tv_title);
//        tv.setText(mTitle);
        return view;
    }
}
