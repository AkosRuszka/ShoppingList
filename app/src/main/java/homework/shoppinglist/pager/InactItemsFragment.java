package homework.shoppinglist.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import homework.shoppinglist.ListHolder;
import homework.shoppinglist.R;
import homework.shoppinglist.adapter.ShoppingItemAdapter;

/**
 * Created by Akos on 2017. 11. 24..
 */

public class InactItemsFragment extends Fragment {

    private ShoppingItemAdapter mAdapter;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mManager;

    private ListHolder listholder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof ListHolder) {
            listholder = (ListHolder)getActivity();
        } else {
            throw new RuntimeException("Az aktiviti nem implementálja a ListHolder inferfészt");
        }
        mAdapter = listholder.getShoppingList().inactive_items_adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = view.findViewById(R.id.recycle_view);
        mManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mManager);
        mRecycleView.setAdapter(mAdapter);
    }
}
