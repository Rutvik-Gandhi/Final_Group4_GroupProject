package com.nihar.final_group4_groupproject.firebase;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.activities.categories.Announcement;
import com.nihar.final_group4_groupproject.model.ChannelContainer;
import com.nihar.final_group4_groupproject.model.NotificationDataContainer;
import com.nihar.final_group4_groupproject.notification.NotificationHandler;
import com.nihar.final_group4_groupproject.operations.Operations;
import com.nihar.final_group4_groupproject.prefs.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "fcmTAG";
    private JSONObject json;
    private NotificationManager notificationManager;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "token: " + token);

        SharedPref sharedPref = new SharedPref(getApplicationContext());
        sharedPref.setFCMToken(token); // working

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new Operations(getApplicationContext()).displayToast("token updated");
            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            json = new JSONObject(remoteMessage.getData().toString());

            // if JSON contains 'channel' object then only show notification
            if (json.has("channel")) {
                showNotification();
            }
            if (json.has("update_sqlite")) {
                if (json.getBoolean("update_sqlite")) {
                    updateUserListAndData();
                }
            }
            if (json.has("update_db")) {
                if (json.getBoolean("update_db")) {
                    updateUserListAndData();
                }
            }

            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "JSON data: " + json);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private JSONObject getObjectFromJson(String obj) throws JSONException {
        Log.d(TAG, json.getJSONObject(obj).toString());
        return json.getJSONObject(obj);
    }

    private void showNotification() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                NotificationHandler notificationHandler = new NotificationHandler(MyFirebaseMessagingService.this);
                notificationHandler.showNotification(channel(), notificationData(), new Announcement());
            }
        });
    }

    private NotificationDataContainer notificationData() {
        try {
            JSONObject data = getObjectFromJson("data");

            String title = data.getString("title");
            String message = data.getString("message");
            int id = 79;
            int icon = R.drawable.ic_baseline_announcement;
            int color = R.color.colorPrimary;

            Log.d(TAG, "title: " + title + ", msg: " + message);
            return new NotificationDataContainer(title, message, id, icon, color);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            return null;
        }
    }

    private ChannelContainer channel() {
        try {
            JSONObject channel = getObjectFromJson("channel");

            String id = channel.getString("id");
            String name = channel.getString("name");

            Log.d(TAG, "id: " + id + ", name: " + name);
            return new ChannelContainer(this, id, name);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            return null;
        }
    }

    private void updateUserListAndData() {
        try {
            new SQLiteBackgroundTask(getApplicationContext()).execute(json);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
