package com.sikware.FixMyLife;

/**
 * Created by Ken on 4/27/2017.
 */


import android.os.AsyncTask;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class DBAdapter extends AsyncTask<String, Void, String>{
    private final String TAG = "addMediaItem";
    private Context context;
    private int dispatch;
    private String query;
    private String phpFile;


    public DBAdapter(Context context, String phpFile, String query) {
        this.query = query;
        this.phpFile = phpFile;
        this.context = context;
        this.dispatch = dispatch;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        String link;
        BufferedReader bufferedReader;
        String result;

        try {
            link = "http://sikware.us/app/" + phpFile + query;
            Log.d(TAG, link);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        Log.d(TAG,result);




    }


}
