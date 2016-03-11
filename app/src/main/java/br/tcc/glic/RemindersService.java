package br.tcc.glic;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.tcc.glic.domain.core.Lembrete;
import br.tcc.glic.domain.services.LembretesService;
import br.tcc.glic.userconfiguration.ConfigUtils;

public class RemindersService extends IntentService {

    private static AlarmManager alarmManager;
    private static DateFormat dateFormat;
    private static List<Calendar> lastReminderTimes;

    public RemindersService() {
        super("RemindersService");

        if(lastReminderTimes == null)
            lastReminderTimes = new ArrayList<>();

        if(dateFormat == null)
            dateFormat = new SimpleDateFormat("HH:mm");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if(alarmManager == null)
            alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences userConfig = ConfigUtils.getUserConfigurationFile(this);
        if(!userConfig.getBoolean(getString(R.string.activate_notifications_config), true))
            return;

        LembretesService service = new LembretesService();

        List<Lembrete> reminderTimes = service.getLembretes();
        if (!areEqual(reminderTimes, lastReminderTimes)) {
            clearAlarms();
            configureNotifications(reminderTimes);
        }
    }

    private boolean areEqual(List<Lembrete> reminderTimes, List<Calendar> lastReminderTimes) {
        if(reminderTimes.size() != lastReminderTimes.size())
            return false;

        for (int i = 0; i < reminderTimes.size(); i++) {
            Calendar current = Calendar.getInstance();
            current.setTime(reminderTimes.get(i).getHoraLembrete());
            Calendar last = lastReminderTimes.get(i);

            if(current.get(Calendar.HOUR_OF_DAY) != last.get(Calendar.HOUR_OF_DAY))
                return false;

            if(current.get(Calendar.MINUTE) != last.get(Calendar.MINUTE))
                return false;
        }

        return true;
    }

    private void clearAlarms() {
        Intent intent = new Intent(this, NotificationsBroadcastReceiver.class);

        PendingIntent current;
        for (
                int i = 0;
                (current = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_NO_CREATE)) != null;
                i++
        ) {
            alarmManager.cancel(current);
        }

        lastReminderTimes.clear();
    }

    private void configureNotifications(List<Lembrete> reminders) {
        for (int i = 0; i < reminders.size(); i++) {
            Lembrete reminder = reminders.get(i);

            Intent intent = new Intent(this, NotificationsBroadcastReceiver.class);

            intent.putExtra(getString(R.string.reminder_time_extra),
                    dateFormat.format(reminder.getHoraRegistro()));

            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, i, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Calendar reminderTime = Calendar.getInstance();
            reminderTime.setTime(reminder.getHoraLembrete());

            lastReminderTimes.add(reminderTime);

            alarmManager.setInexactRepeating(AlarmManager.RTC, reminderTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }
}
