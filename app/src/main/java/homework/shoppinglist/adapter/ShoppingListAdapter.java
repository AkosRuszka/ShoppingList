package homework.shoppinglist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import homework.shoppinglist.OnShoppingListSelectedListener;
import homework.shoppinglist.R;
import homework.shoppinglist.model.ShoppingList;

/**
 * Created by Akos on 2017. 11. 21..
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder>  {

    private final List<ShoppingList> items;
    private OnShoppingListSelectedListener listener;

    public ShoppingListAdapter(OnShoppingListSelectedListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_list, parent, false);
        ShoppingViewHolder viewHolder = new ShoppingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ShoppingList list) {
        items.add(list);
        notifyItemInserted(items.size() - 1);
    }

    public void update(List<ShoppingList> shoppingList) {
        items.clear();
        items.addAll(shoppingList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ShoppingViewHolder holder, int position) {
        ShoppingList item = items.get(position);
        holder.position = position;
        holder.listaneve.setText(item.listName);
        holder.aktivelemszam.setText(String.valueOf(item.active_items_adapter.getItemCount()));
        holder.listadatuma.setText(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN).format(item.date));
        holder.listImageButton.setImageResource(R.drawable.list);
    }

    public void deleteList(ShoppingList list) {
        items.remove(list);
        list.delete();
        notifyItemInserted(items.size() + 1);
        notifyDataSetChanged();
    }


    public class ShoppingViewHolder extends RecyclerView.ViewHolder {
        TextView listaneve;
        TextView aktivelemszam;
        TextView listadatuma;
        ImageButton removeButton;
        ImageButton listImageButton;

        int position;

        public ShoppingViewHolder(View itemView) {
            super(itemView);
            listaneve = itemView.findViewById(R.id.ListNameText);
            aktivelemszam = itemView.findViewById(R.id.ListActiveElement);
            listadatuma = itemView.findViewById(R.id.ListActivatedDate);
            listImageButton = itemView.findViewById(R.id.ShoppingItemIconImageView);
            listImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        listener.onShoppingListSelected(items.get(position));
                    }
                }
            });
            removeButton = itemView.findViewById(R.id.ShoppingItemRemoveButton);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.get(position).deleteAllItem();
                    deleteList(items.get(position));
                }
            });
        }
    }
}
