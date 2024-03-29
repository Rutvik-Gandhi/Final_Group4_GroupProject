package com.nihar.final_group4_groupproject.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nihar.final_group4_groupproject.bg_worker.RegisterAlarmBroadcast;
import com.nihar.final_group4_groupproject.prefs.SharedPref;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // alarm repeating broadcast automatically disabled. So, have to make changes in Pref file.
        new SharedPref(context).setBroadcastRegistrationFlag(false);

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            new RegisterAlarmBroadcast(context).execute();
        }
    }
}
