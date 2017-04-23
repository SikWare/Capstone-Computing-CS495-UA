package com.sikware.FixMyLife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.RadioButton;

import com.sikware.FixMyLife.FeedReaderContract.FeedEntry;

/**
 * Created by Adam Pluth on 4/19/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, FeedEntry.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedEntry.SQL_CREATE_MEDIA_TABLE_H);
        db.execSQL(FeedEntry.SQL_CREATE_MEDIA_TABLE_W);
        db.execSQL(FeedEntry.SQL_CREATE_PANTRY_TABLE_H);
        db.execSQL(FeedEntry.SQL_CREATE_PANTRY_TABLE_W);
        db.execSQL(FeedEntry.SQL_CREATE_NOTES_TABLE_H);
        db.execSQL(FeedEntry.SQL_CREATE_NOTES_TABLE_W);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedEntry.SQL_DELETE_MEDIA_TABLE_H);
        db.execSQL(FeedEntry.SQL_DELETE_MEDIA_TABLE_W);
        db.execSQL(FeedEntry.SQL_DELETE_PANTRY_TABLE_H);
        db.execSQL(FeedEntry.SQL_DELETE_PANTRY_TABLE_W);
        db.execSQL(FeedEntry.SQL_DELETE_NOTES_TABLE_H);
        db.execSQL(FeedEntry.SQL_DELETE_NOTES_TABLE_W);
        onCreate(db);
    }

    public Cursor queryAll(){
        return null;
    }

    public void insertMediaItem(boolean H, SQLiteDatabase db){
        //make Query
        ContentValues values = new ContentValues();
        values.put(FeedEntry._ID, Global.mediaItem.id.toString());
        values.put(FeedEntry.COLUMN_NAME, Global.mediaItem.name);
        values.put(FeedEntry.COLUMN_OWNER_ID, Global.getUser().groupID.toString());
        values.put(FeedEntry.COLUMN_TYPE, Global.mediaItem.type);
        values.put(FeedEntry.COLUMN_PLATFORM, Global.mediaItem.platform);
        values.put(FeedEntry.COLUMN_GENRE, Global.mediaItem.genre);
        values.put(FeedEntry.COLUMN_BOUGHT, Global.mediaItem.isBought);


        //after creating item add to db
        long newRowId =
                // bear with me
                    H
                        ?
                        db.insert(FeedEntry.TABLE_NAME_MEDIA_HAVE, null, values)
                        :
                        db.insert(FeedEntry.TABLE_NAME_MEDIA_WANT, null, values);// the null here is default for column value
        Log.d("item","NewRowId: " + newRowId);

    }

}
