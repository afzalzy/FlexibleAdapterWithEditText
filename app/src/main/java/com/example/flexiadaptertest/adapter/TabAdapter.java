package com.example.flexiadaptertest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.flexiadaptertest.model.TabModel;

import java.util.ArrayList;


/**
 * Created by ominfowave on 5/12/15.
 */
public class TabAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<TabModel> listItems = new ArrayList<>();

    public TabAdapter(FragmentManager fm, ArrayList<TabModel> listItems)
    {
        super(fm);
        this.listItems = listItems;
    }

    @Override
    public Fragment getItem(int position)
    {
        return listItems.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listItems.get(position).getTabName();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }
}

