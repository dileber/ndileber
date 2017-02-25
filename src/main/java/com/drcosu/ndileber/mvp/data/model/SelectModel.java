package com.drcosu.ndileber.mvp.data.model;

/**
 * Created by shidawei on 2017/2/24.
 */
public class SelectModel extends SModel{

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
