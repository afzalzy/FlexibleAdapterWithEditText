package com.example.flexiadaptertest.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flexiadaptertest.MainActivity;
import com.example.flexiadaptertest.R;
import com.example.flexiadaptertest.adapter.PayMethodAdapter;
import com.example.flexiadaptertest.adapter.StickyHeaderAdapterAdapter;
import com.example.flexiadaptertest.adapter.holder.OrderHolder;
import com.example.flexiadaptertest.databinding.FragmentOrderBinding;
import com.example.flexiadaptertest.listener.FilterOptionSelectListener;
import com.example.flexiadaptertest.model.CategoryParseModel;
import com.example.flexiadaptertest.model.OrderProductParseModel;
import com.example.flexiadaptertest.model.OrderProductWeekDayParseModel;
import com.example.flexiadaptertest.util.GlobalMethods;
import com.example.flexiadaptertest.util.SmoothScrollLinearLayoutManager;
import com.example.flexiadaptertest.util.TopSnappedSmoothScroller;

import java.util.ArrayList;

import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.SelectableAdapter;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by afzal on 31/Jan/2018.
 */

public class OrderFragment extends Fragment
{
    private FragmentOrderBinding binding;
    private StickyHeaderAdapterAdapter adapter;
    private int selectedItem = 1;
    private SmoothScrollLinearLayoutManager layoutManager;
    private boolean isScrollingBecauseOfSelection = false;
    private int scrollToPosition = 0;
    private PayMethodAdapter adapterDay;
    private Context context;
    private ArrayList<CategoryParseModel> listOfCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        listOfCategory = new ArrayList<>();
        getCategory();
        initializeComponents();

    }

    private void getCategory()
    {
        for(int i = 0; i < 5; i++)
        {
            CategoryParseModel categoryParseModel = new CategoryParseModel();
            categoryParseModel.setCatName("category " + i+"");
            categoryParseModel.setCatNo(i);
            ArrayList<OrderProductParseModel> listOfOrders = new ArrayList<>();
            for(int j = 0; j < 6; j++)
            {
                OrderProductParseModel orderProductParseModel = new OrderProductParseModel();
                orderProductParseModel.setDescription(i+"_"+j);
                orderProductParseModel.setId(j);
                ArrayList<OrderProductWeekDayParseModel> list =new ArrayList<>();
                for(int k = 0; k < 3; k++)
                {
                    OrderProductWeekDayParseModel orderProductWeekDayParseModel = new OrderProductWeekDayParseModel();
                    orderProductWeekDayParseModel.setQuantity(3+"");
                    orderProductWeekDayParseModel.setReturns(3+"");
                    list.add(orderProductWeekDayParseModel);
                }
                orderProductParseModel.setListOfWeekday(list);
                listOfOrders.add(orderProductParseModel);
            }

            categoryParseModel.setListOfProducts(listOfOrders);
            listOfCategory.add(categoryParseModel);
        }

    }

    //        boolean isFirstTime = true;
    protected void initializeComponents()
    {
        TopSnappedSmoothScroller.MILLISECONDS_PER_INCH = 35f;

        bindEvents();
    }

    protected void bindEvents()
    {

        getOrders();
    }

    private void getOrders()
    {
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(adapter != null && isVisibleToUser)
        {
            selectItemWithDelay(selectedItem);
//            else
//            {
//                saleRecyclerAdapter.notifyDataSetChanged();
//                binding.autoCompleteSearch.requestFocus();
//            }
//            Log.e("fragment visible", saleRecyclerAdapter.getSelectedItemIndex()+"");
        }
        else if(adapter != null)
        {
            if(adapter.getSelectedItemCount() == 1)
            {
                selectedItem = adapter.getSelectedPositions().get(0);
            }
        }
    }

    private void selectItemWithDelay(final int position)
    {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.clearSelection();
                adapter.addSelection(position);
                adapter.notifyDataSetChanged();
            }
        }, 1);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuFilter:
                openFilter(listOfCategory);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFilter(final ArrayList<CategoryParseModel> listOfCategory)
    {
        ((MainActivity) getActivity()).openFilter(listOfCategory, new FilterOptionSelectListener()
        {
            @Override
            public void onFilterOptionClicked(int position, CategoryParseModel categoryModel)
            {
                ((MainActivity) getActivity()).closeFilter();
                goToPosition(position);
            }
        });
    }

    private void goToPosition(final int position)
    {
        Log.e("position ", position + " ");
        int totalSize = adapter.getSectionItemPositions(adapter.getHeaderItems().get(position)).size();

        Log.e("totalSize ", totalSize + " ");
        //int headers=0;
        int newNumber=0;
        if (totalSize > 0)
        {
            newNumber = adapter.getSectionItemPositions(adapter.getHeaderItems().get(position)).get(0);
            Log.e("newNumber ", newNumber + " ");
//            mRecyclerView.smoothScrollToPosition(Math.max(0, position - headers));
            int positionFinal;

            eu.davidea.flexibleadapter.utils.Log.d("scroll to position=%s item=%s", position, adapter.getItem(position));
           int headers = adapter.areHeadersSticky() ? 1 : 0;
            int shouldSelectPosition = Math.max(0, newNumber - headers);

            if(binding.recyclerView.computeVerticalScrollOffset() == 0 && newNumber <= 1)
            {
                adapter.clearSelection();
                adapter.addSelection(newNumber);
                adapter.notifyDataSetChanged();
            }
            binding.recyclerView.smoothScrollToPosition(Math.max(0, newNumber - headers));

            isScrollingBecauseOfSelection = true;
            scrollToPosition = newNumber;
//            Toast.makeText(context, scrollToPosition+"", Toast.LENGTH_SHORT).show();

        }
    }


    private void loadData()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        adapterDay = new PayMethodAdapter(context, arrayList, R.layout.row_weekday);
        binding.spinnerDay.setAdapter(adapterDay);
        setAdapter(0, false);
    }

    private void setAdapter(int dayNo, boolean isChangedManually)
    {

        boolean isFirstTimeLoading = false;
        if(adapter == null)
        {
            isFirstTimeLoading = true;
        }

        adapter = new StickyHeaderAdapterAdapter(listOfCategory, context, new OrderHolder.ProductListener()
        {
            @Override
            public void onLastItemFocusDown()
            {

            }

            @Override
            public void onFocusGoneToPosition(int position)
            {
                Log.e("on2ndItemFocusUp ", "on2ndItemFocusUp " + position);
            }

            @Override
            public void onKeyUp(int position)
            {
                if(layoutManager.findFirstCompletelyVisibleItemPosition() > 1)
                {
                    binding.recyclerView.setPadding(0, (int)GlobalMethods.convertDpToPixel(48), 0, 0);
                }
                else
                {
                    binding.recyclerView.setPadding(0, (int)GlobalMethods.convertDpToPixel(0), 0, 0);
                }
//                ((LinearLayoutManager)binding.recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, (int)GlobalMethods.convertDpToPixel(48));
            }

            @Override
            public void onKeyDown(int position)
            {
                //int pos= adapter.getSelectedItemCount();

                //SmoothScrollLinearLayoutManager manager = (SmoothScrollLinearLayoutManager) adapter.getFlexibleLayoutManager();

                //int pos = manager.findLastVisibleItemPosition();
                //Toast.makeText(activity, ""+pos, Toast.LENGTH_SHORT).show();

                binding.recyclerView.setPadding(0, (int)GlobalMethods.convertDpToPixel(0), 0, 0);


//                ((LinearLayoutManager)binding.recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, (int)GlobalMethods.convertDpToPixel(48));

            }

            @Override
            public void onEditClicked(int position)
            {

            }
        });
        adapter.setMode(SelectableAdapter.Mode.SINGLE);
        layoutManager = new SmoothScrollLinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
//        binding.recyclerView.setPreserveFocusAfterLayout(false);
//        binding.recyclerView.addItemDecoration(new FlexibleItemDecoration(getActivity())
//                .addItemViewType(R.layout.row_order_header, 8, 8, 8, 8)
//                .addItemViewType(R.layout.row_order, 0, 8, 0, 8).withEdge(true)
//             );
        binding.recyclerView.setAdapter(adapter);
        adapter.setDisplayHeadersAtStartUp(true).setStickyHeaders(true);
//        binding.recyclerView.setHasFixedSize(true);
        FastScroller fastScroller = getView().findViewById(R.id.fastScroller);
//        fastScroller.addOnScrollStateChangeListener((MainActivity) getActivity());
        adapter.setFastScroller(fastScroller);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == SCROLL_STATE_IDLE)
                {
                    Toast.makeText(context, scrollToPosition+"", Toast.LENGTH_SHORT).show();
                    if(isScrollingBecauseOfSelection)
                    {
////                            final int newNumber = adapter.getSectionItemPositions(adapter.getItem(layoutManager.findFirstCompletelyVisibleItemPosition()).getHeader()).get(0);
////                            int numbe = adapter.getSelectedPositions().get(0);
                        adapter.clearSelection();
                        adapter.addSelection(scrollToPosition);
                        adapter.notifyDataSetChanged();
//                        int header = adapter.areHeadersSticky() ? 1 : 0;
//                        binding.recyclerView.smoothScrollToPosition(Math.max(0, scrollToPosition - header));
                        isScrollingBecauseOfSelection = false;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
//                Log.e("dx", dx + "");
//                Log.e("dy", dy + "");
//                Log.e("first visible c", layoutManager.findFirstCompletelyVisibleItemPosition()+"");
//                Log.e("first visible ", layoutManager.findFirstVisibleItemPosition()+"");
//                Log.e("last visible c", layoutManager.findLastCompletelyVisibleItemPosition()+"");
//                Log.e("last visible ", layoutManager.findLastVisibleItemPosition()+"");
//                if(layoutManager.findFirstCompletelyVisibleItemPosition() == 0)
                {
                    binding.recyclerView.setPadding(0, 0, 0, 0);
                }
            }
        });

        if(isFirstTimeLoading || isChangedManually)
        {
            selectItemWithDelay(selectedItem);
        }
    }
}