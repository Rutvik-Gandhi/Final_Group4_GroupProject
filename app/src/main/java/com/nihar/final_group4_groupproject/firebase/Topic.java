package com.nihar.final_group4_groupproject.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.nihar.final_group4_groupproject.R;

public class Topic {
    private static final String TAG = "TopicTAG";
    private final String global;
    private final String login;

    public Topic(Context context) {
        global = context.getString(R.string.topic_global);
        login = context.getString(R.string.topic_login);
    }

    public void subscribe(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnCompleteListener(task -> {
            String message = task.isSuccessful() ? topic + " subscribed" : topic + " subscribe failed";
            Log.d(TAG, message);
        });
    }

    public void unsubscribe(String topic) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnCompleteListener(task -> {
            String message = task.isSuccessful() ? topic + " unsubscribed" : topic + " unsubscribe failed";
            Log.d(TAG, message);
        });
    }
}
