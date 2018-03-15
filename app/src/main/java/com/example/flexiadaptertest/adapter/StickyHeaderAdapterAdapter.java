package com.example.flexiadaptertest.adapter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.flexiadaptertest.adapter.holder.HeaderItem;
import com.example.flexiadaptertest.adapter.holder.OrderHolder;
import com.example.flexiadaptertest.model.CategoryParseModel;

import java.util.ArrayList;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;

/**
 * Created by afzal on 24/Jan/2018.
 */

public class StickyHeaderAdapterAdapter extends FlexibleAdapter<AbstractSectionableItem>
{

    public StickyHeaderAdapterAdapter(ArrayList<CategoryParseModel> items, Object listeners, OrderHolder.ProductListener listener)
    {
        // stableIds ? true = Items implement hashCode() so they can have stableIds!
        super(StickyHeaderAdapterAdapter.makeHolders(items, listener), listeners, true);
        // In case you need a Handler, do this:
        // - Overrides the internal Handler with a custom callback that extends the internal one
        mHandler = new Handler(Looper.getMainLooper(), new MyHandlerCallback());
    }

    public static ArrayList<AbstractSectionableItem> makeHolders(ArrayList<CategoryParseModel> items, OrderHolder.ProductListener listener)
    {
        ArrayList<AbstractSectionableItem> holders = new ArrayList<>();
        for (int i = 0; i < items.size(); i++)
        {
            HeaderItem header = newHeader(items.get(i));
            for (int j = 0; j < items.get(i).getListOfProducts().size(); j++)
            {
                holders.add(new OrderHolder(items.get(i).getListOfProducts().get(j), header, listener));
            }
        }
        return holders;
    }

    public static HeaderItem newHeader(CategoryParseModel categoryModel)
    {
        HeaderItem header = new HeaderItem(categoryModel.getCatNo()+"");
        header.setTitle(categoryModel.getCatName());
        return header;
    }

    /*
     * You can override this method to define your own concept of "Empty".
     * This method is never called internally.
     */
    @Override
    public boolean isEmpty()
    {
        return super.isEmpty();
    }

    /*
     * HEADER VIEW
     * This method shows how to add Header View as it was for ListView.
     * Same Header item is enqueued for removal with a delay.
     * The view is represented by a custom Item type to better represent any dynamic content.
     */
//    public void showLayoutInfo(boolean scrollToPosition)
//    {
//        final ScrollableLayoutItem item = new ScrollableLayoutItem("LAY-L");
//
////        item.setTitle(mRecyclerView.getContext().getString(R.string.linear_layout));
////        item.setSubtitle(mRecyclerView.getContext().getString(R.string.columns, String.valueOf(getFlexibleLayoutManager().getSpanCount())));
//        // NOTE: If you have to change at runtime the LayoutManager AND add
//        // Scrollable Headers, consider to add them in post, using a delay >= 0
//        // otherwise scroll animations on all items will not start correctly.
//        addScrollableHeaderWithDelay(item, 1200L, scrollToPosition);
//        removeScrollableHeaderWithDelay(item, 4000L);
//    }

    @Override
    public String onCreateBubbleText(int position)
    {
        if (position < getScrollableHeaders().size())
        {
            return "Top";
        }
        else if (position >= getItemCount() - getScrollableFooters().size())
        {
            return "Bottom";
        }
        else
        {
            position -= getScrollableHeaders().size() + 1;
        }
        // TODO FOR YOU: The basic value, usually, is the first letter
        // return getItem(position).getBubbleText(position);

        // For me the position is (position + 1):
        return super.onCreateBubbleText(position);
    }

//    @Override
//    public void onAttachedToRecyclerView(final RecyclerView recyclerView)
//    {
//        super.onAttachedToRecyclerView(recyclerView);
//        recyclerView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
//
//                // Return false if scrolled to the bounds and allow focus to move off the list
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
//                        return tryMoveSelection(lm, 1);
//                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//                        return tryMoveSelection(lm, -1);
//                    }
//                }
//
//                return false;
//            }
//        });
//    }
//
//    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
//        int selectedItem = 0;
//        if(getSelectedItemCount() == 1)
//        {
//            selectedItem = getSelectedPositions().get(0);
//        }
//        int nextSelectItem = selectedItem + direction;
//
//        // If still within valid bounds, move the selection, notify to redraw, and scroll
//        if (nextSelectItem >= 0 && nextSelectItem < getItemCount()) {
//            notifyItemChanged(selectedItem);
//            selectedItem = nextSelectItem;
//            notifyItemChanged(selectedItem);
//            lm.scrollToPosition(selectedItem);
//            return true;
//        }
//
//        return false;
//    }

    /**
     * Showcase to reuse the internal Handler.
     * <p>
     * <b>IMPORTANT:</b> In order to preserve the internal calls, this custom Callback
     * <u>must</u> extends {@link FlexibleAdapter.HandlerCallback}
     * which implements {@link Handler.Callback},
     * therefore you <u>must</u> call {@code super().handleMessage(message)}.
     * <p>This handler can launch asynchronous tasks.</p>
     * If you catch the reserved "what", keep in mind that this code should be executed
     * <u>before</u> that task has been completed.
     * <p><b>Note:</b> numbers 0-9 are reserved for the Adapter, use others for new values.</p>
     */
    private class MyHandlerCallback extends HandlerCallback
    {
        @Override
        public boolean handleMessage(Message message)
        {
            boolean done = super.handleMessage(message);
            switch (message.what)
            {
                // Currently reserved (you don't need to check these numbers!)
                case 1: //async updateDataSet
                case 2: //async filterItems
                case 3: //confirm delete
                case 8: //onLoadMore remove progress item
                    return done;

                // Free to use, example:
                case 10:
                case 11:
                    return true;
            }
            return false;
        }
    }
}