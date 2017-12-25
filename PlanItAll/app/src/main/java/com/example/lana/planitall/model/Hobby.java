package com.example.lana.planitall.model;

import java.util.Date;

/**
 * Created by lanan on 12/20/2017.
 */

public class Hobby extends BaseTask {
    protected int period;
    protected int date;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Hobby(Integer id, String name, Float duration, int date, int period) {
        super(id, name, duration);
        this.period = period;
        this.date = date;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
