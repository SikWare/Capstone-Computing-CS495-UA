package com.sikware.FixMyLife;

// Icons designed by { Madebyoliver } from Flaticon
// http://www.flaticon.com/authors/madebyoliver
//  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//  ||||||||||||||||||||||||||||||||||||
//   Need to include icon author for copyrights

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.claudiodegio.dbsync.DBSync;
import com.claudiodegio.dbsync.TableToSync;
import com.claudiodegio.dbsync.provider.CloudProvider;
import com.claudiodegio.dbsync.provider.GDriveCloudProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.sikware.FixMyLife.main.SendBirdLoginActivity;
import com.sikware.FixMyLife.main.SendBirdMainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE_OPENER = 20;
    CardView c1,c2,c3,c4,c5,c6;
    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;

    private GoogleApiClient mGoogleApiClient;
    private Bitmap mBitmapToSave;
    private DriveId mFileId;
    //Global.sync = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        c1 = (CardView)findViewById(R.id.pantry);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(v);
            }
        });
        c2 = (CardView)findViewById(R.id.notes);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(v);
            }
        });
        c3 = (CardView)findViewById(R.id.media);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(v);
            }
        });
        c4 = (CardView)findViewById(R.id.chat);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(v);
            }
        });
        c5 = (CardView)findViewById(R.id.progress);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(v);
            }
        });
        c6 = (CardView)findViewById(R.id.calendar);
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(v);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {
            // Create the API client and bind it to an instance variable.
            // We use this instance as the callback for connection and connection
            // failures.
            // Since no account name is passed, the user is prompted to choose.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            // Connect the client. Once connected, the camera is launched.
        }
        mGoogleApiClient.connect();

    }

    @Override
    protected void onPause() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
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
        getMenuInflater().inflate(R.menu.main, menu);
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


    private boolean goTo(View view){
        Intent intent = null;
        switch(view.getTag().toString()){
            case("Pantry"):
                intent = new Intent(this,Pantry.class);
                break;
            case("Notes"):
                intent = new Intent(this,Notes.class);
                break;
            case("Media"):
                intent = new Intent(this,Media.class);
                break;
            case("Chat"):
                intent = new Intent(this,SendBirdLoginActivity.class);
                break;
            case("Progress"):
                intent = new Intent(this,Progress.class);
                break;
            case("Calendar"):
                long startMillis = System.currentTimeMillis();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                startActivity(intent);
//                intent = new Intent(this,Calendar.class);
                break;
            default:
                finish();
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }

    public void deleteDriveFile(){
        Global.mdriveFile.delete(mGoogleApiClient);
    }

    public void findFile(){
        Log.i(TAG, "Looking for file.");
        Query query = new Query.Builder().addFilter(Filters.eq(SearchableField.TITLE, FeedReaderContract.FeedEntry.DATABASE_NAME))
                .build();
        Drive.DriveApi.query(mGoogleApiClient,query).setResultCallback(new
           ResultCallback<DriveApi.MetadataBufferResult>() {
               @Override
               public void onResult(@NonNull DriveApi.MetadataBufferResult result) {
                   if(!result.getStatus().isSuccess()){
                       Log.d(TAG,"Problem retrieving Files...");
                   }
                   else{
                       if(result.getMetadataBuffer().getCount()>0){
                           Metadata md = result.getMetadataBuffer().get(0);
                           Global.mFileId = md.getDriveId();
                           Global.mdriveFile = Global.mFileId.asDriveFile();
                            if(!Global.sync) {
                                Global.mdriveFile.open(mGoogleApiClient, DriveFile.MODE_READ_ONLY, null)
                                        .setResultCallback(openCallback);
                                Global.sync = true;
                            }
                       }


                   }
               }
           });

    }

    public void uploadDriveFile(){
        // Start by creating a new contents, and setting a callback.

        final Bitmap image = mBitmapToSave;
        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {

                    @Override
                    public void onResult(DriveApi.DriveContentsResult result) {
                        File dbStream = null;
                        byte[] b = null;
                        // If the operation was not successful, we cannot do anything
                        // and must
                        // fail.
                        if (!result.getStatus().isSuccess()) {
                            Log.i(TAG, "Failed to create new contents.");
                            return;
                        }
                        OutputStream outputStream = result.getDriveContents().getOutputStream();
                        // Write the bitmap data from it.
                        dbStream = new File(getApplicationContext().getDatabasePath(FeedReaderContract.FeedEntry.DATABASE_NAME).toString());
                        try {
                            //dbStream.toURL();
                            b = readFileToByteArray(dbStream);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
                        try {
                            outputStream.write(b);
                        } catch (IOException e1) {
                            Log.i(TAG, "Unable to write file contents.");
                        }
                        // Create the initial metadata - MIME type and title.
                        // Note that the user will be able to change the title later.
                        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                .setMimeType("application/x-binary").setTitle(FeedReaderContract.FeedEntry.DATABASE_NAME).build();
                        // Create an intent for the file chooser, and start it.
                        IntentSender intentSender = Drive.DriveApi
                                .newCreateFileActivityBuilder()
                                .setInitialMetadata(metadataChangeSet)
                                .setInitialDriveContents(result.getDriveContents())
                                .build(mGoogleApiClient);

                        //Global.mFileId = result.getDriveContents().getDriveId();
 //                       Global.mdriveFile = Global.mFileId.asDriveFile();

//                        findFile();
                    }
                });
        //findFile();
    }

    public void refresh(Intent data){
        //sync = false;
    findFile();
/*        if(!Global.sync) {
        Global.mFileId = data.getParcelableExtra(
                OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
        mFileId = data.getParcelableExtra(
                OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
        Global.mdriveFile = mFileId.asDriveFile();
        Global.sync = true;
    }
    else{
*/     //   if(data==null){return;}
        //deleteDriveFile();

        uploadDriveFile();
        //return;
  //  }
  //  Global.mdriveFile.open(mGoogleApiClient,DriveFile.MODE_READ_ONLY,null)
  //              .setResultCallback(openCallback);
    }
        ResultCallback openCallback = new ResultCallback<DriveApi.DriveContentsResult>() {

                    @Override
                    public void onResult(DriveApi.DriveContentsResult result) {
                        // If the operation was not successful, we cannot do anything
                        // and must
                        // fail.

                        File dbStream = null;
                        byte[] buffer = new byte[1024];
                        int bytesread = 0;
                        byte[] b = new byte[0];

                        if (!result.getStatus().isSuccess()) {
                            Log.i(TAG, "Failed to create new contents.");
                            return;
                        }
                        // Otherwise, we can write our data to the new contents./
                        Log.i(TAG, "New contents created.");
                        // Get an output stream for the contents.
                        DriveContents contents = result.getDriveContents();

                        BufferedInputStream bis = new BufferedInputStream(contents.getInputStream());

                        try {
                            dbStream = new File(getApplicationContext().getDatabasePath(FeedReaderContract.FeedEntry.DATABASE_NAME).toString());
                            FileOutputStream outputStream = new FileOutputStream(dbStream);
                            while((bytesread = bis.read(buffer))!=-1){
                                outputStream.write(buffer,0,bytesread);
                            }
                            outputStream.flush();
                            bis.close();
                            outputStream.close();
                            Global.sync = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };




    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPENER:
                if (resultCode == RESULT_OK) {
                    refresh(data);
                }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "API client connected.");
        //super.onConnected(connectionHint);
        findFile();
/*        if (Global.mFileId == null) {
            IntentSender intentSender = Drive.DriveApi
                    .newOpenFileActivityBuilder()
                   // .setMimeType(new String[] {"application/x-binary"})
                    .build(mGoogleApiClient);
            try {
                startIntentSenderForResult(intentSender, REQUEST_CODE_OPENER,
                        null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.w(TAG, "Unable to send intent", e);
            }
        }
        else {
//            if(sync){
  */              refresh(null);
//            }
    //    }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "GoogleApiClient connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Called whenever the API client fails to connect.
        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
            return;
        }
        // The failure has a resolution. Resolve it.
        // Called typically when the app is not yet authorized, and an
        // authorization
        // dialog is displayed to the user.
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }


}
