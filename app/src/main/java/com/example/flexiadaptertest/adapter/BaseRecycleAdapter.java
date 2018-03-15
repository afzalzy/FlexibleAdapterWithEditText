package com.example.flexiadaptertest.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.flexiadaptertest.BR;


/**
 * Created by Admin on 14-10-2016.
 */

public abstract class BaseRecycleAdapter extends RecyclerView.Adapter<BaseRecycleAdapter.MyViewHolder>
{
    public BaseRecycleAdapter()
    {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayout(), parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        Object obj = getItemByPosition(position);
        holder.bind(obj);
    }

    @Override
    public abstract int getItemCount();

    public abstract int getLayout();

    public abstract Object getItemByPosition(int position);

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ViewDataBinding binding;
        public MyViewHolder(ViewDataBinding binding)
        {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.item, obj);
            binding.setVariable(BR.position, getAdapterPosition());
            binding.executePendingBindings();
        }
    }
}