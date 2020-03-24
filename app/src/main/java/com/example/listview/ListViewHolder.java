package com.example.listview;

import android.widget.TextView;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder {
    public TextView idView;
    public TextView list_id_view;
    public TextView name;

    public ListViewHolder(View view) {
        super(view);
        idView = view.findViewById(R.id.lblID);
        list_id_view = view.findViewById(R.id.lblListID);
        name = view.findViewById(R.id.lblName);
    }
}
