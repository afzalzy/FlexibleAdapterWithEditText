package com.example.flexiadaptertest.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.example.flexiadaptertest.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractHeaderItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by afzal on 09/Feb/2018.
 */

public class HeaderItem extends AbstractHeaderItem<HeaderItem.HeaderViewHolder>
{

    private String id;
    private String title;
    /* number of times this item has been refreshed */
    protected int updates;

    public HeaderItem(String id)
    {
        super();
        this.id = id;
    }

    @Override
    public boolean equals(Object inObject)
    {
        if (inObject instanceof HeaderItem)
        {
            HeaderItem inItem = (HeaderItem) inObject;
            return this.getId().equals(inItem.getId());
        }
        return false;
    }

    @Override
    public boolean shouldNotifyChange(IFlexible newItem) {
        HeaderItem subItem = (HeaderItem) newItem;
        return !(id).equals(subItem.getId()); // Should be bound again if title is different
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public int getSpanSize(int spanCount, int position)
    {
        return spanCount;
    }

    public int getUpdates()
    {
        return updates;
    }

    public void increaseUpdates()
    {
        this.updates++;
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.row_order_header;
    }

    @Override
    public HeaderViewHolder createViewHolder(View view, FlexibleAdapter adapter)
    {
        return new HeaderViewHolder(view, adapter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bindViewHolder(FlexibleAdapter adapter, HeaderViewHolder holder, int position, List payloads)
    {
        holder.labelHeaderName.setText(getTitle());
    }

    static class HeaderViewHolder extends FlexibleViewHolder
    {

        TextView labelHeaderName;

        HeaderViewHolder(View view, FlexibleAdapter adapter)
        {
            super(view, adapter, true);//True for sticky
            labelHeaderName = view.findViewById(R.id.labelHeaderName);

            // Support for StaggeredGridLayoutManager
//            setFullSpan(true);
        }
    }

    @Override
    public String toString()
    {
        return "HeaderItem[id=" + id + ", title=" + title + "]";
    }

    @Override
    public boolean isSelectable()
    {
        return true;
    }
}