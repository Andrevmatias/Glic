package br.tcc.glic;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.enums.TipoIndicador;
import br.tcc.glic.domain.services.IndicadoresService;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.fragments.IndicatorsFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private static final int RC_SIGN_IN = 1, RC_HBA1C_REGISTERED = 2;
    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingConnectionFailure = false;
    private GoogleApiClient mGoogleGamesApiClient;
    private RegisterGlycemiaFragment fragmentGlycemia;
    private IndicatorsFragment fragmentIndicators;
    private RegistrosService registrarDadosService;

    public MainActivity() {
        registrarDadosService = new RegistrosService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentGlycemia = (RegisterGlycemiaFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_register_glycemia_main);
        fragmentIndicators = (IndicatorsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_indicators_main);

        initApiClients();
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
    }

    private void openAddEntry() {
        Intent intent = new Intent(this, RegisterDataActivity.class);
        startActivityForResult(intent, RC_HBA1C_REGISTERED);
    }

    private void initApiClients() {
        mGoogleApiClient = getGoogleApiClient();

        mGoogleGamesApiClient = new GoogleApiClient.Builder(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleGamesApiClient.connect();
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

    @NonNull
    private GoogleApiClient getGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        return new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow

            mResolvingConnectionFailure = true;

        try {
            connectionResult.startResolutionForResult(this, RC_SIGN_IN);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }


    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleGamesApiClient.connect();
            }
        } else if (requestCode == RC_HBA1C_REGISTERED) {
            if(resultCode == RESULT_OK)
                showEstimatedAverageGlycemia();
        }
    }

    private void showEstimatedAverageGlycemia() {
        double eag = new IndicadoresService()
                .getIndicador(TipoIndicador.GlicemiaMediaEstimada).getValor();

        new AlertDialog.Builder(this)
                .setTitle(R.string.estimated_average_glycemia)
                .setMessage(getString(R.string.your_estimated_average_glycemia_is)
                        + " " + String.valueOf((int)Math.floor(eag)))
                .create()
                .show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Games.Achievements.unlock(mGoogleGamesApiClient, "CgkIkv_fwZELEAIQAA");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, Integer.toString(i), Toast.LENGTH_LONG).show();
    }

    public void addGlycemia(View view) {
        Glicemia glicemia = fragmentGlycemia.getGlycemia();

        registrarDadosService.registrarGlicemia(glicemia);

        Toast.makeText(this, getString(R.string.result_saved), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, RemindersService.class);
        startService(intent);

        fragmentGlycemia.reset();
        fragmentIndicators.calcIndicators();
    }
}
