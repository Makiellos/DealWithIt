package com.example.lana.planitall;

/**
 * Created by lanan on 12/19/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lana.planitall.model.BaseTask;
import com.example.lana.planitall.model.DateTransform;
import com.example.lana.planitall.model.Deadline;
import com.example.lana.planitall.model.Hobby;
import com.example.lana.planitall.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeftFragment extends Fragment {

    private ListView vListOfTasks;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private List<BaseTask> taskList = new ArrayList<>();
    ArrayAdapter<BaseTask> adapter;

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

    //SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_left, container, false);
        dbHelper = new DBHelper(getContext());
        vListOfTasks = rootView.findViewById(R.id.vListOfTasks);

        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, taskList);

        vListOfTasks.setAdapter(adapter);

        vListOfTasks.setOnItemClickListener((adapterView, view, i, l) -> {
            if ((taskList.size() >= i + 1)) {
                BaseTask tempTask = taskList.get(i);
                openWriteDatabase();
                if (tempTask instanceof Task) {
                    database.execSQL("delete from task where id=" + tempTask.getId());
                } else if (tempTask instanceof Hobby) {
                    database.execSQL("delete from hobby where id=" + tempTask.getId());
                } else if (tempTask instanceof Deadline) {
                    database.execSQL("delete from deadline where id=" + tempTask.getId());
                }

                taskList.remove(i);
                adapter.clear();
                adapter.addAll(taskList);
                adapter.notifyDataSetChanged();

                closeDatabase();
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            List<BaseTask> taskList = new ArrayList<>();

            openReadDatabase();
            Cursor cursor = database.rawQuery("select * from task where date =  " + DateTransform.getCurrentDateMillis(), null);
            while (cursor.moveToNext()) {
                Task task = new Task(cursor.getInt(0), cursor.getString(1),
                        cursor.getFloat(2), new Date(cursor.getLong(3)));
                taskList.add(task);
            }
            cursor.close();

            cursor = database.rawQuery("select * from hobby where (date - " + DateTransform.getCurrentDateMillis() + ") % period = 0", null);
            while (cursor.moveToNext()) {
                Hobby hobby = new Hobby(cursor.getInt(0), cursor.getString(1),
                        cursor.getFloat(2), cursor.getLong(3), cursor.getInt(4));
                taskList.add(hobby);
            }
            cursor.close();

            cursor = database.rawQuery("select * from deadline where (from_date <= " + DateTransform.getCurrentDateMillis() + ") " +
                    "AND (to_date >= " + DateTransform.getCurrentDateMillis() + ")", null);
            while (cursor.moveToNext()) {
                Deadline deadline = new Deadline(cursor.getInt(0), cursor.getString(1),
                        cursor.getFloat(2), new Date(cursor.getLong(3)), new Date(cursor.getLong(4)));
                taskList.add(deadline);
            }
            cursor.close();
            closeDatabase();

            this.taskList = taskList;

            adapter.clear();
            adapter.addAll(taskList);

            adapter.notifyDataSetChanged();

        }
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
