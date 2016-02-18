package br.tcc.glic;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import br.tcc.glic.domain.core.RegistrarDadosService;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingConnectionFailure = false;
    private GoogleApiClient mGoogleGamesApiClient;
    private RegisterGlycemiaFragment fragmentGlycemia;
    private RegistrarDadosService registrarDadosService;

    public MainActivity() {
        registrarDadosService = new RegistrarDadosService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentGlycemia = (RegisterGlycemiaFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_register_glycemia_main);

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
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they

            }
        }
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

        fragmentGlycemia.reset();
    }
}
