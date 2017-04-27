package com.sikware.FixMyLife;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.sendbird.android.SendBird;

import java.util.UUID;

/**
 * Created by adam pluth on 4/1/17.
 */

class Global extends Application {
    // bob is just for testing purposes
    static User u = new User("bob", "sikwarefromthatguy@gmail.com", UUID.randomUUID(), 0);


    static PantryItem pantryItem = null;
    static MediaItem mediaItem = null;
    static NotesItem notesItem = null;
    static GoogleSignInAccount acct;
    static DBHelper mDbHelper = null;//

    // stuff for send bird
    //private static final String APP_ID = "9DA1B1F4-0BE6-4DA8-82C5-2E81DAB56F23"; // US-1 Demo
    private static final String APP_ID = "BDCD4D3E-2E3A-4CB7-A108-DB19B465B31F";
    public static final String VERSION = "3.0.30";
    public static DriveId mFileId = null;
    public static DriveFile mdriveFile = null;

    @Override
    public void onCreate() {
        super.onCreate();
        SendBird.init(APP_ID, getApplicationContext());
    }



    // this is the only place we should do this for security reasons
    //|||||||||||||
    //VVVVVVVVVVVVV
    public static User getUser() {
        return u;
    }
}
