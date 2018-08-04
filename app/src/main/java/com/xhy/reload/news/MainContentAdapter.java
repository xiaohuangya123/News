package com.xhy.reload.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xhy.reload.news.model.Column;

import java.util.List;

public class MainContentAdapter extends FragmentPagerAdapter{
    private List<Column> columnList;
//    private String[] titles = {"热点","头条","科技","财经","体育","时尚","社会","历史","游戏","读书","宇宙"};

    public MainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainContentAdapter(List<Column> columnList , FragmentManager fm) {
        super(fm);
        this.columnList = columnList;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MainContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("columnName",columnList.get(position).getColumnName());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return columnList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return columnList.get(position).getColumnName();
    }
}
