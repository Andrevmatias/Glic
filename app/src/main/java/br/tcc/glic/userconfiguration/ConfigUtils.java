package br.tcc.glic.userconfiguration;

import android.content.Context;
import android.content.SharedPreferences;

import br.tcc.glic.R;

/**
 * Created by André on 04/02/2016.
 */
public final class ConfigUtils {
    public static SharedPreferences getUserConfigurationFile(Context context){
        return context.getSharedPreferences(
                context.getString(R.string.user_config_file_key),
                Context.MODE_PRIVATE
        );
    }
}
