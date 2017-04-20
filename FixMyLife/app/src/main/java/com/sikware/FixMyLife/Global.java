package com.sikware.FixMyLife;

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.UUID;

/**
 * Created by adam pluth on 4/1/17.
 */

class Global extends Application {
    // bob is just for testing purposes
    static User u = new User("bob", "sikwarefromthatguy@gmail.com", UUID.randomUUID(), 0);


    static PantryItem pantryItem = null;
    static MediaItem mediaItem = null;
    static GoogleSignInAccount acct;
    static DBHelper mDbHelper;

    // this is the only place we should do this for security reasons
    //|||||||||||||
    //VVVVVVVVVVVVV
    public static User getUser() {
        return u;
    }
}
