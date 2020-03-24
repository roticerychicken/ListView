package com.example.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonWriter;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsyncLoaderTask extends AsyncTask<String, Void, String> {
      private static final String TAG = "AsyncLoaderTask";
      @SuppressLint("StaticFieldLeak")
      private MainActivity mainActivity;
      private HashMap<String,String> wData = new HashMap<>();
      private Bitmap bitmap;
      private String lstName;
      private ArrayList<ListItem> list = new ArrayList<>();
      private static final String API = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
      private String strmain = "";


      AsyncLoaderTask(MainActivity ma) {
          mainActivity = ma;
      }

      protected void onPostExecute(String s) {
          Log.d("strmain","jhgh");
          mainActivity.writeListInfo(list);
      }
      @RequiresApi(api = Build.VERSION_CODES.N)
      protected String doInBackground(String... params) {

          StringBuilder sb = new StringBuilder();
          BufferedReader reader = null;

          try {
              URL url = new URL(API);
              HttpURLConnection conn = (HttpURLConnection) url.openConnection();
              conn.setRequestMethod("GET");
              InputStream is = conn.getInputStream();
              reader = new BufferedReader((new InputStreamReader(is)));

              String line;
              while ((line = reader.readLine()) != null) {
                  sb.append(line).append('\n');
              }
          } catch (Exception e) {
                Log.e(TAG,"doInBackground: ", e);
          }

          try {
               JSONArray ar = new JSONArray(sb+"");

               for(int i = 0; i < ar.length(); i++) {
                   JSONObject obj = (JSONObject) ar.get(i);

                   if(parseName(obj.getString("name")) != null) {
                       ListItem li = new ListItem();
                       li.setId(Integer.parseInt(obj.getString("id")));
                       li.setListId(Integer.parseInt(obj.getString("listId")));
                       li.setName(obj.getString("name"));
                       li.setNameValue(Integer.parseInt(parseName(obj.getString("name"))));
                       list.add(li);
                   }
               }
                    list.sort((l1, l2) -> {
                        if (l1.getListId() == l2.getListId()) {
                            return l1.getNameValue() - l2.getNameValue();
                        } else {
                            return l1.getListId() - l2.getListId();
                        }
                    });
          } catch (JSONException e) {

              e.printStackTrace();
          }
          return null;
      }


    public static String parseName(String name) {
        if(name != null) {
                String regular_exp = "[Ii]tem\\s(0|[1-9][0-9]*)*$";

                Pattern ptn = Pattern.compile(regular_exp);
                Matcher match = ptn.matcher(name);
                if (match.matches() == true) {
                    return name.replaceAll("\\D+","");
                }
        }
        return null;

    }
}
