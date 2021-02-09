package com.tiagokontarski.mynewhabit.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public NotificationManager(Context context) {
        this.context = context;
    }

    public void createOrUpdateNotification(HabitModel habitModel, DaysModel daysModel, KeysDataSource dataSource) {
        DaysOfWeek days = new DaysOfWeek();
        List<Integer> selectedDays = days.getSelectedDays(daysModel);

        for (int day : selectedDays) {
            createNotification(habitModel.getUid(), habitModel, day, dataSource);
        }
    }

    private void dropNotification(int id) {

    }

    private void createNotification(int id, HabitModel model, int weekNumber, KeysDataSource dataSource) {
        Intent notificationIntent = new Intent(context, Broadcast.class);
        notificationIntent.putExtra(Broadcast.KEY_NOTIFICATION_ID, 1);
        notificationIntent.putExtra(Broadcast.KEY_NOTIFICATION_MESSAGE, context.getString(R.string.notification_content, model.getDuration()));
        notificationIntent.putExtra(Broadcast.KEY_NOTIFICATION_TITLE, model.getTitle());

        NotificationKeysModel keysModel = new NotificationKeysModel();
        keysModel.setId(model.getUid());
        keysModel.setCreatedAt(System.currentTimeMillis());

        boolean response = dataSource.insert(keysModel) > 0;

        int _id = dataSource.getLastInserted().getKey();

        PendingIntent broadcast = PendingIntent.getBroadcast(context, _id,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        //days of week
        calendar.set(Calendar.DAY_OF_WEEK, weekNumber);

        //hour
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(model.getHour()));

        //minute
        calendar.set(Calendar.MINUTE, Integer.parseInt(model.getMinute()));

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
    }
}
