package br.tcc.glic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tcc.glic.fragments.RegisterGlycemiaFragment;
import br.tcc.glic.userconfiguration.ConfigUtils;
import br.tcc.glic.userconfiguration.RegisterDataField;

public class RegisterDataActivity extends AppCompatActivity {

    private Map<RegisterDataField, Fragment> dataFieldFragments = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);

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
                /*
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
                    */
            }
        }

        transaction.commit();
    }
}
