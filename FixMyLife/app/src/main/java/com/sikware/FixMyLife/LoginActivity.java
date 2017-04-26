package com.sikware.FixMyLife;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.claudiodegio.dbsync.DBSync;
import com.claudiodegio.dbsync.TableToSync;
import com.claudiodegio.dbsync.provider.CloudProvider;
import com.claudiodegio.dbsync.provider.GDriveCloudProvider;
import com.google.android.gms.drive.DriveId;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.sikware.FixMyLife.Global.acct;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    SignInButton signInButton;
    protected DriveId mDriveId;
    final String DRIVE_ID_FILE = "DRIVE_ID_FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mStatusTextView = (TextView) findViewById(R.id.status);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        String driveId = getPreferences(Context.MODE_PRIVATE).getString(DRIVE_ID_FILE, null);

        if (driveId != null) {
            mDriveId = DriveId.decodeFromString(driveId);


        CloudProvider gDriveProvider = new GDriveCloudProvider.Builder(this.getBaseContext())
                .setSyncFileByDriveId(mDriveId)
                .setGoogleApiClient(mGoogleApiClient)
                .build();

        DBSync  dbSync = new DBSync.Builder(this.getBaseContext())
                .setCloudProvider(gDriveProvider)
                .setSQLiteDatabase(Global.mDbHelper.getReadableDatabase())
                .setDataBaseName(Global.mDbHelper.getDatabaseName())
                .addTable(new TableToSync.Builder("name").build())
                .build();
        dbSync.sync();

        }

        //Intent intent = new Intent(this,SendBirdMainActivity.class);
        //startActivity(intent);
        //finish();
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        mGoogleApiClient.connect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
    }


    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG, "handleSignInResult: " + result.isSuccess());
        if(result.isSuccess()) {
            //todo launch activity if .isSuccess()
            acct = result.getSignInAccount();
            Log.d(TAG, "Name: " + acct.getDisplayName());
            Log.d(TAG, "Email: " + acct.getEmail());
            String driveId = acct.getId();
            if (driveId != null) {
                mDriveId = DriveId.decodeFromString(driveId);


                CloudProvider gDriveProvider = new GDriveCloudProvider.Builder(this.getBaseContext())
                        .setSyncFileByDriveId(mDriveId)
                        .setGoogleApiClient(mGoogleApiClient)
                        .build();

                DBSync  dbSync = new DBSync.Builder(this.getBaseContext())
                        .setCloudProvider(gDriveProvider)
                        .setSQLiteDatabase(Global.mDbHelper.getReadableDatabase())
                        .setDataBaseName(Global.mDbHelper.getDatabaseName())
                        .addTable(new TableToSync.Builder("name").build())
                        .build();
                dbSync.sync();

            }
            //updateUI(true);
            }// todo move to bottom when finished
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        //}
    }
}