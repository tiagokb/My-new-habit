package com.tiagokontarski.mynewhabit.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.text.BoringLayout;
import android.util.Log;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.model.DaysModel;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.commoms.model.NotificationKeysModel;
import com.tiagokontarski.mynewhabit.commoms.utils.DaysOfWeek;
import com.tiagokontarski.mynewhabit.data.KeysDataSource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationManager {

    private final Context context;
    private final AlarmManager alarmManager;

    public NotificationManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void dropNotification(List<Integer> keys) {
        Intent notificationIntent = new Intent(context, Broadcast.class);

        for (int key : keys) {

            PendingIntent broadcast = PendingIntent.getBroadcast(context, key,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(broadcast);
        }
    }

    public int createNotification(
            int weekNumber,
            int hour,
            int minute,
            int key,
            String messageTitle,
            String message) {

        try {
            Intent notificationIntent = new Intent(context, Broadcast.class);
            notificationIntent.putExtra(Broadcast.KEY_NOTIFICATION_ID, 1);
            notificationIntent.putExtra(Broadcast.KEY_NOTIFICATION_TITLE, messageTitle);
            notificationIntent.putExtra(Broadcast.KEY_NOTIFICATION_MESSAGE, message);

            PendingIntent broadcast = PendingIntent.getBroadcast(context, key,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();

            //days of week
            calendar.set(Calendar.DAY_OF_WEEK, weekNumber);

            //hour
            calendar.set(Calendar.HOUR_OF_DAY, hour);

            //minute
            calendar.set(Calendar.MINUTE, minute);

            //second
            calendar.set(Calendar.SECOND, 0);

            //millisecon
            calendar.set(Calendar.MILLISECOND, 0);

            long interval = 7 * 24 * 60 * 60 * 1000;

            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);

            if (calendar.before(now)) {
                calendar.add(Calendar.DATE, 7);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, broadcast);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
