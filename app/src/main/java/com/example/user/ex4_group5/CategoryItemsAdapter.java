package com.example.user.ex4_group5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 10/24/2015.
 */
public class CategoryItemsAdapter extends BaseAdapter {
    List<ItemDescription> items;
    Context context;
    public CategoryItemsAdapter(Context context,List<ItemDescription> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemDescription getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
        }
        ItemDescription item = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.item_name);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView desc = (TextView) convertView.findViewById(R.id.item_desc);

        name.setText(item.getItemName());
        price.setText(item.getPrice() + "");
        desc.setText(item.getDesc());
        return convertView;
    }
}
