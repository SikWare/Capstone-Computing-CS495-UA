package com.sikware.FixMyLife;

import android.provider.BaseColumns;

/**
 * Created by Adam Pluth on 4/19/17.
 */

public final class FeedReaderContract {

    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {

        public static final String DATABASE_NAME = "FixMyLife.db";
        public static final String TABLE_NAME_MEDIA = "media";
        public static final String TABLE_NAME_PANTRY = "pantry";
        public static final String TABLE_NAME_NOTES = "notes";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_OWNER_ID = "owner_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_PLATFORM = "platform";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_DETAILS = "details";
        public static final String COLUMN_DUEDATE = "due date";
        public static final String COLUMN_POINTVALUE = "pointValue";
        public static final String COLUMN_ASSIGNTO = "assignTo";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_APPROVED = "approved";
        public static final String COLUMN_BOUGHT = "bought";
        public static final String COLUMN_PRIORITY = "priority";

        public static final String SQL_CREATE_MEDIA_TABLE =
                "CREATE TABLE " + TABLE_NAME_MEDIA + " (" +
                        _ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_OWNER_ID + " TEXT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_PLATFORM + " TEXT," +
                        COLUMN_GENRE + " TEXT," +
                        COLUMN_BOUGHT + " INTEGER" +
                        ")";

        public static final String SQL_CREATE_PANTRY_TABLE =
                "CREATE TABLE " + TABLE_NAME_PANTRY + " (" +
                        _ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_OWNER_ID + " TEXT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_QUANTITY + " TEXT," +
                        COLUMN_UNIT + " TEXT," +
                        COLUMN_BOUGHT + " INTEGER," +
                        COLUMN_PRIORITY + " TEXT" +
                        ")";

        public static final String SQL_CREATE_NOTES_TABLE =
                "CREATE TABLE " + TABLE_NAME_NOTES + " (" +
                        _ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_OWNER_ID + " TEXT," +
                        COLUMN_DETAILS + " TEXT," +
                        COLUMN_DUEDATE + " TEXT," +
                        COLUMN_POINTVALUE + " TEXT," +
                        COLUMN_ASSIGNTO + " TEXT," +
                        COLUMN_COMPLETED + " TEXT," +
                        COLUMN_APPROVED + " TEXT" +
                        ")";

        public static final String SQL_DELETE_PANTRY_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME_PANTRY;

        public static final String SQL_DELETE_NOTES_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME_NOTES;

        public static final String SQL_DELETE_MEDIA_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME_MEDIA;



    }




}
