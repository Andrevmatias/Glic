package br.tcc.glic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

/**
 * Receiver para notificações periódicas
 * Created by André on 06/03/2016.
 */
public class NotificationsBroadcastReceiver extends BroadcastReceiver {
    private final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        notifyReminder(context, intent.getStringExtra(context.getString(R.string.reminder_time_extra)));
    }

    private void notifyReminder(Context context, String time) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_reminder_notification)
                        .setContentTitle(context.getString(R.string.reminder_notification_title))
                        .setContentText(String.format(context.getString(R.string.reminder_notification_text), time))
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setVibrate(new long[]{500, 500, 500, 500})
                        .setLights(Color.RED, 1000, 1000)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(String.format(context.getString(R.string.reminder_notification_big_text), time)))
                        .setAutoCancel(true);

        Intent intent = new Intent(context, RegisterDataActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
