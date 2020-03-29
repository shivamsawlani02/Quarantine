package com.example.quarantine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class ItemsRVAdapter extends RecyclerView.Adapter<ItemsRVAdapter.ItemsVH> implements Filterable {

    ArrayList<Items> itemlist;
    ArrayList<Items> itemlistAll;
    Context context;

    public ItemsRVAdapter(ArrayList<Items> itemlist,Context context) {
        this.itemlist=itemlist;
        this.context=context;
        this.itemlistAll=new ArrayList<>(itemlist);

    }

    @NonNull
    @Override
    public ItemsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemsVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ItemsRVAdapter.ItemsVH holder, int position) {
        holder.populateItem(itemlist.get(position));
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<Items> filteredList=new ArrayList<>();
                if(charSequence.toString().isEmpty())
                {
                    filteredList.addAll(itemlistAll);
                }
                else
                {
                    for(Items items : itemlistAll)
                    {
                        if(items.getName().toLowerCase().contains(charSequence.toString().toLowerCase().trim()))
                        {
                            filteredList.add(items);
                        }
                    }
                }
                FilterResults results=new FilterResults();
                results.values=filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemlist.clear();
                itemlist.addAll((Collection<? extends Items>) filterResults.values);
                notifyDataSetChanged();
            }
        };

    class ItemsVH extends RecyclerView.ViewHolder
    {
        private TextView name,quantity;
        public ItemsVH(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_product_name);
            quantity=itemView.findViewById(R.id.tv_quantity);
        }
        void populateItem(final Items items)
        {
            name.setText(items.getName());
            quantity.setText(items.getQuantity());
        }
    }
}
