package com.sikware.FixMyLife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public class Pantry extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button addItemButton;
    ListView pantryHave, pantryWant;

    private static Context context;
    PantryAdapter haveAdapter;
    PantryAdapter wantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Pantry.context = getApplicationContext();

        addItemButton = (Button)findViewById(R.id.addItemBtn);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        new DBLoad(this,Global.SELECT_PHP,Global.PANTRY_TABLE,1).execute();
        loadLists();
    }

    @Override
    public void onResume(){
        super.onResume();
        haveAdapter.setListData(Global.pantryHaveArray);
        haveAdapter.notifyDataSetChanged();
        wantAdapter.setListData(Global.pantryWantArray);
        wantAdapter.notifyDataSetChanged();

    }
    @Override
    public void onPause(){
        super.onPause();
        Global.pantryItem = null;
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
        getMenuInflater().inflate(R.menu.pantry, menu);
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
        builder.setView(inflater.inflate(R.layout.add_pantry_item_layout, null)).setPositiveButton(R.string.addNew, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog a = (AlertDialog) dialog;
                EditText Ename = (EditText) a.findViewById(R.id.pantryNameItem);
                String name = Ename.getText().toString();
                String type = ((EditText) a.findViewById(R.id.pantryTypeItem)).getText().toString();
                String unit = ((EditText) a.findViewById(R.id.pantryUnitItem)).getText().toString();
                String quantity = ((EditText) a.findViewById(R.id.pantryQtyItem)).getText().toString();
                String bought = ((RadioButton) a.findViewById(R.id.addPantryItemRadioHave)).isChecked() ? "1" : "0";

                UUID pantryID = UUID.randomUUID();
                Global.pantryItem = new PantryItem(pantryID, name, type, unit, quantity, bought);

                if (bought == "1") {
                    Global.pantryHaveArray.add(Global.pantryItem);
                } else {
                    Global.pantryWantArray.add(Global.pantryItem);
                }

                try {
                    String query = "?table=pantry&id=" + pantryID.toString() + "&name=" + URLEncoder.encode(name, "UTF-8") + "&type=" + URLEncoder.encode(type, "UTF-8") +
                            "&unit=" + URLEncoder.encode(unit, "UTF-8") + "&quantity=" + URLEncoder.encode(quantity, "UTF-8") + "&owned=" + bought;
                    DBInsert addItem = new DBInsert(context, "insertItem.php", query, false);
                    addItem.execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                haveAdapter.setListData(Global.pantryHaveArray);
                haveAdapter.notifyDataSetChanged();
                wantAdapter.setListData(Global.pantryWantArray);
                wantAdapter.notifyDataSetChanged();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Global.pantryItem = null;
                dialog.cancel();
                //
                // if we cancel we clear global item so
                // we know we have canceled the window
                //
            }
        }).setTitle("Add New");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadLists(){
        haveAdapter = new PantryAdapter(context, Global.pantryHaveArray);
        wantAdapter = new PantryAdapter(context, Global.pantryWantArray);

        //list view stuff
        final ListView lv1 = (ListView) findViewById(R.id.pantryHaveListView);
        pantryHave = lv1;
        final ListView lv2 = (ListView) findViewById(R.id.pantryWantListView);
        pantryWant = lv2;

        pantryHave.setAdapter(haveAdapter);
        pantryWant.setAdapter(wantAdapter);

    }

}
