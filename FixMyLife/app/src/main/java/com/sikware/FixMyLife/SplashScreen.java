package com.sikware.FixMyLife;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Global.mDbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = Global.mDbHelper.getReadableDatabase();


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
