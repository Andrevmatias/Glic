package br.tcc.glic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import br.tcc.glic.fragments.configuration.AgeConfigurationFragment;
import br.tcc.glic.fragments.configuration.OnConfigurationSelectedListener;
import br.tcc.glic.fragments.configuration.TreatmentTypeConfigurationFragment;

public class FirstConfigurationActivity extends AppCompatActivity
    implements OnConfigurationSelectedListener
{
    private Map<String, Object> configurations;
    private Queue<Fragment> configFragments;

    public FirstConfigurationActivity() {
        configurations = new HashMap<>();
        initConfigFragments();
    }

    private void initConfigFragments() {
        configFragments = new LinkedList<>();
        configFragments.add(new TreatmentTypeConfigurationFragment());
        configFragments.add(new AgeConfigurationFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_configuration);

        showNextFragment();
    }

    private void showNextFragment() {
        if(!configFragments.isEmpty()) {
            showFragment(configFragments.poll());
        } else {
            saveConfigurations();
            goToMainActivity();
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first_config_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onConfigurationSelected(String key, Object value) {
        configurations.put(key, value);
        showNextFragment();
    }

    private void goToMainActivity() {
        Intent nextIntent = new Intent(this, MainActivity.class);
        startActivity(nextIntent);
    }

    private void saveConfigurations() {

    }
}
