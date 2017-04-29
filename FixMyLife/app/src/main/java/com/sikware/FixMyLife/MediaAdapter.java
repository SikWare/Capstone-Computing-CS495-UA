package com.sikware.FixMyLife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Ken on 4/28/2017.
 */

public class MediaAdapter extends BaseAdapter {

    private ArrayList<MediaItem> listData;

    private LayoutInflater layoutInflater;

    public MediaAdapter(Context context, ArrayList<MediaItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setListData(ArrayList<MediaItem> data){
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
            convertView = layoutInflater.inflate(R.layout.media_item_view, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.typeView = (TextView) convertView.findViewById(R.id.type);
            holder.platformView = (TextView) convertView.findViewById(R.id.platform);
            holder.genreView = (TextView) convertView.findViewById(R.id.genre);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).name);
        holder.typeView.setText(listData.get(position).type);
        holder.platformView.setText(listData.get(position).platform);
        holder.genreView.setText(listData.get(position).genre);

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView typeView;
        TextView platformView;
        TextView genreView;
    }


}
