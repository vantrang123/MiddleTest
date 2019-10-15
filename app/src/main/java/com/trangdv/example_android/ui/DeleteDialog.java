package com.trangdv.example_android.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.trangdv.example_android.DatabaseHandler;
import com.trangdv.example_android.R;

import java.io.File;

@SuppressLint("ValidFragment")
public class DeleteDialog extends DialogFragment {
    private static final String TAG = "DeleteDialog";
    private TextView delete;
    private TextView cancel;
    private int id;
    private Context mContext;
    private DatabaseHandler databaseHandler;
    public DeleteDialog(Context context, int position){
        id =  position;
        mContext = context;
        databaseHandler = new DatabaseHandler(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            this.getDialog().setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes(); // change this to your dialog.
            params.y = 300; // Here is the param to set your dialog position. Same with params.x
            getDialog().getWindow().setAttributes(params);
        }

        View view = inflater.inflate(R.layout.dialog_delete,container,false);
        setup(view);

        return view;
    }

    public void setup(View v) {
        delete = v.findViewById(R.id.tv_delete);
        cancel = v.findViewById(R.id.tv_cancel);
        delete.setText(R.string.tv_delete);
        cancel.setText(R.string.tv_cancel);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile(mContext,id);
                getDialog().dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }

    public void deleteFile(Context context, int position) {
        Toast.makeText(context, String.format(context.getString(R.string.toast_delete)),Toast.LENGTH_SHORT).show();
        databaseHandler.removeItemWithId(databaseHandler.getItemAt(position).getmId());

    }

}
