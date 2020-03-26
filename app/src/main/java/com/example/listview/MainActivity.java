package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.swiperefreshlayout.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import java.lang.ref.WeakReference;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<ListItem> list = new ArrayList<>();  // Main content is here
    private RecyclerView recyclerView;
    private ListAdapter lAdapter;
    private TextView statusText;
    private SwipeRefreshLayout swiper;
    private boolean isConnected = true;
    private AsyncLoaderTask t;
    private View text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.txtLoading);

        recyclerView = findViewById(R.id.recyclerView);
        lAdapter = new ListAdapter(list, this);
        recyclerView.setAdapter(lAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(doNetCheck()){
            isConnected = true;
            doAsync();
        } else {
            isConnected = false;
            statusText.setText("No Internet");
        }


        swiper = findViewById(R.id.swiper);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(doNetCheck()) {
                    isConnected = true;
                    doAsync();
                } else {
                    statusText.setText("No Internet");
                }
                swiper.setRefreshing(false); // This stops the busy-circle
            }
        });
    }

    private boolean doNetCheck() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return true;
        }

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }
    private void doAsync() {
        t = new AsyncLoaderTask(this);
        t.execute();
    }
    @Override
    protected void onResume() {

        super.onResume();
    }
    public void writeListInfo(ArrayList<ListItem> lst) {
        if(isConnected == true) {
            statusText.setVisibility(View.INVISIBLE);
            //this.list = list;
            list.clear();
            //statusText.setVisibility(View.GONE);
            list.addAll(lst);
            lAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onDestroy() {
        t.cancel(true);
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks


    }

}
