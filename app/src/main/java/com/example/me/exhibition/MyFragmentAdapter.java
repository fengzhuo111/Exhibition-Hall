package com.example.me.exhibition;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titles;

    public MyFragmentAdapter(FragmentManager fragmentManager,List<Fragment> fragments,List<String>title) {
        super(fragmentManager);
        fragmentList=fragments;
        titles=title;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int i){
        return titles.get(i);
    }
}
