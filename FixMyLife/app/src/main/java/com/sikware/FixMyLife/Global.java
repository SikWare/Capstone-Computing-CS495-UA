package com.sikware.FixMyLife;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.sendbird.android.SendBird;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by adam pluth on 4/1/17.
 */

class Global extends Application {
    // bob is just for testing purposes
    static User u = new User("bob", "sikwarefromthatguy@gmail.com", UUID.randomUUID(), 0);

    //items
    static PantryItem pantryItem = null;
    static MediaItem mediaItem = null;
    static NotesItem notesItem = null;
    static GoogleSignInAccount acct;
    static DBHelper mDbHelper = null;

    //itemArrays
    static ArrayList<MediaItem> mediaHaveArray = new ArrayList<MediaItem>();
    static ArrayList<MediaItem> mediaWantArray = new ArrayList<MediaItem>();
    static ArrayList<PantryItem> pantryHaveArray = new ArrayList<PantryItem>();
    static ArrayList<PantryItem> pantryWantArray = new ArrayList<PantryItem>();
    static ArrayList<NotesItem> notesArray = new ArrayList<NotesItem>();

    //strings for mySQL
    static final String INSERT_PHP = "insertItem.php";
    static final String SELECT_PHP = "selectItem.php";


    static final String MEDIA_TABLE = "?table=media";


    // stuff for send bird
    private static final String APP_ID = "BDCD4D3E-2E3A-4CB7-A108-DB19B465B31F";
    public static final String VERSION = "3.0.30";

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
