package com.trangdv.example_android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.trangdv.example_android.DatabaseHandler;
import com.trangdv.example_android.R;
import com.trangdv.example_android.model.Info;

public class DetailActivity extends AppCompatActivity {
    public static final String KEY_POSITION = "position";
    DatabaseHandler database;
    Info info;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvAddress;
    private int position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("DetailInfo");
        database = new DatabaseHandler(this);

        Intent intent = getIntent();
        position = intent.getIntExtra(KEY_POSITION, 0);

        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        tvAddress = findViewById(R.id.tv_address);

        info = getItem(position);

        tvName.setText(info.getmName());
        tvAge.setText(info.getmAge());
        tvAddress.setText(info.getmAddress());
    }

    public Info getItem(int position) {
        return database.getItemAt(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
