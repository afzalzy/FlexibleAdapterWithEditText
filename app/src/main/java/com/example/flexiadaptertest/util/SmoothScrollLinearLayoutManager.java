package com.example.flexiadaptertest.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import eu.davidea.flexibleadapter.common.IFlexibleLayoutManager;

/**
 * Created by afzal on 08/Feb/2018.
 */

public class SmoothScrollLinearLayoutManager extends LinearLayoutManager implements IFlexibleLayoutManager
{

    private RecyclerView.SmoothScroller mSmoothScroller;

    public SmoothScrollLinearLayoutManager(Context context) {
        this(context, VERTICAL, false);
    }

    public SmoothScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mSmoothScroller = new TopSnappedSmoothScroller(context, this);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        mSmoothScroller.setTargetPosition(position);
        startSmoothScroll(mSmoothScroller);
    }

    @Override
    public boolean supportsPredictiveItemAnimations()
    {
        return false;
    }

    @Override
    public int getSpanCount() {
        return 1;
    }

//    @Override
//    public View onInterceptFocusSearch(View focused, int direction)
//    {
//        if(super.onInterceptFocusSearch(focused, direction) == null)
//        {
//            return focused;
////            focused.getId() == R.id.textQuantity
//        }
//        return super.onInterceptFocusSearch(focused, direction);
//    }
}