package com.aqui;

/**
 * Created by Jorge on 19/12/13.
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class itemAdapter extends BaseAdapter {

    private Context context;
    private List <item> items;

    public itemAdapter(Context context, List items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create a new view into the list.
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item, parent, false);

        // Set data into the view.
        ImageView ivItem = (ImageView) rowView.findViewById(R.id.imageView);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.textView);


        tvTitle.setText(this.items.get(position).getPlace());
        ivItem.setImageResource(this.items.get(position).getLogo());

        return rowView;
    }

}