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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<ListItem> list = new ArrayList<>();  // Main content is here
    private RecyclerView recyclerView;
    private ListAdapter lAdapter;
    private TextView statusText;
    private SwipeRefreshLayout swiper;
    private int isConnected = 1;
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
            isConnected = 1;
            new AsyncLoaderTask(this).execute();
        } else {
            isConnected = 0;
            statusText.setText("No Internet");
        }


        swiper = findViewById(R.id.swiper);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(doNetCheck()) {
                    isConnected = 1;
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

        if (netInfo != null && netInfo.isConnected()) {
           return true;
        } else {
            return false;
        }
    }
    private void doAsync() {
        new AsyncLoaderTask(this).execute();
    }
    @Override
    protected void onResume() {

        super.onResume();
    }
    public void writeListInfo(ArrayList<ListItem> lst) {
        if(isConnected == 1) {
            statusText.setVisibility(View.INVISIBLE);
            //this.list = list;
            list.clear();
            //statusText.setVisibility(View.GONE);
            list.addAll(lst);
            lAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks


    }

}
