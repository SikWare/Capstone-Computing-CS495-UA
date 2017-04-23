package com.sikware.FixMyLife;

import android.provider.BaseColumns;

/**
 * Created by Adam Pluth on 4/19/17.
 */

public final class FeedReaderContract {

    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {

        public static final String DATABASE_NAME = "FixMyLife.db";
        public static final String TABLE_NAME_MEDIA_HAVE = "mediaH";
        public static final String TABLE_NAME_MEDIA_WANT = "mediaW";
        public static final String TABLE_NAME_PANTRY_HAVE = "pantryH";
        public static final String TABLE_NAME_PANTRY_WANT = "pantryW";
        public static final String TABLE_NAME_NOTES_HAVE = "notesH";
        public static final String TABLE_NAME_NOTES_WANT = "notesW";
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

        public static final String SQL_CREATE_MEDIA_TABLE_H =
                "CREATE TABLE " + TABLE_NAME_MEDIA_HAVE + " (" +
                        _ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," + // 1
                        COLUMN_OWNER_ID + " TEXT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_PLATFORM + " TEXT," + // 2
                        COLUMN_GENRE + " TEXT," + // 3
                        COLUMN_BOUGHT + " TEXT" +
                        ")";
        public static final String SQL_CREATE_MEDIA_TABLE_W =
                "CREATE TABLE " + TABLE_NAME_MEDIA_WANT + " (" +
                        _ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," + // 1
                        COLUMN_OWNER_ID + " TEXT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_PLATFORM + " TEXT," + // 2
                        COLUMN_GENRE + " TEXT," + // 3
                        COLUMN_BOUGHT + " TEXT" +
                        ")";

        public static final String SQL_CREATE_PANTRY_TABLE_H =
                "CREATE TABLE " + TABLE_NAME_PANTRY_HAVE + " (" +
                        _ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_OWNER_ID + " TEXT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_QUANTITY + " TEXT," +
                        COLUMN_UNIT + " TEXT," +
                        COLUMN_BOUGHT + " INTEGER," +
                        COLUMN_PRIORITY + " TEXT" +
                        ")";
        public static final String SQL_CREATE_PANTRY_TABLE_W =
                "CREATE TABLE " + TABLE_NAME_PANTRY_WANT + " (" +
                        _ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_OWNER_ID + " TEXT," +
                        COLUMN_TYPE + " TEXT," +
                        COLUMN_QUANTITY + " TEXT," +
                        COLUMN_UNIT + " TEXT," +
                        COLUMN_BOUGHT + " INTEGER," +
                        COLUMN_PRIORITY + " TEXT" +
                        ")";

        public static final String SQL_CREATE_NOTES_TABLE_H =
                "CREATE TABLE " + TABLE_NAME_NOTES_HAVE + " (" +
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
        public static final String SQL_CREATE_NOTES_TABLE_W =
                "CREATE TABLE " + TABLE_NAME_NOTES_WANT + " (" +
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

        public static final String SQL_DELETE_PANTRY_TABLE_H =
                "DROP TABLE IF EXISTS " + TABLE_NAME_PANTRY_HAVE;
        public static final String SQL_DELETE_PANTRY_TABLE_W =
                "DROP TABLE IF EXISTS " + TABLE_NAME_PANTRY_WANT;

        public static final String SQL_DELETE_NOTES_TABLE_H =
                "DROP TABLE IF EXISTS " + TABLE_NAME_NOTES_HAVE;
        public static final String SQL_DELETE_NOTES_TABLE_W =
                "DROP TABLE IF EXISTS " + TABLE_NAME_NOTES_WANT;

        public static final String SQL_DELETE_MEDIA_TABLE_H =
                "DROP TABLE IF EXISTS " + TABLE_NAME_MEDIA_HAVE;
        public static final String SQL_DELETE_MEDIA_TABLE_W =
                "DROP TABLE IF EXISTS " + TABLE_NAME_MEDIA_WANT;

        public static final String SQL_QUERY_ALL_MEADIA_HAVE = "SELECT * from " + TABLE_NAME_MEDIA_HAVE;
        public static final String SQL_QUERY_ALL_MEDIA_WANT = "SELECT * from " + TABLE_NAME_MEDIA_WANT;
        public static final String SQL_QUERY_ALL_PANTRY_HAVE = "SELECT * from " + TABLE_NAME_PANTRY_HAVE;
        public static final String SQL_QUERY_ALL_PANTRY_WANT = "SELECT * from " + TABLE_NAME_PANTRY_WANT;
        public static final String SQL_QUERY_ALL__NOTES_HAVE = "SELECT * from " + TABLE_NAME_NOTES_HAVE;
        public static final String SQL_QUERY_ALL_NOTES_WANT = "SELECT * from " + TABLE_NAME_NOTES_WANT;

    }




}
