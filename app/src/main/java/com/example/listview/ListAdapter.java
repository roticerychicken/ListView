package com.example.listview;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private List<ListItem> lst;
    private MainActivity ma;
    private static final String TAG = "listAdapter";
    public ListAdapter(List<ListItem> lst, MainActivity ma) {
        this.lst = lst;
        this.ma = ma;
    }

    @NonNull
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_entry,viewGroup,false);

        itemView.setOnClickListener(this.ma);

        return new ListViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ListViewHolder holder, int i) {
        Log.d("EmployeesAdapter", "onBindViewHolder: FILLING VIEW HOLDER Employee ");
        ListItem n = (ListItem) this.lst.get(i);
        holder.idView.setText(String.valueOf(n.getId()));
        holder.list_id_view.setText(String.valueOf(n.getListId()));
        holder.name.setText(n.getName());
    }

    public int getItemCount() {
        return this.lst.size();
    }
}
