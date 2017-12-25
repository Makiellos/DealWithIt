package com.example.lana.planitall.model;

/**
 * Created by lanan on 12/20/2017.
 */

public abstract class BaseTask {
    protected Integer id;
    protected String name;
    protected String duration;

    public BaseTask(Integer id, String name, String duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
