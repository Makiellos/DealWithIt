package com.example.lana.planitall;

/**
 * Created by lanan on 12/19/2017.
 */

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.lana.planitall.model.BaseTask;
import com.example.lana.planitall.model.DateTransform;
import com.example.lana.planitall.model.Deadline;
import com.example.lana.planitall.model.Hobby;
import com.example.lana.planitall.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.YEAR;

/**
 * A placeholder fragment containing a simple view.
 */
public class RightFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private CalendarView vCalendarOfTasks;
    private SQLiteDatabase database;
    private List<BaseTask> taskList = new ArrayList<>();
    private DBHelper dbHelper;

    public RightFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RightFragment newInstance() {
        RightFragment fragment = new RightFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_right, container, false);

        dbHelper = new DBHelper(getContext());

        vCalendarOfTasks = rootView.findViewById(R.id.calendarView);
        vCalendarOfTasks.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            String taskText = "";

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            cal.set(YEAR, i);
            cal.set(Calendar.MONTH, i1);
            cal.set(Calendar.DAY_OF_MONTH, i2);

            Long date = cal.getTimeInMillis();
            Date dateTemp = cal.getTime();
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            String dateStr = formater.format(dateTemp);
            Boolean  areThereAnyTasks = false;

            openReadDatabase();
            Cursor cursor = database.rawQuery("select * from task where date =  " + date, null);
            while (cursor.moveToNext()) {
                taskText += "Name: " + cursor.getString(1) + "  Task  Duration: " + cursor.getFloat(2) + "\n\n";
                areThereAnyTasks = true;
            }
            cursor.close();

            cursor = database.rawQuery("select * from hobby where (date - " + date + ") % (period*1000*60*60*24) = 0", null);
            while (cursor.moveToNext()) {
                taskText += "Name: " + cursor.getString(1) + "  Hobby  Period: " + cursor.getInt(4) + "\n\n";
                areThereAnyTasks = true;
            }
            cursor.close();

            cursor = database.rawQuery("select * from deadline where (from_date <= " + date + ") " +
                    "AND (to_date >= " + date + ")", null);
            while (cursor.moveToNext()) {
                taskText += "Name: " + cursor.getString(1) + "  Deadline  From: " + formater.format(new Date(cursor.getLong(3)))
                          + " To: " + formater.format(new Date(cursor.getLong(4))) + "\n\n";
                areThereAnyTasks = true;
            }
            cursor.close();

            closeDatabase();

            if (!areThereAnyTasks){
                taskText = "There are no tasks";
            }

            new AlertDialog.Builder(getContext()).setMessage(taskText).show();

        });


        return rootView;
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
