package br.tcc.glic.userconfiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.tcc.glic.R;

/**
 * Created by André on 04/02/2016.
 */
public final class ConfigUtils {
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
        Set<String> fieldsConfig = prefs.getStringSet(context.getString(R.string.fields_to_show_config), null);
        List<RegisterDataField> fields = new ArrayList<>(fieldsConfig.size());
        for (String fieldConfig :
                fieldsConfig) {
            fields.add(RegisterDataField.valueOf(fieldConfig));
        }

        Collections.sort(fields);

        return  fields;
    }
}
