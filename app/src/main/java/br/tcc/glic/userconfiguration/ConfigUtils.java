package br.tcc.glic.userconfiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
}
