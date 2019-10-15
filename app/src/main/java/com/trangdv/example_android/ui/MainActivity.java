package com.trangdv.example_android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trangdv.example_android.DatabaseHandler;
import com.trangdv.example_android.R;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemListener {
    private Button btnAdd;
    private EditText edtName;
    private EditText edtAge;
    private EditText edtAddress;
    RecyclerView recyclerView;
    DatabaseHandler database;

    String name;
    String age;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MiddleTest");
        database = new DatabaseHandler(this);

        btnAdd = findViewById(R.id.btn_add);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        edtAddress = findViewById(R.id.edt_address);

        recyclerView = findViewById(R.id.rv_info);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(this, layoutManager, this);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtName.getText().toString().equals("") && !edtAge.getText().toString().equals("") && !edtAddress.getText().toString().equals("")) {
                    getInfo();
                    if (!checkAge(age)) {
                        Toast.makeText(MainActivity.this, "Tuổi không hợp lệ!", Toast.LENGTH_SHORT).show();
                    } else {
                        database.addInfo(name, age, address);
                        Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Bạn phải nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkAge(String age) {
        int num = Integer.parseInt(age);
        if (num > 1 && num < 99) {
            return true;
        }
        return false;
    }

    private void getInfo() {
        name = edtName.getText().toString();
        age = edtAge.getText().toString();
        address = edtAddress.getText().toString();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void dialogConfirmDelete(Context context, int id) {
        DeleteDialog dialog = new DeleteDialog(context, id);
        dialog.show(getSupportFragmentManager(), "OptionItemDialog");
    }

    @Override
    public void dispatchToDetail(int id) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_POSITION, id);
        startActivity(intent);
    }

}
