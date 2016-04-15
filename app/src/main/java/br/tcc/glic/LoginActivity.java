package br.tcc.glic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.tcc.glic.userconfiguration.ConfigUtils;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startNextActivity();
    }

    private void startNextActivity() {
        Class<? extends Activity> activity =
                ConfigUtils.getSystemConfigurationFile(this).getBoolean(getString(R.string.app_configured_config), false) ?
                        MainActivity.class : FirstConfigurationActivity.class;
        Intent nextIntent = new Intent(this, activity);
        startActivity(nextIntent);
    }
}

