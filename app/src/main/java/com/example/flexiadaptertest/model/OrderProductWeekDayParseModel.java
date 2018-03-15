package com.example.flexiadaptertest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by afzal on 26/Feb/2018.
 */

public class OrderProductWeekDayParseModel implements Parcelable, Cloneable
{
    private String quantity;
    private String returns;

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getReturns()
    {
        return returns;
    }

    public void setReturns(String returns)
    {
        this.returns = returns;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.quantity);
        dest.writeString(this.returns);
    }

    public OrderProductWeekDayParseModel()
    {
    }

    protected OrderProductWeekDayParseModel(Parcel in)
    {
        this.quantity = in.readString();
        this.returns = in.readString();
    }

    public static final Creator<OrderProductWeekDayParseModel> CREATOR = new Creator<OrderProductWeekDayParseModel>()
    {
        @Override
        public OrderProductWeekDayParseModel createFromParcel(Parcel source)
        {
            return new OrderProductWeekDayParseModel(source);
        }

        @Override
        public OrderProductWeekDayParseModel[] newArray(int size)
        {
            return new OrderProductWeekDayParseModel[size];
        }
    };

    public OrderProductWeekDayParseModel clone()
    {
        try
        {
            return (OrderProductWeekDayParseModel)super.clone();
        }
        catch (CloneNotSupportedException ex)
        {
            ex.printStackTrace();
            return this;
        }
    }
}