package com.example.lana.planitall.model;

import java.util.Date;

/**
 * Created by lanan on 12/20/2017.
 */

public class Task extends BaseTask {
    protected Date date;


    public Task(Integer id, String name, Float duration, Date date) {
        super(id, name, duration);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
