package com.sikware.FixMyLife;

/**
 * Created by Ken on 4/28/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import static android.util.Log.d;

public class DBLoad extends AsyncTask<String, Void, String>{
    private final String TAG = "DBLoad";
    private Context context;
    private String php;
    private String query;
    private int dispatch;

    public DBLoad(Context context, String php, String query, int dispatch) {
        this.context = context;
        this.php = php;
        this.query = query;
        this.dispatch = dispatch;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {

            try{
                String link = "http://sikware.us/app/selectItem.php"+query;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                Log.d("TAG", sb.toString());
                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

    @Override
    protected void onPostExecute(String result){
        convertToJSON(result);
    }

    public void convertToJSON(String result) {
        if(result == null) { d(TAG, "no data received"); return; }

        switch (dispatch) {

            case 0 : {
                ArrayList<MediaItem> tempHave = new ArrayList<MediaItem>();
                ArrayList<MediaItem> tempWant = new ArrayList<MediaItem>();

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d(TAG,jsonObject.toString());
                        UUID id = UUID.fromString(jsonObject.getString("id"));
                        String name =  jsonObject.getString("name");
                        String type =  jsonObject.getString("type");
                        String platform =  jsonObject.getString("name");
                        String genre =  jsonObject.getString("genre");
                        String isBought = jsonObject.getString("owned");
                        MediaItem item = new MediaItem(id, name, type, platform, genre, isBought);
                        Log.d("isBought: ",isBought);
                        if(isBought.equalsIgnoreCase("1")) { tempHave.add(item); Log.d(TAG,"Added to Have Media Array"); }
                        else { tempWant.add(item); Log.d(TAG,"Added to Media Array");}
                        d("last item inserted",item.toString());
                    }
                    Global.mediaHaveArray = tempHave;
                    Global.mediaWantArray  = tempWant;
                }
                catch (JSONException jse) {
                    jse.printStackTrace();
                }
            }

            case 1 : {
                ArrayList<PantryItem> tempHave = new ArrayList<PantryItem>();
                ArrayList<PantryItem> tempWant = new ArrayList<PantryItem>();

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d(TAG,jsonObject.toString());
                        UUID id = UUID.fromString(jsonObject.getString("id"));
                        String name =  jsonObject.getString("name");
                        String type =  jsonObject.getString("type");
                        String unit =  jsonObject.getString("unit");
                        String quantity =  jsonObject.getString("quantity");
                        String isBought = jsonObject.getString("owned");
                        PantryItem item = new PantryItem(id, name, type, unit, quantity, isBought);
                        Log.d("isBought: ",isBought);
                        if(isBought.equalsIgnoreCase("1")) { tempHave.add(item); Log.d(TAG,"Added to Have Pantry Array"); }
                        else { tempWant.add(item); Log.d(TAG,"Added to Want Pantry Array");}
                        d("last item inserted",item.toString());
                    }
                    Global.pantryHaveArray = tempHave;
                    Global.pantryWantArray  = tempWant;
                }
                catch (JSONException jse) {
                    jse.printStackTrace();
                }
            }

            case 2 : {
                ArrayList<NotesItem> tempNotes = new ArrayList<NotesItem>();

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d(TAG,jsonObject.toString());
                        UUID id = UUID.fromString(jsonObject.getString("id"));
                        String name =  jsonObject.getString("name");
                        String details =  jsonObject.getString("details");
                        NotesItem item = new NotesItem(id, name, details);
                        tempNotes.add(item);
                        d("last item inserted",item.toString());
                    }
                  Global.notesArray = tempNotes;
                }
                catch (JSONException jse) {
                    jse.printStackTrace();
                }
            }

            case 3 : {
                ArrayList<TaskItem> tempTasks = new ArrayList<TaskItem>();

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d(TAG,jsonObject.toString());
                        UUID id = UUID.fromString(jsonObject.getString("id"));
                        String name =  jsonObject.getString("name");
                        String details =  jsonObject.getString("details");
                        String dueDate = jsonObject.getString("dueDate");
                        TaskItem item = new TaskItem(id, name, details, dueDate);
                        tempTasks.add(item);
                        d("last item inserted",item.toString());
                    }
                    Global.tasksArray = tempTasks;
                }
                catch (JSONException jse) {
                    jse.printStackTrace();
                }
            }
        }
    }
}
