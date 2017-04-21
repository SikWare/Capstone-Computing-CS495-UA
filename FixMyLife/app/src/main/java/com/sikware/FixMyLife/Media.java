package com.sikware.FixMyLife;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.sikware.FixMyLife.Global.mDbHelper;
import com.sikware.FixMyLife.FeedReaderContract.FeedEntry;

import java.util.ArrayList;
import java.util.UUID;

public class Media extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button addMediaBtn;
    ListView mediaHave, mediaWant;
    SQLiteDatabase db;
    ArrayList<String> resultsH = new ArrayList<String>();
    ArrayList<String> resultsW = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addMediaBtn = (Button)findViewById(R.id.addMediaBtn);
        addMediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        db = mDbHelper.getWritableDatabase();

        loadLists();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.media, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void addItem(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_media_item_layout,null)).setPositiveButton(R.string.addNew, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //send data back from dialog

                // Gets the data repository in write mode
                ContentValues values = new ContentValues();

                AlertDialog a = (AlertDialog) dialog;
//                Context context = getApplicationContext();
                EditText Ename = (EditText)a.findViewById(R.id.mediaNameItem);
                String name = Ename.getText().toString();
                String type = ((EditText)a.findViewById(R.id.mediaTypeItem)).getText().toString();
                String platform = ((EditText)a.findViewById(R.id.mediaPlatformItem)).getText().toString();
                String genre = ((EditText)a.findViewById(R.id.mediaGenreItem)).getText().toString();
                boolean bought = ((RadioButton)a.findViewById(R.id.addItemRadioHave)).isChecked()?true:false;
                //MediaItem(UUID ownerID, String name, String type, String unit, String quantity, Boolean bought)
                Global.mediaItem = new MediaItem(Global.getUser().groupID,name,type,platform,genre,bought);
                //after creating item we set to global to keep in memory
                Log.d("item",Global.mediaItem.toString());
                //make Query
                values.put(FeedEntry._ID, Global.mediaItem.id.toString());
                values.put(FeedEntry.COLUMN_NAME, name);
                values.put(FeedEntry.COLUMN_OWNER_ID, Global.getUser().groupID.toString());
                values.put(FeedEntry.COLUMN_TYPE, type);
                values.put(FeedEntry.COLUMN_PLATFORM, platform);
                values.put(FeedEntry.COLUMN_GENRE, genre);
                values.put(FeedEntry.COLUMN_GENRE, (bought ? 1 : 0));

                long newRowId = db.insert(FeedEntry.TABLE_NAME_MEDIA, null, values);// the null here is default for column value
                //after creating item add to db
                Log.d("item","NewRowId: " + newRowId);
                loadLists();

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Global.mediaItem = null;
                dialog.cancel();
                //
                // if we cancel we clear global item so
                // we know we have canceled the window
                //
            }
        }).setTitle(R.string.addNew);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadLists(){
        if(db.isOpen()== false){
            db.openOrCreateDatabase(FeedEntry.DATABASE_NAME,null);
        }

        Cursor haveCursor = db.rawQuery("SELECT * from " + FeedEntry.TABLE_NAME_MEDIA + " WHERE " + FeedEntry.COLUMN_BOUGHT + " = 1" ,null);
        mediaHave = (ListView)findViewById(R.id.mediaHaveListView);
        MediaCursorAdapter mediaAdapterH = new MediaCursorAdapter(this, haveCursor);
        mediaHave.setAdapter(mediaAdapterH);
        // if we want to change items in list view we do this
        //mediaAdapter.changeCursor(newCursor);
        Cursor wantCursor = db.rawQuery("SELECT * from " + FeedEntry.TABLE_NAME_MEDIA + " WHERE " + FeedEntry.COLUMN_BOUGHT + " = 0" ,null);
        mediaWant = (ListView)findViewById(R.id.mediaWantListView);
        MediaCursorAdapter mediaAdapterW = new MediaCursorAdapter(this, wantCursor);
        mediaHave.setAdapter(mediaAdapterW);

    }



}
