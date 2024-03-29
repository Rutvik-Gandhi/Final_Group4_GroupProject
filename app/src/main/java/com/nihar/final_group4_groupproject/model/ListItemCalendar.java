package com.nihar.final_group4_groupproject.model;

public class ListItemCalendar {
    private String userId;
    private String name;
    private String date;
    private String type;

    public ListItemCalendar(String userId, String name, String date, String type) {
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
