package com.example.flexiadaptertest.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by afzal on 16/Feb/2018.
 */

public class MyCustomEditTextListener implements TextWatcher
{
    private int position;
    private EditTextOnTextChangeListener listener;

    public interface EditTextOnTextChangeListener
    {
        void onTextChanged(int position, String text);
    }

    public void updatePosition(int position, EditTextOnTextChangeListener listener)
    {
        this.position = position;
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i2, int i3)
    {
//            if(s.toString().trim().length()>0)
//            {
//                listOfCustomerSaleReturn.get(position).setSaleQty(s.toString().trim());
//            }
        listener.onTextChanged(position, s.toString().trim());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}