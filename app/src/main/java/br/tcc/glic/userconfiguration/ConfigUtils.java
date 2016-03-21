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
        return prefs.getBoolean(context.getString(R.string.auto_avaliation_config), true);
    }

    public static void turnOffAutoAvaliation(Context context) {
        SharedPreferences prefs = getUserConfigurationFile(context);
        prefs
                .edit()
                .putBoolean(context.getString(R.string.auto_avaliation_config), false)
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
}
