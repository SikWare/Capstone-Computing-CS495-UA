package com.sikware.FixMyLife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Ken on 4/28/2017.
 */

public class PantryAdapter extends BaseAdapter {

    private ArrayList<PantryItem> listData;

    private LayoutInflater layoutInflater;

    public PantryAdapter(Context context, ArrayList<PantryItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setListData(ArrayList<PantryItem> data){
        listData = data;
    }

    @Override
    public int getCount() {
        return listData.size();
    }


    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.pantry_item_view, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.pantryName);
            holder.typeView = (TextView) convertView.findViewById(R.id.pantryType);
            holder.unitView = (TextView) convertView.findViewById(R.id.pantryUnit);
            holder.quantityView = (TextView) convertView.findViewById(R.id.pantryQty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).name);
        holder.typeView.setText(listData.get(position).type);
        holder.unitView.setText(listData.get(position).unit);
        holder.quantityView.setText(listData.get(position).quantity);

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView typeView;
        TextView quantityView;
        TextView unitView;
    }


}
