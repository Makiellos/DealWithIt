package com.example.lana.planitall;

/**
 * Created by lanan on 12/19/2017.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lana.planitall.model.BaseTask;
import com.example.lana.planitall.model.Deadline;
import com.example.lana.planitall.model.Hobby;
import com.example.lana.planitall.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeftFragment extends Fragment {

    private ListView vListOfTasks;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public LeftFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LeftFragment newInstance() {
        LeftFragment fragment = new LeftFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_left, container, false);
        dbHelper = new DBHelper(getContext());
        vListOfTasks = rootView.findViewById(R.id.vListOfTasks);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Map<Integer, BaseTask> taskMap = new HashMap<>();
        openReadDatabase();
        String text = "Tasks: \n";
        Cursor cursor = database.rawQuery("select * from task", null);
        while (cursor.moveToNext()) {
            Task task = new Task(cursor.getInt(0), cursor.getString(1),
                    cursor.getFloat(2), new Date(cursor.getInt(3)));
            taskMap.put(cursor.getInt(0), task);
        }
        cursor.close();

        Map<Integer, BaseTask> hobbyMap = new HashMap<>();
        text += "Hobby: \n";
        cursor = database.rawQuery("select * from hobby", null);
        while (cursor.moveToNext()) {
            Hobby hobby = new Hobby(cursor.getInt(0), cursor.getString(1),
                    cursor.getFloat(2), cursor.getInt(3), cursor.getInt(4));
            taskMap.put(cursor.getInt(0), hobby);
        }
        cursor.close();
        Map<Integer, BaseTask> deadlineMap = new HashMap<>();
        text += "Deadline: \n";
        cursor = database.rawQuery("select * from deadline", null);
        while (cursor.moveToNext()) {
            Deadline deadline = new Deadline(cursor.getInt(0), cursor.getString(1),
                    cursor.getFloat(2), new Date(cursor.getInt(3)), new Date(cursor.getInt(4)));
            taskMap.put(cursor.getInt(0), deadline);
        }
        cursor.close();
        closeDatabase();
    }

    

    @Override
    public void onPause() {
        closeDatabase();
        super.onPause();
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
