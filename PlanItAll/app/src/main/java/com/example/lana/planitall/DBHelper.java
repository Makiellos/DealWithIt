package com.example.lana.planitall;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



/**
 * Created by lanan on 12/14/2017.
 */

public class DBHelper extends SQLiteOpenHelper implements OnClickListener{

    private static final String TABLE_NAME = "task";
    private static final int DB_VERSION = 1;

    Button btnAddNewTask, button3;
    EditText editTaskName, editTaskTime;

    DBHelper dbHelper;

    public DBHelper(Context context) {
        super(context, "taskDb", null, DB_VERSION);

        dbHelper = new DBHelper(context, "task", null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       /* sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                + "id integer primary key autoincrement,"
                //TODO: add columns
                + "name text,"
                + "email text" + ");");
        */

        dbHelper = new DBHelper();

    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
