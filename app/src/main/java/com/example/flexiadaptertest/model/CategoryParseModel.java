package com.example.flexiadaptertest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by afzal on 26/Feb/2018.
 */

public class CategoryParseModel implements Parcelable, Cloneable
{
    private int catNo;
    private String catName;
    private ArrayList<OrderProductParseModel> listOfProducts;

    public int getCatNo()
    {
        return catNo;
    }

    public void setCatNo(int catNo)
    {
        this.catNo = catNo;
    }

    public String getCatName()
    {
        return catName;
    }

    public void setCatName(String catName)
    {
        this.catName = catName;
    }

    public ArrayList<OrderProductParseModel> getListOfProducts()
    {
        return listOfProducts;
    }

    public void setListOfProducts(ArrayList<OrderProductParseModel> listOfProducts)
    {
        this.listOfProducts = listOfProducts;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.catNo);
        dest.writeString(this.catName);
        dest.writeList(this.listOfProducts);
    }

    public CategoryParseModel()
    {
    }

    protected CategoryParseModel(Parcel in)
    {
        this.catNo = in.readInt();
        this.catName = in.readString();
        this.listOfProducts = new ArrayList<OrderProductParseModel>();
        in.readList(this.listOfProducts, OrderProductParseModel.class.getClassLoader());
    }

    public static final Creator<CategoryParseModel> CREATOR = new Creator<CategoryParseModel>()
    {
        @Override
        public CategoryParseModel createFromParcel(Parcel source)
        {
            return new CategoryParseModel(source);
        }

        @Override
        public CategoryParseModel[] newArray(int size)
        {
            return new CategoryParseModel[size];
        }
    };

    public CategoryParseModel clone()
    {
        try
        {
            return (CategoryParseModel)super.clone();
        }
        catch (CloneNotSupportedException ex)
        {
            ex.printStackTrace();
            return this;
        }
    }
}