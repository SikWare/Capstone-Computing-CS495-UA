package com.sikware.FixMyLife;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class Notes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button addNotesBtn;
    ListView notesHave, notesWant;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addNotesBtn = (Button)findViewById(R.id.addNoteBtn);
        addNotesBtn.setOnClickListener(new View.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.notes, menu);
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
        //todo double check layouts and shit
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_notes_item_layout,null)).setPositiveButton(R.string.addNew, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //send data back from dialog

                // Gets the data repository in write mode

                AlertDialog a = (AlertDialog) dialog;
//                Context context = getApplicationContext();
                String name = ((EditText)a.findViewById(R.id.notesNameItem)).getText().toString();
                String dueDate = ((EditText)a.findViewById(R.id.notesDueDateItem)).getText().toString();
                String pointVal = ((EditText)a.findViewById(R.id.notesPointValueItem)).getText().toString();
                String details = ((EditText)a.findViewById(R.id.notesDetailsItem)).getText().toString();
                boolean urgent = ((CheckBox)a.findViewById(R.id.urgentChkBx)).isChecked();
                //NotesItem(UUID ownerID, String name, String type, String unit, String quantity, Boolean bought)
                Global.notesItem = new NotesItem(Global.getUser().groupID,name,dueDate,pointVal,details,urgent);
                //after creating item we set to global to keep in memory
                Log.d("item",Global.notesItem.toString());

                // insert to db
                //todo put the boolean back in(urgent messages are want);
                //the boolean is what determines which db the item goes in
                Global.mDbHelper.insertNotesItem(urgent,db);

                loadLists();

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Global.notesItem = null;
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
            db.openOrCreateDatabase(FeedReaderContract.FeedEntry.DATABASE_NAME,null);
        }

        Cursor haveCursor = db.rawQuery(FeedReaderContract.FeedEntry.SQL_QUERY_ALL_NOTES_HAVE, null);
        NotesCursorAdapter notesAdapterH = new NotesCursorAdapter(this, R.layout.notes_item_view,haveCursor);

        notesHave = (ListView)findViewById(R.id.notesHaveListView);
        notesHave.setAdapter(notesAdapterH);

        // if we want to change items in list view we do this
        notesAdapterH.changeCursor(haveCursor);

        Cursor wantCursor = db.rawQuery(FeedReaderContract.FeedEntry.SQL_QUERY_ALL_NOTES_WANT, null);
        NotesCursorAdapter notesAdapterW = new NotesCursorAdapter(this, R.layout.notes_item_view,wantCursor);

        notesWant = (ListView)findViewById(R.id.notesWantListView);
        notesWant.setAdapter(notesAdapterW);

        notesAdapterW.changeCursor(wantCursor);

    }


}
