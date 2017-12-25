package com.example.lana.planitall.model;

/**
 * Created by lanan on 12/20/2017.
 */

public class Subtask {

    protected int id;
    protected String text;
    protected int parent_id;
    protected int taskType;

    public Subtask(int id, String text, int parent_id, int taskType) {
        this.id = id;
        this.text = text;
        this.parent_id = parent_id;
        this.taskType = taskType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
}
