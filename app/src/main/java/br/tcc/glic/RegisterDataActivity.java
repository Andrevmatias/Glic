package br.tcc.glic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.exceptions.InvalidValueException;
import br.tcc.glic.fragments.RegisterCarbohydratesFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;
import br.tcc.glic.fragments.RegisterHbA1cFragment;
import br.tcc.glic.fragments.RegisterInsulinFragment;
import br.tcc.glic.userconfiguration.ConfigUtils;
import br.tcc.glic.userconfiguration.RegisterDataField;

public class RegisterDataActivity extends AchievementUnlockerActivity {

    private Map<RegisterDataField, Fragment> dataFieldFragments = new HashMap<>();
    private List<RegisterDataField> availableFields =
            new ArrayList<>(Arrays.asList(RegisterDataField.values()));
    private Toolbar toolbar;
    private View btnAddField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

        toolbar = (Toolbar) findViewById(R.id.toolbar_register_data);
        btnAddField = findViewById(R.id.btn_add_register_field);
        setSupportActionBar(toolbar);

        addDefaultFields();
    }

    public void addField(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final List<RegisterDataFieldListAdapterModel> models = convertToListAdapterModel(availableFields);
        ArrayAdapter<RegisterDataFieldListAdapterModel> adapter =
                new ArrayAdapter<>(this, android.R.layout.select_dialog_item, models);
        builder
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    addField(models.get(which).getRegisterDataField());
                    dialog.dismiss();
                }
            })
                .create()
                .show();
    }

    private List<RegisterDataFieldListAdapterModel> convertToListAdapterModel(List<RegisterDataField> fields) {
        List<RegisterDataFieldListAdapterModel> list = new ArrayList<>();
        for (RegisterDataField field :
                fields) {
            list.add(new RegisterDataFieldListAdapterModel(field, this));
        }
        return list;
    }

    private void addDefaultFields() {
        List<RegisterDataField> fieldsToShow = ConfigUtils.getFieldsToShow(this);
        Collections.sort(fieldsToShow, new Comparator<RegisterDataField>() {
            @Override
            public int compare(RegisterDataField lhs, RegisterDataField rhs) {
                return lhs.getOrder() - rhs.getOrder();
            }
        });


        for (RegisterDataField field :
                fieldsToShow) {
            addField(field);
        }


    }

    private void addField(RegisterDataField field) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

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
                RegisterInsulinFragment registerInsulinFragment = new RegisterInsulinFragment();
                dataFieldFragments.put(RegisterDataField.Insulin, registerInsulinFragment);
                transaction.add(R.id.register_data_container, registerInsulinFragment);
                break;
            case HbA1c:
                RegisterHbA1cFragment registerHbA1cFragment = new RegisterHbA1cFragment();
                dataFieldFragments.put(RegisterDataField.HbA1c, registerHbA1cFragment);
                transaction.add(R.id.register_data_container, registerHbA1cFragment);
                break;
        }

        availableFields.remove(field);

        if(availableFields.isEmpty())
            btnAddField.setVisibility(View.GONE);

        transaction.commit();
    }

    private void saveAll() {
        ArrayList<br.tcc.glic.domain.core.Registro> registeredEntries
                = new ArrayList<>(dataFieldFragments.size());

        for (RegisterDataField fieldKey :
                dataFieldFragments.keySet()) {

            br.tcc.glic.domain.core.Registro registro = null;
            switch (fieldKey)
            {
                case Glycemia:
                    registro = saveGlycemia();
                    break;
                case Carbohydrates:
                    registro = saveCarbohydrates();
                    break;
                case Insulin:
                    registro = saveInsulin();
                    break;
                case HbA1c:
                    registro = saveHbA1c();
                    break;
            }
            if(registro != null)
                registeredEntries.add(registro);
        }

        if(!registeredEntries.isEmpty()) {
            Toast.makeText(this, getString(R.string.result_saved), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, RemindersService.class);
            startService(intent);

            Intent returnIntent = new Intent();
            returnIntent.putExtra(getString(R.string.registered_entries_argument), registeredEntries);
            setResult(RESULT_OK, returnIntent);
        }
    }

    private br.tcc.glic.domain.core.Registro saveInsulin() {
        RegisterInsulinFragment fragmentInsulin =
                (RegisterInsulinFragment) dataFieldFragments.get(RegisterDataField.Insulin);

        AplicacaoInsulina aplicacaoInsulina = fragmentInsulin.getInsulin();

        if(aplicacaoInsulina != null) {
            if (!aplicacaoInsulina.isValid())
                throw new InvalidValueException(getString(R.string.invalid_insulin_value_message));

            new RegistrosService(this).registrarAplicacaoInsulina(aplicacaoInsulina);
            fragmentInsulin.reset();
            return aplicacaoInsulina;
        }

        return null;
    }

    private br.tcc.glic.domain.core.Registro saveHbA1c() {
        RegisterHbA1cFragment fragmentHbA1c =
                (RegisterHbA1cFragment) dataFieldFragments.get(RegisterDataField.HbA1c);

        HemoglobinaGlicada hbA1c = fragmentHbA1c.getHbA1c();

        if(hbA1c != null){
            if (!hbA1c.isValid())
                throw new InvalidValueException(getString(R.string.invalid_hba1c_value_message));

            new RegistrosService(this).registrarHemoglobinaGlicada(hbA1c);
            fragmentHbA1c.reset();
            return hbA1c;
        }

        return null;
    }

    private br.tcc.glic.domain.core.Registro saveGlycemia() {
        RegisterGlycemiaFragment fragmentGycemia =
                (RegisterGlycemiaFragment) dataFieldFragments.get(RegisterDataField.Glycemia);

        Glicemia glycemia = fragmentGycemia.getGlycemia();

        if(glycemia != null) {
            if (!glycemia.isValid())
                throw new InvalidValueException(getString(R.string.invalid_glycemia_value_message));

            new RegistrosService(this).registrarGlicemia(glycemia);
            fragmentGycemia.reset();
            return glycemia;
        }

        return null;
    }

    private br.tcc.glic.domain.core.Registro saveCarbohydrates() {
        RegisterCarbohydratesFragment fragmentCarbohydrates =
                (RegisterCarbohydratesFragment) dataFieldFragments.get(RegisterDataField.Carbohydrates);

        CarboidratoIngerido carboidratoIngerido = fragmentCarbohydrates.getCarbohydrate();

        if (carboidratoIngerido != null) {
            if (!carboidratoIngerido.isValid())
                throw new InvalidValueException(getString(R.string.invalid_carbohydrate_value_message));

            new RegistrosService(this).registrarCarboidratosIngeridos(carboidratoIngerido);
            fragmentCarbohydrates.reset();
            return carboidratoIngerido;
        }

        return null;
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
            //Os dados são salvos no finish
            finish();
            return true;
        }

        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void finish() {
        try {
            saveAll();
            super.finish();
        } catch (InvalidValueException ex) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.invalid_field_value_message_title)
                    .setMessage(ex.getMessage())
                    .create()
                    .show();
        }
    }

    private class RegisterDataFieldListAdapterModel {
        private final RegisterDataField registerDataField;
        private final Context context;

        public RegisterDataFieldListAdapterModel(RegisterDataField field, Context context) {
            this.registerDataField = field;
            this.context = context;
        }

        public RegisterDataField getRegisterDataField() {
            return registerDataField;
        }

        @Override
        public String toString() {
            return this.registerDataField.getHumanReadableString(this.context);
        }
    }
}
