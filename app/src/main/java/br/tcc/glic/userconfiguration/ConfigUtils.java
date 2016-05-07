package br.tcc.glic.userconfiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.tcc.glic.R;
import br.tcc.glic.domain.personagem.TipoPersonagem;

/**
 * Classe para acesso às configurações do sistema e do usuário
 * Created by André on 04/02/2016.
 */
public final class ConfigUtils {
    private final static int QTD_SELF_EVALUATION_CORRECT_ANSWERS_TO_TURN_OFF = 5;

    public static SharedPreferences getUserConfigurationFile(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getSystemConfigurationFile(Context context){
        return context.getSharedPreferences(
                context.getString(R.string.system_config_file_key),
                Context.MODE_PRIVATE
        );
    }

    public static List<RegisterDataField> getFieldsToShow(Context context){
        SharedPreferences prefs = getUserConfigurationFile(context);
        Set<String> fieldsConfig = prefs.getStringSet(context.getString(R.string.fields_to_show_config), new HashSet<String>(0));
        List<RegisterDataField> fields = new ArrayList<>(fieldsConfig.size());
        for (String fieldConfig :
                fieldsConfig) {
            fields.add(RegisterDataField.valueOf(fieldConfig));
        }

        Collections.sort(fields);

        return  fields;
    }

    public static boolean isAutoAvaliationOn(Context context) {
        SharedPreferences prefs = getUserConfigurationFile(context);
        return prefs.getBoolean(context.getString(R.string.self_evaluation_config), true) 
                || prefs.getBoolean(context.getString(R.string.self_evaluation_user_config), false);
    }

    public static void turnOffAutoAvaliation(Context context) {
        SharedPreferences prefs = getUserConfigurationFile(context);
        prefs
                .edit()
                .putBoolean(context.getString(R.string.self_evaluation_config), false)
                .apply();
    }

    public static void incrementSelfEvaluationCorrectAnswers(Context context) {
        SharedPreferences prefs = getSystemConfigurationFile(context);
        String configKey = context.getString(R.string.correct_self_evaluation_answers_config);
        int newValue = prefs.getInt(configKey, 0) + 1;
        prefs
                .edit()
                .putInt(configKey, newValue)
                .apply();

        if(newValue >= QTD_SELF_EVALUATION_CORRECT_ANSWERS_TO_TURN_OFF)
            turnOffAutoAvaliation(context);
    }

    public static void unlockReminders(Context context) {
        SharedPreferences sysPref = getSystemConfigurationFile(context);
        sysPref
            .edit()
            .putBoolean(context.getString(R.string.reminders_unlocked_config), true)
            .apply();

        SharedPreferences userPref = getUserConfigurationFile(context);
        userPref
                .edit()
                .putBoolean(context.getString(R.string.activate_notifications_config), true)
                .apply();

    }

    public static boolean isRemindersUnlocked(Context context) {
        SharedPreferences prefs = getSystemConfigurationFile(context);
        return prefs.getBoolean(context.getString(R.string.reminders_unlocked_config), false);
    }

    public static int getScore(Context context) {
        return getSystemConfigurationFile(context)
                .getInt(context.getString(R.string.score_config), 0);
    }

    public static int incrementScore(Context context, int points) {
        SharedPreferences prefs = getSystemConfigurationFile(context);
        int incremented = prefs.getInt(context.getString(R.string.score_config), 0) + points;
        prefs
            .edit()
            .putInt(context.getString(R.string.score_config), incremented)
            .apply();

        return incremented;
    }

    public static int incrementLevel(Context context, int numLevels) {
        SharedPreferences prefs = getSystemConfigurationFile(context);
        int incremented = prefs.getInt(context.getString(R.string.lvl_config), 0) + numLevels;
        prefs
                .edit()
                .putInt(context.getString(R.string.lvl_config), incremented)
                .apply();

        return incremented;
    }

    public static TipoPersonagem getCharacterType(Context context) {
        SharedPreferences prefs = getUserConfigurationFile(context);

        String charType = prefs.getString(context.getString(R.string.character_type_config), "");

        return TipoPersonagem.valueOf(charType);
    }

    public static int getLevel(Context context) {
        return getSystemConfigurationFile(context)
                .getInt(context.getString(R.string.lvl_config), 0);
    }
}
