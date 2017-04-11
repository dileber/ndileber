package com.drcosu.ndileber.mvp.data.model;

/**
 * Created by shidawei on 2017/2/24.
 */
public class SelectModel extends SModel{

    public SelectModel(){

    }

    public SelectModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
