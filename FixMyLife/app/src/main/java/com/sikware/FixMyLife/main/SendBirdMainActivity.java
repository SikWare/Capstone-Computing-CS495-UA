package com.sikware.FixMyLife.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sikware.FixMyLife.R;
import com.sikware.FixMyLife.groupchannel.GroupChannelActivity;
import com.sikware.FixMyLife.openchannel.OpenChannelActivity;
import com.sikware.FixMyLife.utils.PreferenceUtils;

public class SendBirdMainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_send_bird);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        mNavView = (NavigationView) findViewById(R.id.nav_view_main);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_item_open_channels) {
                    Intent intent = new Intent(SendBirdMainActivity.this, com.sikware.FixMyLife.openchannel.OpenChannelActivity.class);
                    startActivity(intent);
                    return true;

                } else if (id == R.id.nav_item_group_channels) {
                    Intent intent = new Intent(SendBirdMainActivity.this, com.sikware.FixMyLife.groupchannel.GroupChannelActivity.class);
                    startActivity(intent);
                    return true;

                } else if (id == R.id.nav_item_disconnect) {
                    // Unregister push tokens and disconnect
                    disconnect();
                    return true;
                }

                return false;
            }
        });

        // Displays the SDK version in a TextView
        String sdkVersion = String.format(getResources().getString(R.string.all_app_version),
                BaseApplication.VERSION, SendBird.getSDKVersion());
        ((TextView) findViewById(R.id.text_main_versions)).setText(sdkVersion);
    }

    /**
     * Unregisters all push tokens for the current user so that they do not receive any notifications,
     * then disconnects from SendBird.
     */
    private void disconnect() {
        SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
            @Override
            public void onUnregistered(SendBirdException e) {
                if (e != null) {
                    // Error!
                    e.printStackTrace();
                    return;
                }

                Toast.makeText(SendBirdMainActivity.this, "All push tokens unregistered.", Toast.LENGTH_SHORT)
                        .show();

                SendBird.disconnect(new SendBird.DisconnectHandler() {
                    @Override
                    public void onDisconnected() {
                        PreferenceUtils.setConnected(SendBirdMainActivity.this, false);
                        Intent intent = new Intent(getApplicationContext(), SendBirdLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}
