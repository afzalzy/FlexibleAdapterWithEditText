package com.example.flexiadaptertest.adapter;

import android.support.annotation.LayoutRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by afzal on 24/Jan/2018.
 */

public class RecyclerDataBindingAdapter<T extends Object> extends BaseRecycleAdapter
{
    private int layout;
    private ArrayList<T> listOfItem;
    private HashMap<Integer, Object> hashMap = new HashMap<>();

    public RecyclerDataBindingAdapter(@LayoutRes int layout, ArrayList<T> listOfItem)
    {
        this.layout = layout;
        this.listOfItem = listOfItem;
    }

    public RecyclerDataBindingAdapter(@LayoutRes int layout, ArrayList<T> listOfItem, HashMap<Integer, Object> hashMap)
    {
        this.layout = layout;
        this.listOfItem = listOfItem;
        this.hashMap = hashMap;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        for(Map.Entry<Integer, Object> entry : hashMap.entrySet()) {
            holder.binding.setVariable(entry.getKey(), entry.getValue());
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {
        return listOfItem.size();
    }

    @Override
    public int getLayout()
    {
        return layout;
    }

    @Override
    public Object getItemByPosition(int position)
    {
        return listOfItem.get(position);
    }
}