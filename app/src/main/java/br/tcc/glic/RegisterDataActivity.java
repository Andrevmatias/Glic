package br.tcc.glic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.services.RegistrarDadosService;
import br.tcc.glic.fragments.RegisterCarbohydratesFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;
import br.tcc.glic.fragments.RegisterHbA1cFragment;
import br.tcc.glic.userconfiguration.ConfigUtils;
import br.tcc.glic.userconfiguration.RegisterDataField;

public class RegisterDataActivity extends AppCompatActivity {

    private Map<RegisterDataField, Fragment> dataFieldFragments = new HashMap<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        toolbar = (Toolbar) findViewById(R.id.toolbar_register_data);
        setSupportActionBar(toolbar);

        addDefaultFields();
    }

    private void addDefaultFields() {
        List<RegisterDataField> fieldsToShow = ConfigUtils.getFieldsToShow(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for (RegisterDataField field :
                fieldsToShow) {
            switch (field){
                case Glycemia:
                    RegisterGlycemiaFragment registerGlycemiaFragment = new RegisterGlycemiaFragment();
                    Bundle args = new Bundle();
                    args.putBoolean(getString(R.string.show_date_bundle_argument), true);
                    registerGlycemiaFragment.setArguments(args);
                    dataFieldFragments.put(RegisterDataField.Glycemia, registerGlycemiaFragment);
                    transaction.add(R.id.register_data_container, registerGlycemiaFragment);
                    break;
                case Carbohydrates:
                    RegisterCarbohydratesFragment registerCarbohydratesFragment = new RegisterCarbohydratesFragment();
                    dataFieldFragments.put(RegisterDataField.Carbohydrates, registerCarbohydratesFragment);
                    transaction.add(R.id.register_data_container, registerCarbohydratesFragment);
                    break;
                case Insulin:
                                    /*
                    RegisterInsulinFragment registerInsulinFragment = new RegisterInsulinFragment();
                    dataFieldFragments.put(RegisterDataField.Insulin, registerInsulinFragment);
                    transaction.add(R.id.register_data_container, registerInsulinFragment);
                    break;
                    */
                    break;
                case HbA1c:
                    RegisterHbA1cFragment registerHbA1cFragment = new RegisterHbA1cFragment();
                    dataFieldFragments.put(RegisterDataField.HbA1c, registerHbA1cFragment);
                    transaction.add(R.id.register_data_container, registerHbA1cFragment);
                    break;
            }
        }

        transaction.commit();
    }

    private void saveAll() {
        for (RegisterDataField fieldKey :
                dataFieldFragments.keySet()) {
            switch (fieldKey)
            {
                case Glycemia:
                    saveGlycemia();
                    break;
                case Carbohydrates:
                    saveCarbohydrates();
                    break;
                case Insulin:
                    break;
                case HbA1c:
                    saveHbA1c();
                    break;
            }
        }

        Toast.makeText(this, getString(R.string.result_saved), Toast.LENGTH_LONG).show();

        goToMain();
    }

    private void saveHbA1c() {
        RegisterHbA1cFragment fragmentHbA1c =
                (RegisterHbA1cFragment) dataFieldFragments.get(RegisterDataField.HbA1c);

        HemoglobinaGlicada hbA1c = fragmentHbA1c.getHbA1c();
        if(hbA1c != null)
            new RegistrarDadosService().registrarHemoglobinaGlicada(hbA1c);
    }

    private void goToMain() {
        Intent nextIntent = new Intent(this, MainActivity.class);
        startActivity(nextIntent);
    }

    private void saveGlycemia() {
        RegisterGlycemiaFragment fragmentGycemia =
                (RegisterGlycemiaFragment) dataFieldFragments.get(RegisterDataField.Glycemia);

        Glicemia glycemia = fragmentGycemia.getGlycemia();
        if(glycemia != null)
            new RegistrarDadosService().registrarGlicemia(glycemia);
    }

    private void saveCarbohydrates() {
        RegisterCarbohydratesFragment fragmentCarbohydrates =
                (RegisterCarbohydratesFragment) dataFieldFragments.get(RegisterDataField.Carbohydrates);

        CarboidratoIngerido carboidratoIngerido = fragmentCarbohydrates.getCarbohydrate();
        if(carboidratoIngerido != null)
            new RegistrarDadosService().registrarCarboidratosIngeridos(carboidratoIngerido);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.register_data_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveAll();
            return true;
        }

        return false;
    }
}
