package com.sikware.FixMyLife;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sikware.FixMyLife.FeedReaderContract.FeedEntry;

/**
 * Created by Adam Pluth on 4/21/2017.
 */

public class PantryCursorAdapter extends ResourceCursorAdapter {
    public PantryCursorAdapter(Context context, int i, Cursor c) {
        super(context, i,c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.pantry_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //todo make this right
        TextView name = (TextView) view.findViewById(R.id.pantryName);
        TextView quantity = (TextView) view.findViewById(R.id.pantryQty);
        TextView unit = (TextView) view.findViewById(R.id.pantryUnit);
        TextView type = (TextView) view.findViewById(R.id.pantryType);
        // Extract properties from cursor
        String nameText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
        String qunatityText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_QUANTITY));
        String unitText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_UNIT));
        String typeText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_TYPE));
        // Populate fields with extracted properties
        name.setText(nameText);
        quantity.setText(String.valueOf(qunatityText));
        unit.setText(unitText);
        type.setText(typeText);
    }
}
