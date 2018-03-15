package com.example.flexiadaptertest.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.EditText;

/**
 * Created by afzal on 25/Jan/2017.
 */

public class GlobalMethods
{

    public static void setZeroToNull(EditText editText)
    {
        editText.setText(setZeroToNull(editText.getText().toString()));
    }

    public static String setZeroToNull(String text)
    {
        try
        {
            if (Integer.parseInt(text) == 0)
            {
                return "";
            }
            else
            {
                return Integer.parseInt(text)+"";
            }
        }
        catch (Exception ex)
        {
//            ex.printStackTrace();
            return "";
        }
    }

    public static Drawable getDrawableFromAttr(Context context, int attr)
    {
        int[] attrs = new int[] { attr /* index 0 */};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable drawable = ta.getDrawable(0);
        ta.recycle();
        return drawable;
    }

    public static float convertDpToPixel(float dp)
    {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}