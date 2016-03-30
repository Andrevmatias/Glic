package br.tcc.glic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.tcc.glic.adapters.FeedbackListAdapter;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.fragments.IndicatorsFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;
import br.tcc.glic.fragments.SelfEvaluationFragment;
import br.tcc.glic.userconfiguration.ConfigUtils;

public class MainActivity extends AppCompatActivity
        implements SelfEvaluationFragment.SelfEvaluationDismissListener{


    private static final int RC_DATA_REGISTERED = 2;

    private RegisterGlycemiaFragment fragmentGlycemia;
    private IndicatorsFragment fragmentIndicators;
    private RegistrosService registrarDadosService;

    public MainActivity() {
        registrarDadosService = new RegistrosService(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentGlycemia = (RegisterGlycemiaFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_register_glycemia_main);
        fragmentIndicators = (IndicatorsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_indicators_main);

        initComponents();
    }

    private void initComponents() {
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        ImageButton btnList = (ImageButton) findViewById(R.id.btn_list);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList();
            }
        });

        ImageButton btnAddEntry = (ImageButton) findViewById(R.id.btn_add_entry);
        btnAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddEntry();
            }
        });

        ImageButton btnCharts = (ImageButton) findViewById(R.id.btn_charts);
        btnCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCharts();
            }
        });
    }

    private void openAddEntry() {
        Intent intent = new Intent(this, RegisterDataActivity.class);
        startActivityForResult(intent, RC_DATA_REGISTERED);
    }

    private void openCharts() {
        Intent intent = new Intent(this, ChartsActivity.class);
        startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openList() {
        Intent intent = new Intent(this, EntriesListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(fragmentIndicators != null)
            fragmentIndicators.calcIndicators();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == RC_DATA_REGISTERED) {
            if(resultCode == RESULT_OK) {
                ArrayList<Registro> registros =
                        (ArrayList<Registro>) intent.getSerializableExtra(getString(R.string.registered_entries_argument));

                if(ConfigUtils.isAutoAvaliationOn(this) && SelfEvaluationFragment.shouldShowFor(registros))
                    askForSelfEvaluation(registros);
                else if(FeedbackListAdapter.suportsAny(registros))
                    showFeedback(registros);
            }
        }
    }

    private void askForSelfEvaluation(ArrayList<Registro> registros) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.registered_entries_argument), registros);

        DialogFragment dialog = new SelfEvaluationFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);
    }

    private void showFeedback(List<Registro> registros) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        FeedbackListAdapter adapter =
                new FeedbackListAdapter(this, registros);
        builder
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .create()
            .show();
    }

    public void addGlycemia(View view) {
        Glicemia glicemia = fragmentGlycemia.getGlycemia();

        registrarDadosService.registrarGlicemia(glicemia);

        Toast.makeText(this, getString(R.string.result_saved), Toast.LENGTH_LONG).show();

        ArrayList<Registro> registros = new ArrayList<>();
        registros.add(glicemia);

        if(ConfigUtils.isAutoAvaliationOn(this))
            askForSelfEvaluation(registros);
        else
            showFeedback(registros);

        Intent intent = new Intent(this, RemindersService.class);
        startService(intent);

        fragmentGlycemia.reset();
        fragmentIndicators.calcIndicators();
    }

    @Override
    public void onDismissSelfEvaluation(final List<Registro> evaluatedEntries) {
        if(!ConfigUtils.isAutoAvaliationOn(this))
            new AlertDialog.Builder(this)
                    .setMessage(R.string.self_evaluation_desactivation)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            showFeedback(evaluatedEntries);
                        }
                    })
                    .create()
                    .show();
        else
            showFeedback(evaluatedEntries);
    }
}
