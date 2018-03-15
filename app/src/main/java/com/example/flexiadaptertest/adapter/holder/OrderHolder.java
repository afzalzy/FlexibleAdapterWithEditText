package com.example.flexiadaptertest.adapter.holder;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.flexiadaptertest.R;
import com.example.flexiadaptertest.model.OrderProductParseModel;
import com.example.flexiadaptertest.util.GlobalMethods;
import com.example.flexiadaptertest.util.KeyboardLessEditText;
import com.example.flexiadaptertest.util.MyCustomEditTextListener;

import java.io.Serializable;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.IHolder;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Shreyash Mashru on 18-Feb-18.
 */

public class OrderHolder extends AbstractSectionableItem<OrderHolder.ItemViewHolder, HeaderItem>
        implements IFilterable, IHolder<OrderProductParseModel>
{
    private OrderProductParseModel modelData;
    private ProductListener listener;
    private int dayNo = 0;

    public OrderHolder(OrderProductParseModel modelData, HeaderItem header, ProductListener listener)
    {
        super(header);
        this.modelData = modelData;
        this.listener = listener;
    }

    @Override
    public OrderProductParseModel getModel()
    {
        return modelData;
    }

    @Override
    public boolean equals(Object inObject)
    {
        if (inObject instanceof OrderProductParseModel)
        {
            OrderProductParseModel OrderProductParseModel = (OrderProductParseModel) inObject;
            return (OrderProductParseModel.getId() + "_" + header.getId()).equals(modelData.getId() + "_" + header.getId());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return (modelData.getId() + "_" + header.getId()).hashCode();
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.row_order;
    }

    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, final ItemViewHolder holder, int position, List<Object> payloads)
    {
        Log.e("bindViewHolder ", "bindViewHolder");
        Context context = holder.itemView.getContext();

        holder.labelDescription.setText(position+"");

        holder.quantityChangeListener.updatePosition(holder.getAdapterPosition(), new MyCustomEditTextListener.EditTextOnTextChangeListener()
        {
            @Override
            public void onTextChanged(int position, String text)
            {
                setQuantity(text);
            }
        });
        holder.textQuantity.removeTextChangedListener(holder.quantityChangeListener);
        //holder.setIsRecyclable(true);
        holder.textQuantity.setText(getQuantity());
//
//
        holder.returnChangeListener.updatePosition(holder.getAdapterPosition(), new MyCustomEditTextListener.EditTextOnTextChangeListener()
        {
            @Override
            public void onTextChanged(int position, String text)
            {
                setReturns(text);
            }
        });
        holder.textRet.removeTextChangedListener(holder.returnChangeListener);
        //holder.setIsRecyclable(true);
        holder.textRet.setText(getReturns());




        holder.textQuantity.setOnFocusChangeListener(null);

        if(adapter.getSelectedItemCount() == 1 && position == adapter.getSelectedPositions().get(0))
        {
//                Log.e("on bind ", holder.getAdapterPosition() + " test");
            holder.textQuantity.requestFocus();
        }
        else
        {
            holder.textQuantity.clearFocus();
        }
        holder.textQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if(hasFocus)
                {
//                        Log.e("bindd focuschange ", holder.getAdapterPosition() + " test");
                    listener.onFocusGoneToPosition(holder.getAdapterPosition());
                    adapter.toggleSelection(holder.getAdapterPosition());
                    // holder.quantity.setBackgroundColor(GlobalMethods.getColorFromAttr(context, R.attr.colorPrimary));
                }
                else
                {
                    holder.textQuantity.removeTextChangedListener(holder.quantityChangeListener);
                    GlobalMethods.setZeroToNull(holder.textQuantity);
                    holder.textQuantity.addTextChangedListener(holder.quantityChangeListener);
//                    holder.quantity.setBackgroundColor(GlobalMethods.getColorFromAttr(context, R.attr.colorAccent));
                }
            }
        });
        holder.textRet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if(hasFocus)
                {
                    listener.onFocusGoneToPosition(holder.getAdapterPosition());
                    adapter.toggleSelection(holder.getAdapterPosition());
                    // holder.quantity.setBackgroundColor(GlobalMethods.getColorFromAttr(context, R.attr.colorPrimary));
                }
                else
                {
                    holder.textRet.removeTextChangedListener(holder.returnChangeListener);
                    GlobalMethods.setZeroToNull(holder.textRet);
                    holder.textRet.addTextChangedListener(holder.returnChangeListener);
//                    holder.quantity.setBackgroundColor(GlobalMethods.getColorFromAttr(context, R.attr.colorAccent));
                }
            }
        });
        View.OnKeyListener keyListener = new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
                {
//                    if(holder.getAdapterPosition() == totalSize - 1)
//                    {
////                        Log.e("called ", "called");
//                        listener.onLastItemFocusDown();
//                        return true;
//                    }
                }
                else if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT))
                {
                    return true;
                }
                return false;
            }
        };
        holder.itemView.setOnKeyListener(keyListener);
