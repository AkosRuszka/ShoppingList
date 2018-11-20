package homework.shoppinglist.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.io.Serializable;
import java.util.Date;

import homework.shoppinglist.adapter.ShoppingItemAdapter;

/**
 * Created by Akos on 2017. 11. 24..
 */

public class ShoppingList extends SugarRecord implements Serializable {
    @Unique
    public String listName;
    public Date date;

    public ShoppingList() {
        active_items_adapter = new ShoppingItemAdapter(this);
        inactive_items_adapter = new ShoppingItemAdapter(this);
    }

    @Ignore
    public ShoppingItemAdapter active_items_adapter;

    @Ignore
    public ShoppingItemAdapter inactive_items_adapter;

    public void deleteAllItem() {
        active_items_adapter.removeAllItems();
        inactive_items_adapter.removeAllItems();
    }
}
