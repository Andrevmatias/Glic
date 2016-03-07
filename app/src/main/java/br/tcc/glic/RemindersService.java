package br.tcc.glic;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tcc.glic.domain.services.LembretesService;

public class RemindersService extends IntentService {

    private static AlarmManager alarmManager;
    /**
     * Hora -> Minuto -> Intent
     */
    private static Map<Integer, Map<Integer, PendingIntent>> pendingIntentsMap;

    private final DateFormat dateFormat;
    private List<Calendar> lastReminderTimes;

    public RemindersService() {
        super("RemindersService");

        dateFormat = new SimpleDateFormat("HH:mm");
        lastReminderTimes = new ArrayList<>();

        if(pendingIntentsMap == null) {
            pendingIntentsMap = new HashMap<>();
            for (int i = 0; i <= 24; i++)
                pendingIntentsMap.put(i, new HashMap<Integer, PendingIntent>());
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if(alarmManager == null)
            alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LembretesService service = new LembretesService();

        List<Calendar> reminderTimes = service.getHorasLembretes();
        if (!areEqual(reminderTimes, lastReminderTimes)) {
            clearAlarms();
            configureNotifications(reminderTimes);
        }
    }

    private boolean areEqual(List<Calendar> reminderTimes, List<Calendar> lastReminderTimes) {
        if(reminderTimes.size() != lastReminderTimes.size())
            return false;

        for (int i = 0; i < reminderTimes.size(); i++) {
            Calendar current = reminderTimes.get(i);
            Calendar last = lastReminderTimes.get(i);

            if(current.get(Calendar.HOUR_OF_DAY) != last.get(Calendar.HOUR_OF_DAY))
                return false;

            if(current.get(Calendar.MINUTE) != last.get(Calendar.MINUTE))
                return false;
        }

        return true;
    }

    private void clearAlarms() {
        for (int i = 1; i <= 24; i++) {
            for (PendingIntent alarmIntent : pendingIntentsMap.get(i).values()) {
                alarmManager.cancel(alarmIntent);
            }
            pendingIntentsMap.get(i).clear();
        }

        lastReminderTimes.clear();
    }

    private void configureNotifications(List<Calendar> reminderTimes) {
        Intent intent = new Intent(this, NotificationsBroadcastReceiver.class);

        for (Calendar reminderTime : reminderTimes) {
            intent.putExtra(getString(R.string.reminder_time_extra),
                    dateFormat.format(reminderTime.getTime()));

            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            pendingIntentsMap
                    .get(reminderTime.get(Calendar.HOUR_OF_DAY))
                    .put(reminderTime.get(Calendar.MINUTE), alarmIntent);
            lastReminderTimes.add(reminderTime);

            alarmManager.setInexactRepeating(AlarmManager.RTC, reminderTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }
}
