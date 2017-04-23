package com.sikware.FixMyLife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


}
