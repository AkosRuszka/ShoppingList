package homework.shoppinglist.adapter;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import homework.shoppinglist.R;
import homework.shoppinglist.model.ShoppingItem;
import homework.shoppinglist.model.ShoppingList;

/**
 * Created by Akos on 2017. 11. 21..
 */

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder> implements Serializable {

    private List<ShoppingItem> items;
    private ShoppingList list;

    public ShoppingItemAdapter(ShoppingList list) {
        this.list = list;
        items = new ArrayList<>();
    }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ShoppingViewHolder viewHolder = new ShoppingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ShoppingItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    @Override
    public void onBindViewHolder(ShoppingViewHolder holder, int position) {
        ShoppingItem item = items.get(position);
        holder.position = position;
        holder.nameTextView.setText(item.name);
        holder.priceTextView.setText("Ár: " + item.estimatedPrice + " Ft");
        holder.iconImageView.setImageResource(getImageResource(item.category));
        holder.isBoughtCheckBox.setChecked(item.isBought);
    }

    private @DrawableRes
    int getImageResource(ShoppingItem.Category category) {
        @DrawableRes int ret;
        switch (category) {
            case HOME:
                ret = R.drawable.home;
                break;
            case ELECTRIC:
                ret = R.drawable.flash;
                break;
            case FOOD:
                ret = R.drawable.groceries;
                break;
            default:
                ret = 0;
        }
        return ret;
    }

    public void deleteItemWithORM(ShoppingItem item) {
        item.delete();
        items.remove(item);
        notifyItemInserted(items.size() + 1);
        notifyDataSetChanged();
    }

    public void deleteItemthisList(ShoppingItem item) {
        items.remove(item);
        notifyItemInserted(items.size() + 1);
        notifyDataSetChanged();
    }

    public void removeAllItems() {
        for(ShoppingItem sitem : items)
            System.out.println(sitem.name);

        for(ShoppingItem sitem : items) {
            items.remove(sitem);
            System.out.println(sitem.delete() == true ? "Sikeres elemtörlés" : "Sikertelen elemtörlés");
        }
    }

    public class ShoppingViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView nameTextView;
        TextView priceTextView;
        CheckBox isBoughtCheckBox;
        ImageButton removeButton;

        int position;

        public ShoppingViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.ShoppingItemIconImageView);
            nameTextView = itemView.findViewById(R.id.ShoppingItemNameView);
            priceTextView = itemView.findViewById(R.id.ShoppingItemPriceView);
            isBoughtCheckBox = itemView.findViewById(R.id.ShoppingItemIsBoughtCheckBox);
            removeButton = itemView.findViewById(R.id.ShoppingItemRemoveButton);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Törtlés előtt");
                    for(ShoppingItem item : items) {
                        System.out.println(item.name);
                    }
                    deleteItemWithORM(items.get(position));
                    System.out.println("Törtlés után");
                    for(ShoppingItem item : items) {
                        System.out.println(item.name);
                    }
                }
            });
            isBoughtCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean checked = isBoughtCheckBox.isChecked();
                    ShoppingItem item = items.get(position);
                    item.delete();
                    /* Átrakjuk egyik listából a másikba */
                    item.isBought = checked;

                    if(item.isBought) {
                        list.inactive_items_adapter.addItem(item);
                        list.active_items_adapter.deleteItemthisList(item);
                    } else {
                        list.active_items_adapter.addItem(item);
                        list.inactive_items_adapter.deleteItemthisList(item);
                    }
                    item.save();
                }
            });
        }
    }
}
