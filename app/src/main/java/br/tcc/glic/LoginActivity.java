package br.tcc.glic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import br.tcc.glic.userconfiguration.ConfigUtils;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureGoogleApi();
        trySilentSignIn();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void configureGoogleApi() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void trySilentSignIn() {
        OptionalPendingResult<GoogleSignInResult> pendingResult =
                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (pendingResult.isDone()) {
            handleSilentSigninResult(pendingResult.get());
        } else {
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSilentSigninResult(result);
                }
            });
        }
    }

    private void handleSilentSigninResult(GoogleSignInResult data) {
        if(data.isSuccess()) {
            startNextActivity();
        } else {
            setContentView(R.layout.activity_login);
        }
    }

    private void startNextActivity() {
        Class<? extends Activity> activity =
                ConfigUtils.getSystemConfigurationFile(this).getBoolean(getString(R.string.app_configured_config), false) ?
                        MainActivity.class : FirstConfigurationActivity.class;
        Intent nextIntent = new Intent(this, activity);
        startActivity(nextIntent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), getString(R.string.google_api_connect_error), Toast.LENGTH_LONG)
                .show();
    }
}

