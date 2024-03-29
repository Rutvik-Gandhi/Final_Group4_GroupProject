package com.nihar.final_group4_groupproject.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.activities.categories.CalendarEvents;
import com.nihar.final_group4_groupproject.model.ChannelContainer;
import com.nihar.final_group4_groupproject.model.NotificationDataContainer;
import com.nihar.final_group4_groupproject.notification.NotificationHandler;
import com.nihar.final_group4_groupproject.prefs.SharedPref;
import com.nihar.final_group4_groupproject.sqlite.MyDBHandler;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiverTAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Broadcast received");

        SharedPref sharedPref = new SharedPref(context);
        sharedPref.setBroadcastRegistrationFlag(true);

        MyDBHandler dbHandler = new MyDBHandler(context);

        if (sharedPref.getLoginStatus()) {
            if (!dbHandler.checkBirthdayToday().isEmpty()) {
                showNotification(context, context.getString(R.string.type_birthday));
            }
            if (!dbHandler.checkAnniversaryToday().isEmpty()) {
                showNotification(context, context.getString(R.string.type_anniversary));
            }
        }
    }

    private void showNotification(Context context, String type) {
        // default for birthday
        int notificationId = 89;
        String title = context.getString(R.string.birthday);
        String text = context.getString(R.string.tap_here_to_wish);
        int icon = R.drawable.ic_baseline_cake_filled;
        int color = R.color.colorRed;

        // changes value if anniversary
        if (type.equals(context.getString(R.string.type_anniversary))) {
            notificationId = 99;
            title = context.getString(R.string.anniversary);
            icon = R.drawable.ic_baseline_rings;
        }

        ChannelContainer channel = new ChannelContainer(
                context,
                context.getString(R.string.channel_birthday_anniversary_id),
                context.getString(R.string.channel_birthday_anniversary_name)
        );
        NotificationDataContainer notification = new NotificationDataContainer(
                title,
                text,
                notificationId,
                icon,
                color
        );
        NotificationHandler notificationHandler = new NotificationHandler(context);
        notificationHandler.showNotification(channel, notification, new CalendarEvents());
    }
}
