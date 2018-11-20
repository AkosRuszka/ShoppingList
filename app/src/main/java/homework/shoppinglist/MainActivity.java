package homework.shoppinglist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import homework.shoppinglist.adapter.ShoppingListAdapter;
import homework.shoppinglist.model.ShoppingItem;
import homework.shoppinglist.model.ShoppingList;
import homework.shoppinglist.newdialog.NewShoppingListDialogFragment;

public class MainActivity extends AppCompatActivity implements NewShoppingListDialogFragment.INewShoppingListDialogListener {

    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    public String v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFab();
        initRecyclerView();
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewShoppingListDialogFragment().show(getSupportFragmentManager(), NewShoppingListDialogFragment.TAG);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItemsInBackground();
    }

    @Override
    public void onShoppingListCreated(ShoppingList newItem) {
        adapter.addItem(newItem);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadItemsInBackground();
        adapter = new ShoppingListAdapter(new OnShoppingListSelectedListener() {
            @Override
            public void onShoppingListSelected(ShoppingList list) {
                Intent showListIntent = new Intent();
                showListIntent.setClass(MainActivity.this, ItemsActivity.class);

                /* Kattintáskor elküldjük Intent-el a ShoppingList-et is. */
                showListIntent.putExtra(ItemsActivity.ADAPTER_REF,list);
                startActivity(showListIntent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<ShoppingList>>() {

            @Override
            protected List<ShoppingList> doInBackground(Void... voids) {
                List<ShoppingList> lista = ShoppingList.listAll(ShoppingList.class);
                List<ShoppingItem> itemlista = ShoppingItem.listAll(ShoppingItem.class);

                /* Az itemlistákat elrakjuk a listák között */
                for(ShoppingList actlist : lista) {
                     for(ShoppingItem actItem : itemlista) {
                         if(actItem.listname.equals(actlist.listName)) {
                             if(actItem.isBought) {
                                 actlist.inactive_items_adapter.addItem(actItem);
                             } else {
                                 actlist.active_items_adapter.addItem(actItem);
                             }
                         }
                     }
                }

                return lista;
            }

            @Override
            protected void onPostExecute(List<ShoppingList> shoppingList) {
                super.onPostExecute(shoppingList);
                adapter.update(shoppingList);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

