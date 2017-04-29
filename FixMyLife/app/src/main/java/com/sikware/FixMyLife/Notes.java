package com.sikware.FixMyLife;

import android.content.Context;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public class Notes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button addNoteBtn, addTaskBtn;
    ListView notesView, tasksView;
    NotesAdapter notesAdapter;
    TasksAdapter taskAdapter;

    private static Context context;

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

        Notes.context = getApplicationContext();

        addNoteBtn = (Button)findViewById(R.id.addNoteBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote(v);
            }
        });

        addTaskBtn = (Button)findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask(v);
            }
        });

        new DBLoad(this, Global.SELECT_PHP, Global.NOTES_TABLE,2).execute();
        new DBLoad(this, Global.SELECT_PHP, Global.TASKS_TABLE,3).execute();
        loadLists();

    }

    @Override
    public void onResume(){
        super.onResume();
        notesAdapter.setListData(Global.notesArray);
        notesAdapter.notifyDataSetChanged();
        taskAdapter.setListData(Global.tasksArray);
        taskAdapter.notifyDataSetChanged();

    }
    @Override
    public void onPause(){
        super.onPause();
        Global.notesItem = null;
        Global.taskItem = null;
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

    void addNote(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_notes_item_layout,null)).setPositiveButton("Add Note", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog a = (AlertDialog) dialog;
                String name = ((EditText)a.findViewById(R.id.notesNameItem)).getText().toString();
                String details = ((EditText)a.findViewById(R.id.notesDetailsItem)).getText().toString();
                UUID noteID = UUID.randomUUID();
                Global.notesItem = new NotesItem(noteID,name,details);

                Global.notesArray.add(Global.notesItem);

                try {
                    String query = "?table=notes&id=" + noteID.toString() + "&name=" + URLEncoder.encode(name, "UTF-8") + "&details=" + URLEncoder.encode(details, "UTF-8");
                    DBInsert addItem = new DBInsert(context, "insertItem.php", query, false);
                    addItem.execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                notesAdapter.setListData(Global.notesArray);
                notesAdapter.notifyDataSetChanged();

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
        }).setTitle("Add Note");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void addTask(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_task_layout,null)).setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog a = (AlertDialog) dialog;
                String name = ((EditText)a.findViewById(R.id.taskName)).getText().toString();
                String details = ((EditText)a.findViewById(R.id.taskDetails)).getText().toString();
                String dueDate = ((EditText)a.findViewById(R.id.taskDueDate)).getText().toString();
                UUID taskID = UUID.randomUUID();
                Global.taskItem = new TaskItem(taskID,name,details,dueDate);

                Global.tasksArray.add(Global.taskItem);

                try {
                    String query = "?table=tasks&id=" + taskID.toString() + "&name=" + URLEncoder.encode(name, "UTF-8") + "&details=" + URLEncoder.encode(details, "UTF-8")
                            + "&dueDate=" + URLEncoder.encode(dueDate, "UTF-8");
                    Log.d("TASK Insert", query);
                    DBInsert addItem = new DBInsert(context, "insertItem.php", query, false);
                    addItem.execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                taskAdapter.setListData(Global.tasksArray);
                taskAdapter.notifyDataSetChanged();

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Global.taskItem = null;
                dialog.cancel();
                //
                // if we cancel we clear global item so
                // we know we have canceled the window
                //
            }
        }).setTitle("Add Task");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadLists(){
        notesAdapter = new NotesAdapter(context, Global.notesArray);
        taskAdapter = new TasksAdapter(context, Global.tasksArray);

        //list view stuff
        final ListView lv1 = (ListView) findViewById(R.id.notesView);
        notesView = lv1;
        final ListView lv2 = (ListView) findViewById(R.id.tasksView);
        tasksView = lv2;

        notesView.setAdapter(notesAdapter);
        tasksView.setAdapter(taskAdapter);

    }


}
