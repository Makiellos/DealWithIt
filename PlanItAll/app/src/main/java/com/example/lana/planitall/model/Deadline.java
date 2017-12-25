package com.example.lana.planitall.model;

import java.util.Date;

/**
 * Created by lanan on 12/20/2017.
 */

public class Deadline extends BaseTask {

    protected Date fromDate;
    protected Date toDate;

    public Deadline(Integer id, String name, String duration, Date fromDate, Date toDate) {
        super(id, name, duration);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
