package com.example.flexiadaptertest.model;

import android.support.v4.app.Fragment;

/**
 * Created by afzal on 02/Feb/2017.
 */

public class TabModel
{
    public TabModel(String tabName, Fragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;
    }

    private String tabName;

    private Fragment fragment;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
