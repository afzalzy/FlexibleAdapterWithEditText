package com.example.flexiadaptertest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by afzal on 26/Feb/2018.
 */

public class OrderProductParseModel implements Parcelable, Cloneable
{
    private int id;
    private String description;
    private ArrayList<OrderProductWeekDayParseModel> listOfWeekday;
    private String W1Qty;
    private String W2Qty;
    private String W3Qty;
    private String W4Qty;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ArrayList<OrderProductWeekDayParseModel> getListOfWeekday()
    {
        return listOfWeekday;
    }

    public void setListOfWeekday(ArrayList<OrderProductWeekDayParseModel> listOfWeekday)
    {
        this.listOfWeekday = listOfWeekday;
    }

    public String getW1Qty()
    {
        return W1Qty;
    }

    public void setW1Qty(String w1Qty)
    {
        W1Qty = w1Qty;
    }

    public String getW2Qty()
    {
        return W2Qty;
    }

    public void setW2Qty(String w2Qty)
    {
        W2Qty = w2Qty;
    }

    public String getW3Qty()
    {
        return W3Qty;
    }

    public void setW3Qty(String w3Qty)
    {
        W3Qty = w3Qty;
    }

    public String getW4Qty()
    {
        return W4Qty;
    }

    public void setW4Qty(String w4Qty)
    {
        W4Qty = w4Qty;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeList(this.listOfWeekday);
        dest.writeString(this.W1Qty);
        dest.writeString(this.W2Qty);
        dest.writeString(this.W3Qty);
        dest.writeString(this.W4Qty);
    }

    public OrderProductParseModel()
    {
    }

    protected OrderProductParseModel(Parcel in)
    {
        this.id = in.readInt();
        this.description = in.readString();
        this.listOfWeekday = new ArrayList<OrderProductWeekDayParseModel>();
        in.readList(this.listOfWeekday, OrderProductWeekDayParseModel.class.getClassLoader());
        this.W1Qty = in.readString();
        this.W2Qty = in.readString();
        this.W3Qty = in.readString();
        this.W4Qty = in.readString();
    }

    public static final Creator<OrderProductParseModel> CREATOR = new Creator<OrderProductParseModel>()
    {
        @Override
        public OrderProductParseModel createFromParcel(Parcel source)
        {
            return new OrderProductParseModel(source);
        }

        @Override
        public OrderProductParseModel[] newArray(int size)
        {
            return new OrderProductParseModel[size];
        }
    };

    public OrderProductParseModel clone()
    {
        try
        {
            return (OrderProductParseModel)super.clone();
        }
        catch (CloneNotSupportedException ex)
        {
            ex.printStackTrace();
            return this;
        }
    }
}