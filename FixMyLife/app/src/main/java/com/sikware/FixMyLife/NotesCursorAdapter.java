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

public class NotesCursorAdapter extends ResourceCursorAdapter {
    public NotesCursorAdapter(Context context, int i, Cursor c) {
        super(context, i,c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.notes_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //todo make this right
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView dueDate = (TextView) view.findViewById(R.id.notesDueDate);
        TextView pointVal = (TextView) view.findViewById(R.id.notesPointValue);
        TextView details = (TextView) view.findViewById(R.id.notesDetails);
        // Extract properties from cursor
        String nameText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME));
        String dateText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DUEDATE));
        String pointText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_POINTVALUE));
        String detailsText = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_DETAILS));
        // Populate fields with extracted properties
        name.setText(nameText);
        dueDate.setText(String.valueOf(dateText));
        pointVal.setText(pointText);
        details.setText(detailsText);
    }
}
