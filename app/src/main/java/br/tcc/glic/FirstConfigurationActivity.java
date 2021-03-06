package br.tcc.glic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import br.tcc.glic.domain.core.IntervaloGlicemia;
import br.tcc.glic.domain.enums.TipoGlicemia;
import br.tcc.glic.domain.enums.TipoTerapia;
import br.tcc.glic.domain.personagem.TipoPersonagem;
import br.tcc.glic.domain.services.TipoInsulinaService;
import br.tcc.glic.fragments.configuration.AgeConfigurationFragment;
import br.tcc.glic.fragments.configuration.CharacterConfigurationFragment;
import br.tcc.glic.fragments.configuration.OnConfigurationSelectedListener;
import br.tcc.glic.fragments.configuration.TreatmentTypeConfigurationFragment;
import br.tcc.glic.userconfiguration.ConfigUtils;
import br.tcc.glic.userconfiguration.RegisterDataField;

public class FirstConfigurationActivity extends AppCompatActivity
    implements OnConfigurationSelectedListener {
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
        configFragments.add(new CharacterConfigurationFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_configuration);

        showNextFragment();
    }

    private void showNextFragment() {
        if (!configFragments.isEmpty()) {
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
        SharedPreferences.Editor sharedPreferencesEditor =
                ConfigUtils.getUserConfigurationFile(this).edit();

        saveAgeConfigurations(sharedPreferencesEditor);
        saveTreatmentConfigurations(sharedPreferencesEditor);
        saveCharacterConfigurations(sharedPreferencesEditor);

        sharedPreferencesEditor.apply();

        new TipoInsulinaService().registrarInsulinasPadrao();

        ConfigUtils.getSystemConfigurationFile(this)
                .edit()
                .putBoolean(getString(R.string.app_configured_config), true)
                .apply();
    }

    private void saveCharacterConfigurations(SharedPreferences.Editor sharedPreferencesEditor) {
        String key = getString(R.string.character_config);
        TipoPersonagem value = (TipoPersonagem) configurations.get(key);

        sharedPreferencesEditor
                .putString(getString(R.string.character_type_config), value.toString());
    }

    private void saveTreatmentConfigurations(SharedPreferences.Editor sharedPreferencesEditor) {
        String key = getString(R.string.therapy_type_config);
        TipoTerapia value = (TipoTerapia) configurations.get(key);

        RegisterDataField[] campos = RegisterDataField.getByTipoTerapia(value);

        Set<String> camposString = new HashSet<>();
        for(RegisterDataField campo : campos)
            camposString.add(campo.toString());

        sharedPreferencesEditor
                .putStringSet(getString(R.string.fields_to_show_config), camposString);
    }

    private void saveAgeConfigurations(SharedPreferences.Editor sharedPreferencesEditor) {
        String key = getString(R.string.age_config);
        int value = (Integer)configurations.get(key);

        IntervaloGlicemia intervaloPre =
                IntervaloGlicemia.getByIdade(value, TipoGlicemia.PrePrandial);
        IntervaloGlicemia intervaloPos =
                IntervaloGlicemia.getByIdade(value, TipoGlicemia.PosPrandial);

        sharedPreferencesEditor
                .putString(getString(R.string.min_pre_glycemia_config),
                        String.valueOf(intervaloPre.getLimiteMinimo()));
        sharedPreferencesEditor
                .putString(getString(R.string.max_pre_glycemia_config),
                        String.valueOf(intervaloPre.getLimiteMaximo()));

        sharedPreferencesEditor
                .putString(getString(R.string.min_pos_glycemia_config),
                        String.valueOf(intervaloPos.getLimiteMinimo()));
        sharedPreferencesEditor
                .putString(getString(R.string.max_pos_glycemia_config),
                        String.valueOf(intervaloPos.getLimiteMaximo()));
    }
}
