package com.example.flexiadaptertest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flexiadaptertest.adapter.TabAdapter;
import com.example.flexiadaptertest.fragment.FilterFragment;
import com.example.flexiadaptertest.fragment.OrderFragment;
import com.example.flexiadaptertest.listener.FilterOptionSelectListener;
import com.example.flexiadaptertest.model.CategoryParseModel;
import com.example.flexiadaptertest.model.TabModel;
import com.example.flexiadaptertest.util.MyViewPager;
import com.example.flexiadaptertest.util.SlidingTabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private MyViewPager pager;
    private TabAdapter adapter;
    private DrawerLayout drawerLayout;
    private SlidingTabLayout tabs;
    private ArrayList<String> listOfItems;
    private ArrayList<TabModel> arrayListOfTabItems = new ArrayList<>();

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    public void initializeComponents() {
        ((Toolbar)findViewById(R.id.toolbar)).setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        if(findViewById(R.id.toolbar) != null)
        {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            showBackButton(true);
        }
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        listOfItems = new ArrayList<>();
        showTitle("Customer");

        initTab();
    }

    protected void showBackButton(boolean show)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        getSupportActionBar().setDisplayShowHomeEnabled(show);
        findViewById(R.id.dummyView).setVisibility(show ? View.GONE : View.VISIBLE);
    }

    protected void showTitle(String title)
    {
        ((TextView)findViewById(R.id.textViewTitle)).setGravity(Gravity.LEFT);
        ((TextView) findViewById(R.id.textViewTitle)).setText(title);
        ((TextView) findViewById(R.id.textViewTitle)).setVisibility(View.VISIBLE);
//        findViewById(R.id.imageViewLogo).setVisibility(View.GONE);
    }

    private void initTab()
    {
        arrayListOfTabItems.add(new TabModel("Orders", new OrderFragment()));
        adapter = new TabAdapter(getSupportFragmentManager(), arrayListOfTabItems);
        pager = (MyViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(arrayListOfTabItems.size());
//        pager.setOffscreenPageLimit(0);
        pager.setAdapter(adapter);


        pager.setFocusable(false);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setFocusable(false);
//        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }

        });
        tabs.setViewPager(pager);
//        pager.setCurrentItem(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    public void openFilter(ArrayList<CategoryParseModel> listOfCategory, FilterOptionSelectListener listener)
    {
        FilterFragment fragment = FilterFragment.getInstance(listOfCategory, listener);
        navigateToFragment(fragment, false);
        drawerLayout.openDrawer(Gravity.END);
    }

    public void closeFilter()
    {
        drawerLayout.closeDrawer(Gravity.END);
    }

    protected void navigateToFragment(Fragment fragment, boolean isAddToBackStack)
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContainer, fragment, fragment.getClass().getName());
        if(isAddToBackStack)
        {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }
}