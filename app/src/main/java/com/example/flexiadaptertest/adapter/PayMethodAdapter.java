package com.example.flexiadaptertest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.flexiadaptertest.R;

import java.util.ArrayList;

/**
 * Created by afzal on 09/May/2017.
 */

public class PayMethodAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> listOfPayMethod;
    private LayoutInflater inflater;
    private int layout;

    public PayMethodAdapter(Context context, ArrayList<String> listOfPayMethod, int layout)
    {
        this.context = context;
        this.listOfPayMethod = listOfPayMethod;
        this.layout = layout;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return listOfPayMethod.size();
    }

    @Override
    public String getItem(int position)
    {
        return listOfPayMethod.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(layout, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(String object, ViewHolder holder)
    {
        holder.labelPayMethodName.setText(object);
    }

    protected class ViewHolder
    {
        private TextView labelPayMethodName;

        public ViewHolder(View view)
        {
            labelPayMethodName = (TextView) view.findViewById(R.id.labelPayMethodName);
        }
    }
}