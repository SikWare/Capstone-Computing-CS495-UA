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

public class TasksAdapter extends BaseAdapter {

    private ArrayList<TaskItem> listData;

    private LayoutInflater layoutInflater;

    public TasksAdapter(Context context, ArrayList<TaskItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setListData(ArrayList<TaskItem> data){
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
            convertView = layoutInflater.inflate(R.layout.tasks_item_view, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.taskName);
            holder.detailsView = (TextView) convertView.findViewById(R.id.taskDetails);
            holder.dueDateView = (TextView) convertView.findViewById(R.id.taskDueDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).name);
        holder.detailsView.setText(listData.get(position).details);
        holder.dueDateView.setText(listData.get(position).dueDate);

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView detailsView;
        TextView dueDateView;

    }

}
