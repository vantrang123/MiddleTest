package com.trangdv.example_android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trangdv.example_android.DatabaseHandler;
import com.trangdv.example_android.R;
import com.trangdv.example_android.listeners.OnDatabaseChangedListeners;
import com.trangdv.example_android.model.Info;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements OnDatabaseChangedListeners {

    private LayoutInflater mInflater;
    private int iD;
    Context context;
    LinearLayoutManager layoutManager;
    ItemListener listener;
    DatabaseHandler database;
    Info info;

    public RecyclerAdapter(Context context, LinearLayoutManager layoutManager, ItemListener itemListener) {
        super();
        this.context = context;
        this.layoutManager = layoutManager;
        listener = itemListener;
        database = new DatabaseHandler(context);
        database.setChangedListeners(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        info = getItem(position);
        holder.tvName.setText(info.getmName());
        holder.tvAge.setText("Tuổi: " + info.getmAge());
        holder.tvAddress.setText("Địa chỉ: " + info.getmAddress());
    }

    @Override
    public int getItemCount() {
        return database.getCount();
    }

    @Override
    public void onNewDatabaseEntryAdded() {
        notifyItemInserted(getItemCount() -1 );
        layoutManager.scrollToPosition(getItemCount() - 1 );
    }

    @Override
    public void onNewDatabaseEntryRemoved() {
        notifyItemRemoved(iD);
    }

    @Override
    public void onNewDatabaseEntryRenamed() {
        notifyItemChanged(iD);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvAge;
        public TextView tvAddress;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvAge = view.findViewById(R.id.tv_age);
            tvAddress = view.findViewById(R.id.tv_address);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.dialogConfirmDelete(context, getLayoutPosition());
                    iD = getLayoutPosition();
                    return false;
                }
            });
        }

    }

    public Info getItem(int position) {
        return database.getItemAt(position);
    }

    interface ItemListener {
        void dialogConfirmDelete(Context context, int id);
    }
}
