package test.com.test.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import test.com.test.Recive;


public class BroadCastNotification extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // cancel any further alarms
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.cancel(alarmIntent);
        completeWakefulIntent(intent);
        // start the GcmTaskService
        Recive.showNotification(context);
    }
}
