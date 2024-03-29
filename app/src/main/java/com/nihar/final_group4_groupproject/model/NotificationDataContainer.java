package com.nihar.final_group4_groupproject.model;

import com.nihar.final_group4_groupproject.R;

public class NotificationDataContainer {
    private String title;
    private String message;
    private int id;
    private int icon;
    private int color;

    public NotificationDataContainer(String title, String message, int id, int icon, int color) {
        this.title = title;
        this.message = message;
        this.id = id;
        this.icon = icon;
        this.color = color;
    }

    public NotificationDataContainer(String title, String message) {
        this.title = title;
        this.message = message;
        this.id = 999;
        this.icon = R.drawable.ic_baseline_check_circle_filled;
        this.color = R.color.colorPrimary;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public int getColor() {
        return color;
    }

}
