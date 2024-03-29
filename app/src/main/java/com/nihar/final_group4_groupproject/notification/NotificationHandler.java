package com.nihar.final_group4_groupproject.notification;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.model.ChannelContainer;
import com.nihar.final_group4_groupproject.model.NotificationDataContainer;

import java.util.ArrayList;

public class NotificationHandler {
    private Context context;
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_REQUEST_CODE = 101;

    public NotificationHandler(Context context) {
        this.context = context;
    }

    public void showNotification(ChannelContainer channel, NotificationDataContainer notification, Activity activity) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel(channel);

        Intent intent = new Intent(context, activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context,
                context.getString(R.string.channel_default_id)
        )
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getMessage())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(notification.getIcon())
                .setColor(context.getResources().getColor(notification.getColor()))
                .setChannelId(channel.getId());

        notificationManager.notify(notification.getId(), builder.build());
    }

    private void createChannel(ChannelContainer channel) {
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel defaultChannel = new NotificationChannel(
                context.getString(R.string.channel_default_id),
                context.getString(R.string.channel_default_name),
                importance
        );

        NotificationChannel notificationChannel = new NotificationChannel(
                channel.getId(),
                channel.getName(),
                importance
        );

        ArrayList<NotificationChannel> channelList = new ArrayList<>();
        channelList.add(defaultChannel);
        channelList.add(notificationChannel);

        notificationManager.createNotificationChannels(channelList);
    }

}