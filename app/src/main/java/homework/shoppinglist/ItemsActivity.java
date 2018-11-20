package homework.shoppinglist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import homework.shoppinglist.model.ShoppingItem;
import homework.shoppinglist.model.ShoppingList;
import homework.shoppinglist.newdialog.NewShoppingItemDialogFragment;
import homework.shoppinglist.pager.ItemsPagerAdapter;

public class ItemsActivity extends AppCompatActivity implements ListHolder, NewShoppingItemDialogFragment.INewShoppingItemDialogListener{

    private ShoppingList list = null;

    public static final String ADAPTER_REF = "adapter_reference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);

        /* Intent-ből kiszedjük azt a ShoppingList-et amire történt a kattintás. */
        list = (ShoppingList)getIntent().getSerializableExtra(ADAPTER_REF);

        getSupportActionBar().setTitle(list.listName + " lista elemei");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFab();
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewShoppingItemDialogFragment().show(getSupportFragmentManager(), NewShoppingItemDialogFragment.TAG);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewPager mainViewPager = (ViewPager)findViewById(R.id.mainViewPager);
        ItemsPagerAdapter itemsPagerAdapter = new ItemsPagerAdapter(getSupportFragmentManager(), this);
        mainViewPager.setAdapter(itemsPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ShoppingList getShoppingList() {
        return list;
    }

    @Override
    public void onShoppingItemCreated(ShoppingItem newItem) {
        newItem.listname = list.listName;
        newItem.save();
        list.active_items_adapter.addItem(newItem);
    }
}
