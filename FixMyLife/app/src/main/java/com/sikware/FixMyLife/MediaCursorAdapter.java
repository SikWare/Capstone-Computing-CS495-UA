package com.sikware.FixMyLife;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sikware.FixMyLife.FeedReaderContract.FeedEntry;

/**
 * Created by Adam Pluth on 4/21/2017.
 */

public class MediaCursorAdapter extends ResourceCursorAdapter {
    public MediaCursorAdapter(Context context, int i,Cursor c) {
        super(context, i,c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.media_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView platform = (TextView) view.findViewById(R.id.platform);
        TextView genre = (TextView) view.findViewById(R.id.genre);
        // Extract properties from cursor
        String nameText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
        String platformText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_PLATFORM));
        String genreText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_GENRE));
        // Populate fields with extracted properties
        name.setText(nameText);
        platform.setText(String.valueOf(platformText));
        genre.setText(genreText);
    }
}
