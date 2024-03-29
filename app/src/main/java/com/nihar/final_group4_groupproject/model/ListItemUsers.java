package com.nihar.final_group4_groupproject.model;

public class ListItemUsers {
    private String userId;
    private String name;
    private String position;

    public ListItemUsers(String userId, String name, String position) {
        this.userId = userId;
        this.name = name;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
