package com.sikware.FixMyLife;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sikware.FixMyLife.FeedReaderContract.FeedEntry;


public class Media extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button addMediaBtn;
    ListView mediaHave, mediaWant;
    SQLiteDatabase db;


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

        db = Global.mDbHelper.getWritableDatabase();

        loadLists();

    }
    @Override
    public void onResume(){
        super.onResume();
        if(!db.isOpen()){
            db = Global.mDbHelper.getWritableDatabase();
        }

    }
    @Override
    public void onPause(){
        if(db.isOpen()){
            db.close();
        }
        super.onPause();
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

                AlertDialog a = (AlertDialog) dialog;
//                Context context = getApplicationContext();
                EditText Ename = (EditText)a.findViewById(R.id.mediaNameItem);
                String name = Ename.getText().toString();
                String type = ((EditText)a.findViewById(R.id.mediaTypeItem)).getText().toString();
                String platform = ((EditText)a.findViewById(R.id.mediaPlatformItem)).getText().toString();
                String genre = ((EditText)a.findViewById(R.id.mediaGenreItem)).getText().toString();
                String bought = ((RadioButton)a.findViewById(R.id.addItemRadioHave)).isChecked()?"x":"o";
                //MediaItem(UUID ownerID, String name, String type, String unit, String quantity, Boolean bought)
                Global.mediaItem = new MediaItem(Global.getUser().groupID,name,type,platform,genre,bought);
                //after creating item we set to global to keep in memory
                Log.d("item",Global.mediaItem.toString());

                // insert to db
                boolean b = ((RadioButton)a.findViewById(R.id.addItemRadioHave)).isChecked();
                Global.mDbHelper.insertMediaItem(b,db);

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

        Cursor haveCursor = db.rawQuery(FeedEntry.SQL_QUERY_ALL_MEADIA_HAVE, null);
        MediaCursorAdapter mediaAdapterH = new MediaCursorAdapter(this, R.layout.media_item_view,haveCursor);

        mediaHave = (ListView)findViewById(R.id.mediaHaveListView);
        mediaHave.setAdapter(mediaAdapterH);

        // if we want to change items in list view we do this
        mediaAdapterH.changeCursor(haveCursor);

        Cursor wantCursor = db.rawQuery(FeedEntry.SQL_QUERY_ALL_MEDIA_WANT, null);
        MediaCursorAdapter mediaAdapterW = new MediaCursorAdapter(this, R.layout.media_item_view,wantCursor);

        mediaWant = (ListView)findViewById(R.id.mediaWantListView);
        mediaWant.setAdapter(mediaAdapterW);

        mediaAdapterW.changeCursor(wantCursor);

    }



}
