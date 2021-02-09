package com.tiagokontarski.mynewhabit.broadcast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.tiagokontarski.mynewhabit.R;
import com.tiagokontarski.mynewhabit.commoms.model.HabitModel;
import com.tiagokontarski.mynewhabit.ui.MainActivity;

public class Broadcast extends BroadcastReceiver {

    public static final String KEY_NOTIFICATION_ID = "notification_id";
    public static final String KEY_NOTIFICATION_MESSAGE = "notification_message";
    public static final String KEY_NOTIFICATION_TITLE = "notification_title";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, ii, 0);

        int id = intent.getIntExtra(KEY_NOTIFICATION_ID, 0);
        String message = intent.getStringExtra(KEY_NOTIFICATION_MESSAGE);
        String contentTitle = intent.getStringExtra(KEY_NOTIFICATION_TITLE);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = getNotification(contentTitle, message, context, manager, pIntent);
        manager.notify(id, notification);
    }

    private Notification getNotification(String contentTitle, String content, Context context, NotificationManager manager, PendingIntent intent) {

        Notification.Builder builder = new Notification.Builder(context.getApplicationContext())
                .setContentTitle(contentTitle)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(content))
                .setContentIntent(intent)
                .setTicker("Alerta")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId, "Channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        return builder.build();
    }
}
