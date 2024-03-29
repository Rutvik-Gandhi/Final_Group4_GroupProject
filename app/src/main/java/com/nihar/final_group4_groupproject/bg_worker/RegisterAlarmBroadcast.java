package com.nihar.final_group4_groupproject.bg_worker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.nihar.final_group4_groupproject.broadcast.MyReceiver;
import com.nihar.final_group4_groupproject.prefs.SharedPref;

import java.util.Calendar;

public class RegisterAlarmBroadcast extends AsyncTask<Void, Void, Boolean> {

    public static final String TAG = "AlarmBroadcastTAG";
    private final Context context;

    public RegisterAlarmBroadcast(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 0);

        Log.d(TAG, Integer.toString(calendar.get(Calendar.YEAR)));
        Log.d(TAG, Integer.toString(calendar.get(Calendar.MONTH)));
        Log.d(TAG, Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        Log.d(TAG, Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)));
        Log.d(TAG, Integer.toString(calendar.get(Calendar.MINUTE)));
        Log.d(TAG, Integer.toString(calendar.get(Calendar.SECOND)));

        // creating a new intent specifying the broadcast receiver
        Intent intent = new Intent(context.getApplicationContext(), MyReceiver.class);
        // creating a pending intent using the intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(),
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(AppCompatActivity.ALARM_SERVICE);
        long time = calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000);

        // setting the repeating alarm that will be triggered every day
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                time,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
        return true;
    }

    @Override
    protected void onPostExecute(Boolean registrationStatus) {
        super.onPostExecute(registrationStatus);
        if (registrationStatus) {
            new SharedPref(context).setBroadcastRegistrationFlag(true);
            Log.d(TAG, "registered");
        }
    }
}
