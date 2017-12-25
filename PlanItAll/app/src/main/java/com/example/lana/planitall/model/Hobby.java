package com.example.lana.planitall.model;

import java.util.Date;

/**
 * Created by lanan on 12/20/2017.
 */

public class Hobby extends BaseTask {
    protected int period;

    public Hobby(Integer id, String name, String duration, int period) {
        super(id, name, duration);
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
