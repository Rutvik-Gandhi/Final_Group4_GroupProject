package com.nihar.final_group4_groupproject.model;

import android.content.Context;

import com.nihar.final_group4_groupproject.R;

public class ChannelContainer {
    private Context context;
    private String id;
    private String name;

    public ChannelContainer(Context context) {
        this.context = context;
        this.id = context.getString(R.string.channel_default_id);
        this.name = context.getString(R.string.channel_default_name);
    }

    public ChannelContainer(Context context, String id, String name) {
        this.context = context;
        this.id = id;
        this.name = name;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
