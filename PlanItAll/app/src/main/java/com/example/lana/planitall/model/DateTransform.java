package com.example.lana.planitall.model;

import java.util.Date;

/**
 * Created by lanan on 12/25/2017.
 */

public class DateTransform {

    public static long getCurrentDateMillis(){
        final long MILLIS_IN_DAY = 60 * 60 * 24 * 1000;
        long currentTime = new Date().getTime();
        long dateOnly = (currentTime / MILLIS_IN_DAY) * MILLIS_IN_DAY;

        return dateOnly;
    }

    public static long deleteMills(Date date){
        final long MILLIS_IN_DAY = 60 * 60 * 24 * 1000;
        long currentTime = date.getTime();
        long dateOnly = (currentTime / MILLIS_IN_DAY) * MILLIS_IN_DAY;

        return dateOnly;

    }

}