//        holder.textQuantity.setOnKeyListener(keyListener);
//        holder.textRet.setOnKeyListener(keyListener);
    }

    @Override
    public ItemViewHolder createViewHolder(View view, FlexibleAdapter adapter)
    {
        Log.e("createViewHolder ", "createViewHolder");
        return new ItemViewHolder(view, adapter);
    }

    @Override
    public void onViewAttached(FlexibleAdapter adapter, ItemViewHolder holder, int position)
    {
        super.onViewAttached(adapter, holder, position);
        Log.e("on view attached", "on view attached" + holder.getAdapterPosition());
//        Log.e("onViewAttached ", "onViewAttached");
        keyboardInput(adapter, holder);
    }

    @Override
    public void onViewDetached(FlexibleAdapter adapter, ItemViewHolder holder, int position)
    {
        holder.textQuantity.setOnFocusChangeListener(null);
        holder.textRet.setOnFocusChangeListener(null);
        super.onViewDetached(adapter, holder, position);
    }

    private void keyboardInput(FlexibleAdapter adapter, ItemViewHolder holder)
    {
        setText(adapter, holder.getAdapterPosition(), holder.textQuantity, getQuantity(), holder.quantityChangeListener, true);
        setText(adapter, holder.getAdapterPosition(), holder.textRet, getReturns(), holder.returnChangeListener, false);
    }

    private void setText(FlexibleAdapter adapter, int position, KeyboardLessEditText editText, String text, MyCustomEditTextListener listener, boolean focusShouldDo)
    {
        editText.removeTextChangedListener(listener);
        //        holder.quantity.setText(listOfCustomerSaleReturn.get(holder.getAdapterPosition()).getSaleQty());
        editText.setText(text);
        if(adapter.getSelectedItemCount() == 1 && position == adapter.getSelectedPositions().get(0) && focusShouldDo)
        {
//            Log.e("onViewAttached ", position + " test");
            editText.requestFocus();
            //            holder.quantity.setBackgroundColor(GlobalMethods.getColorFromAttr(context, R.attr.colorPrimary));
        }
        else
        {
            editText.clearFocus();
        }
        editText.selectAll();
        editText.addTextChangedListener(listener);
    }

    private void setQuantity(String text)
    {
        if(dayNo < modelData.getListOfWeekday().size())
        {
            modelData.getListOfWeekday().get(dayNo).setQuantity(text);
        }
    }

    private String getQuantity()
    {
        if(dayNo < modelData.getListOfWeekday().size())
        {
            return modelData.getListOfWeekday().get(dayNo).getQuantity();
        }
        return "";
    }

    private String getReturns()
    {
        if(dayNo < modelData.getListOfWeekday().size())
        {
            return modelData.getListOfWeekday().get(dayNo).getReturns();
        }
        return "";
    }

    private void setReturns(String text)
    {
        if(dayNo < modelData.getListOfWeekday().size())
        {
            modelData.getListOfWeekday().get(dayNo).setReturns(text);
        }
    }

    @Override
    public boolean filter(Serializable constraint)
    {
        return false;
    }

    public class ItemViewHolder extends FlexibleViewHolder
    {
        TextView labelDescription, labelW1, labelW2, labelW3, labelW4;
        private KeyboardLessEditText textQuantity, textRet;
        public MyCustomEditTextListener quantityChangeListener = new MyCustomEditTextListener(), returnChangeListener = new MyCustomEditTextListener();

        public ItemViewHolder(View itemView, final FlexibleAdapter adapter)
        {
            super(itemView, adapter);
            labelDescription = (TextView) itemView.findViewById(R.id.labelDescription);
            labelW1 = (TextView) itemView.findViewById(R.id.labelW1);
            labelW2 = (TextView) itemView.findViewById(R.id.labelW2);
            labelW3 = (TextView) itemView.findViewById(R.id.labelW3);
            labelW4 = (TextView) itemView.findViewById(R.id.labelW4);
            textQuantity = (KeyboardLessEditText) itemView.findViewById(R.id.textQuantity);
            textRet = (KeyboardLessEditText) itemView.findViewById(R.id.textRet);
            textQuantity.isTouchAllowed = true;
            textRet.isTouchAllowed = true;
            View.OnKeyListener keyListener = new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
                    {
                        listener.onKeyDown(getAdapterPosition());
                    }
                    else if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_DPAD_UP))
                    {
                        listener.onKeyUp(getAdapterPosition());
                    }
                    return false;
                }
            };
            textQuantity.setOnKeyListener(keyListener);
            textRet.setOnKeyListener(keyListener);
        }
    }

    public interface ProductListener
    {
        void onLastItemFocusDown();

        void onFocusGoneToPosition(int position);

        void onKeyUp(int position);
        void onKeyDown(int position);

        void onEditClicked(int position);
    }
}