package com.example.lana.planitall;

/**
 * Created by lanan on 12/19/2017.
 */

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class CentralFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private DBHelper dbHelper;
    @Nullable
    private SQLiteDatabase database;

    private EditText nameET;
    private EditText timeET;
    private Button addBtn;
    private Button readBtn;

    public CentralFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CentralFragment newInstance() {
        CentralFragment fragment = new CentralFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getContext());
        View rootView = inflater.inflate(R.layout.fragment_central, container, false);
        nameET = rootView.findViewById(R.id.editTaskName);
        timeET = rootView.findViewById(R.id.editTaskTime);
        addBtn = rootView.findViewById(R.id.btnAddNewTask);
        readBtn = rootView.findViewById(R.id.button3);
        addBtn.setOnClickListener(view -> {
            openWriteDatabase();
            String name = nameET.getText().toString();
            String durationString = timeET.getText().toString();
            int duration = Integer.valueOf(durationString);
            database.execSQL("insert or replace into task (name, duration, date) values(\"" + name + "\", "
                    + duration + ", "
                    + System.currentTimeMillis() + ")");
            closeDatabase();
        });
        readBtn.setOnClickListener(view -> {
            openReadDatabase();
            Cursor cursor = database.rawQuery("select name, duration, date from task", null);
            String text = "";
            while (cursor.moveToNext()) {
                text += cursor.getString(0) + ", "
                        + cursor.getInt(1) + ", "
                        + cursor.getInt(2) + " \n";
            }
            cursor.close();
            closeDatabase();
            new AlertDialog.Builder(getContext())
                    .setMessage(text)
                    .show();
        });

        // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    @Override
    public void onStop() {
        closeDatabase();
        super.onStop();
    }

    private void closeDatabase() {
        if (database != null) {
            database.close();
        }
        database = null;
    }

    private void openReadDatabase() {
        if (database == null) {
            database = dbHelper.getReadableDatabase();
        }
    }

    private void openWriteDatabase() {
        if (database == null) {
            database = dbHelper.getWritableDatabase();
        }
    }
}
