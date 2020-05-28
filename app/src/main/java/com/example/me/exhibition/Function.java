package com.example.me.exhibition;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Function extends Fragment {

//    private String context;
//    private TextView mTextView;

    private ViewPager viewPager;
    private MyFragmentAdapter fragmentPagerAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private TopTabFragment fragment;
    private List<String> titles;
    private String[] title = {"预订门票","路径规划","场馆流量"};

    public Function(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_function,container,false);

        viewPager=view.findViewById(R.id.view_page);
        tabLayout=view.findViewById(R.id.tab_layout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        fragmentList=new ArrayList<>();
        titles=new ArrayList<>();
        for(int i=0;i<title.length;i++){
            titles.add(title[i]);

            if (i == 0){
                Fragment fre;
                fre = new TicketFragment();
                fragmentList.add(fre);
            }else if(i == 1){
                Fragment fre;
                fre = new RouteFragment();
                fragmentList.add(fre);
            }else {
                fragment = new TopTabFragment();

                fragmentList.add(fragment);
            }

        }

        fragmentPagerAdapter=new MyFragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);//与ViewPage建立关系
    }
}
