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

public class NotesAdapter extends BaseAdapter {

    private ArrayList<NotesItem> listData;

    private LayoutInflater layoutInflater;

    public NotesAdapter(Context context, ArrayList<NotesItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setListData(ArrayList<NotesItem> data){
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
            convertView = layoutInflater.inflate(R.layout.notes_item_view, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.noteName);
            holder.detailsView = (TextView) convertView.findViewById(R.id.notesDetails);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).name);
        holder.detailsView.setText(listData.get(position).details);

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView detailsView;

    }

}
