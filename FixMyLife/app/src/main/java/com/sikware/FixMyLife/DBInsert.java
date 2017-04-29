package com.sikware.FixMyLife;

/**
 * Created by Ken on 4/27/2017.
 */


import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class DBInsert extends AsyncTask<String, Void, String>{
    private final String TAG = "DBInsert";
    private Context context;
    private String query;
    private String phpFile;


    public DBInsert(Context context, String phpFile, String query, Boolean dispatch) {
        this.query = query;
        this.phpFile = phpFile;
        this.context = context;
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
        Log.d(TAG, "Data inserted succesfully!");
    }


}
