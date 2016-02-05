package br.tcc.glic.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import br.tcc.glic.FirstConfigurationActivity;
import br.tcc.glic.MainActivity;
import br.tcc.glic.R;
import br.tcc.glic.userconfiguration.ConfigUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks {

    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private View sigininButton = null;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureGoogleApi();
    }

    private void configureGoogleApi() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .build();
    }

    private void startNextActivity() {
        Class<? extends Activity> activity =
                ConfigUtils.getUserConfigurationFile(getActivity()).getBoolean(getString(R.string.app_configured_config), false) ?
                        MainActivity.class : FirstConfigurationActivity.class;
        Intent nextIntent = new Intent(getActivity(), activity);
        startActivity(nextIntent);
    }

    @Override
    public void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initComponents(view);

        return view;
    }

    private void initComponents(View view) {
        this.sigininButton = view.findViewById(R.id.sign_in_button);
        sigininButton.setEnabled(mGoogleApiClient.isConnected());
        view.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSigninResult(GoogleSignInResult data) {
        if(data.isSuccess()) {
            startNextActivity();
        } else {
            Toast.makeText(getActivity(), getString(R.string.login_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSigninResult(result);
                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if(sigininButton != null)
            sigininButton.setEnabled(mGoogleApiClient.isConnected());
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
}
