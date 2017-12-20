package com.example.lana.planitall;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lanan on 12/19/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "testDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table task ("
                + "id integer primary key autoincrement,"
                + "name text not null,"
                + "duration integer not null,"
                + "date integer not null);");
        sqLiteDatabase.execSQL("create table hobby ("
                + "id integer primary key autoincrement,"
                + "name text not null,"
                + "duration integer not null,"
                + "period integer not null);");
        sqLiteDatabase.execSQL("create table deadline ("
                + "id integer primary key autoincrement,"
                + "name text not null,"
                + "duration integer not null,"
                + "from_date integer not null,"
                + "to_date integer not null);");
        sqLiteDatabase.execSQL("create table subtask ("
                + "id integer primary key autoincrement,"
                + "message text not null,"
                + "parent_id integer not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
