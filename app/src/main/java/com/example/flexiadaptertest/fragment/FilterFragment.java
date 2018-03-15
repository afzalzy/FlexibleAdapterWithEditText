package com.example.flexiadaptertest.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flexiadaptertest.BR;
import com.example.flexiadaptertest.R;
import com.example.flexiadaptertest.adapter.RecyclerDataBindingAdapter;
import com.example.flexiadaptertest.databinding.FragmentFilterBinding;
import com.example.flexiadaptertest.listener.FilterOptionSelectListener;
import com.example.flexiadaptertest.model.CategoryParseModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by afzal on 01/Feb/2018.
 */

public class FilterFragment extends Fragment
{
    private FragmentFilterBinding binding;
    private ArrayList<CategoryParseModel> listOfCategory;
    private FilterOptionSelectListener listener;
    private Context context;
    public static final String Bundle_ListCategory = "ListCategory";

    public void setListener(FilterOptionSelectListener listener)
    {
        this.listener = listener;
    }

    public static FilterFragment getInstance(ArrayList<CategoryParseModel> listOfCategory, FilterOptionSelectListener listener)
    {
        FilterFragment filterFragment = new FilterFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FilterFragment.Bundle_ListCategory, listOfCategory);
        filterFragment.setArguments(bundle);
        filterFragment.setListener(listener);
        return filterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initializeComponents();
    }

    protected void initializeComponents()
    {
        if(getArguments() != null)
        {
            if(getArguments().containsKey(FilterFragment.Bundle_ListCategory))
            {
                listOfCategory = getArguments().getParcelableArrayList(FilterFragment.Bundle_ListCategory);
            }
        }
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(BR.handler, listener);
        RecyclerDataBindingAdapter adapter = new RecyclerDataBindingAdapter(R.layout.row_filter_option, listOfCategory, hashMap);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
    }
}