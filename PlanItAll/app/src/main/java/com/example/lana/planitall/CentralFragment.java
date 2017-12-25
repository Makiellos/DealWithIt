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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lana.planitall.model.DateTransform;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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

    private RadioGroup typeRG;
    private RadioButton rBtnTask;
    private RadioButton rBtnHobby;
    private RadioButton rBtnDeadline;

    private EditText nameET;
    private EditText timeET;
    private EditText dateET;
    private EditText periodET;
    private EditText fromTimeET;
    private EditText toTimeET;
    private Button addBtn;

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
        View rootView = inflater.inflate(R.layout.fragment_central_new, container, false);
        typeRG = rootView.findViewById(R.id.lRGTaskTypes);
        rBtnTask = rootView.findViewById(R.id.vRBtnTypeTask);
        rBtnHobby = rootView.findViewById(R.id.vRBtnTypeHobby);
        rBtnDeadline = rootView.findViewById(R.id.vRBtnTypeDeadline);
        nameET = rootView.findViewById(R.id.vETTaskName);
        timeET = rootView.findViewById(R.id.vETTime);
        dateET = rootView.findViewById(R.id.vETDateTask);
        periodET = rootView.findViewById(R.id.vETDateHobby);
        fromTimeET = rootView.findViewById(R.id.vETDateDeadlineFrom);
        toTimeET = rootView.findViewById(R.id.vETDateDeadlineTo);
        addBtn = rootView.findViewById(R.id.vBtnAdd);
        addBtn.setOnClickListener(view -> {
            openWriteDatabase();
            try {
                String name = nameET.getText().toString();
                String durationString = timeET.getText().toString();
                float duration = Float.valueOf(durationString);
                if (rBtnTask.isChecked()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = formatter.parse(dateET.getText().toString());
                    long dateMillis = DateTransform.deleteMills(date);
                    database.execSQL("insert or replace into task (name, duration, date) values(\"" + name + "\", "
                    + duration + ", "
                    + dateMillis + ")");
                } else if (rBtnHobby.isChecked()) {
                    int period = Integer.valueOf(periodET.getText().toString());
                    database.execSQL("insert or replace into hobby (name, duration, date, period) values(\"" + name + "\", "
                            + duration + ", "
                            + DateTransform.getCurrentDateMillis() + ", "
                            + period + ")");
                } else if (rBtnDeadline.isChecked()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date fromDate = formatter.parse(fromTimeET.getText().toString());
                    long fromDateMillis = DateTransform.deleteMills(fromDate);
                    Date toDate = formatter.parse(toTimeET.getText().toString());
                    long toDateMillis = DateTransform.deleteMills(toDate);
                    database.execSQL("insert or replace into deadline (name, duration, from_date, to_date) values(\"" + name + "\", "
                            + duration + ", "
                            + fromDateMillis + ", "
                            + toDateMillis + ")");
                }
                nameET.setText("");
                timeET.setText("");
                dateET.setText("");
                periodET.setText("");
                fromTimeET.setText("");
                toTimeET.setText("");
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                closeDatabase();
            }
        });
        typeRG.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.vRBtnTypeHobby:
                    dateET.setVisibility(View.GONE);
                    periodET.setVisibility(View.VISIBLE);
                    fromTimeET.setVisibility(View.GONE);
                    toTimeET.setVisibility(View.GONE);
                    break;
                case R.id.vRBtnTypeDeadline:
                    dateET.setVisibility(View.GONE);
                    periodET.setVisibility(View.GONE);
                    fromTimeET.setVisibility(View.VISIBLE);
                    toTimeET.setVisibility(View.VISIBLE);
                    break;
                default:
                    dateET.setVisibility(View.VISIBLE);
                    periodET.setVisibility(View.GONE);
                    fromTimeET.setVisibility(View.GONE);
                    toTimeET.setVisibility(View.GONE);
                    break;
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        openReadDatabase();
        String text = "Tasks: \n";
        Cursor cursor = database.rawQuery("select * from task", null);
        while (cursor.moveToNext()) {
            text += cursor.getInt(0) + ", "
                    + cursor.getString(1) + ", "
                    + cursor.getFloat(2) + ", "
                    + cursor.getLong(3) + "\n";
        }
        cursor.close();
        text += "Hobby: \n";
        cursor = database.rawQuery("select * from hobby", null);
        while (cursor.moveToNext()) {
            text += cursor.getString(1) + "  "
                    + cursor.getFloat(2) + "\n";
        }
        cursor.close();
        text += "Deadline: \n";
        cursor = database.rawQuery("select * from deadline", null);
        while (cursor.moveToNext()) {
            text += cursor.getInt(0) + ", "
                    + cursor.getString(1) + ", "
                    + cursor.getFloat(2) + ", "
                    + cursor.getLong(3) + ", "
                    + cursor.getLong(4) + "\n";
        }
        cursor.close();
        Log.d("lol", "onResume: " + text);
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
