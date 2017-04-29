package com.sikware.FixMyLife;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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

import com.sikware.FixMyLife.FeedReaderContract.FeedEntry;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.UUID;



public class Media extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button addMediaBtn;
    ListView mediaHave, mediaWant;
    private static Context context;

    MediaAdapter haveAdapter;
    MediaAdapter wantAdapter;




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

        Media.context = getApplicationContext();

        addMediaBtn = (Button) findViewById(R.id.addMediaBtn);
        addMediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        new DBLoad(this, Global.SELECT_PHP, Global.MEDIA_TABLE).execute();
        loadLists();
    }

        @Override
    public void onResume() {
        super.onResume();
            haveAdapter.setListData(Global.mediaHaveArray);
            haveAdapter.notifyDataSetChanged();
            wantAdapter.setListData(Global.mediaWantArray);
            wantAdapter.notifyDataSetChanged();

        }

    @Override
    public void onPause() {
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

    void addItem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_media_item_layout, null)).setPositiveButton(R.string.addNew, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog a = (AlertDialog) dialog;
                EditText Ename = (EditText) a.findViewById(R.id.mediaNameItem);
                String name = Ename.getText().toString();
                String type = ((EditText) a.findViewById(R.id.mediaTypeItem)).getText().toString();
                String platform = ((EditText) a.findViewById(R.id.mediaPlatformItem)).getText().toString();
                String genre = ((EditText) a.findViewById(R.id.mediaGenreItem)).getText().toString();
                String bought = ((RadioButton) a.findViewById(R.id.addMediaItemRadioHave)).isChecked() ? "1" : "0";

                //mysql
                //Will more than likely need to update to accomodate obj

                UUID mediaID = UUID.randomUUID();
                Global.mediaItem  = new MediaItem(mediaID, name, type, platform, genre, bought);

                if (bought == "1") { Global.mediaHaveArray.add(Global.mediaItem); }
                else { Global.mediaWantArray.add(Global.mediaItem); }

                try {
                    String query = "?table=media&id=" + mediaID.toString() + "&name=" + URLEncoder.encode(name, "UTF-8") + "&type=" + URLEncoder.encode(type, "UTF-8") +
                            "&platform=" + URLEncoder.encode(platform, "UTF-8") + "&genre=" + URLEncoder.encode(genre, "UTF-8") + "&owned=" + bought;
                    DBInsert addItem = new DBInsert(context, "insertItem.php", query, false);
                    addItem.execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

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

    private void loadLists() {

        haveAdapter = new MediaAdapter(context, Global.mediaHaveArray);
        wantAdapter = new MediaAdapter(context, Global.mediaWantArray);

        //list view stuff
        final ListView lv1 = (ListView) findViewById(R.id.mediaHaveListView);
        mediaHave = lv1;
        final ListView lv2 = (ListView) findViewById(R.id.mediaWantListView);
        mediaWant = lv2;

        mediaHave.setAdapter(haveAdapter);
        mediaWant.setAdapter(wantAdapter);

    }

}


















