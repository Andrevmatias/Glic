package br.tcc.glic.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.support.v4.app.Fragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Arrays;
import java.util.List;

import br.tcc.glic.LoginActivity;
import br.tcc.glic.R;
import br.tcc.glic.userconfiguration.RegisterDataField;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener, GoogleApiClient.ConnectionCallbacks {


    private GoogleApiClient mGoogleApiClient;
    private Preference prefLogout;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        initComponents();

        configureGoogleApi();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    private void initComponents() {
        prefLogout = findPreference(getString(R.string.logout_key));
        prefLogout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        handleSignOut();
                    }
                });

                return true;
            }
        });

        initListsEntries();
        initSummary(getPreferenceScreen());
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

    private void handleSignOut() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
    }

    private void initListsEntries() {
        MultiSelectListPreference fieldsToShowList = (MultiSelectListPreference)
                findPreference(getString(R.string.fields_to_show_config));

        RegisterDataField[] registerDataFields = RegisterDataField.values();

        String[] entries = new String[registerDataFields.length];
        String[] entryValues = new String[registerDataFields.length];

        for(int i = 0; i < registerDataFields.length; i++) {
            entries[i] = registerDataFields[i].getHumanReadableString(this.getActivity());
            entryValues[i] = registerDataFields[i].toString();
        }

        fieldsToShowList.setEntries(entries);
        fieldsToShowList.setEntryValues(entryValues);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePrefSummary(findPreference(key));
    }

    private void initSummary(Preference p) {
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) p;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummary(pGrp.getPreference(i));
            }
        } else {
            updatePrefSummary(p);
        }
    }

    private void updatePrefSummary(Preference p) {
        if (p instanceof ListPreference) {
            ListPreference listPref = (ListPreference) p;
            p.setSummary(listPref.getEntry());
        }
        if (p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            p.setSummary(editTextPref.getText());
        }
        if (p instanceof MultiSelectListPreference) {
            MultiSelectListPreference multiSelectPref = (MultiSelectListPreference) p;
            StringBuilder builder = new StringBuilder();
            String prefix = "";
            List<CharSequence> entryValuesList = Arrays.asList(multiSelectPref.getEntryValues());
            for(CharSequence entryValue : multiSelectPref.getValues()) {
                CharSequence entry =
                        multiSelectPref.getEntries()[entryValuesList.indexOf(entryValue)];
                builder.append(prefix);
                prefix = ", ";
                builder.append(entry);
            }

            p.setSummary(builder.toString());
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if(prefLogout != null)
            prefLogout.setEnabled(mGoogleApiClient.isConnected());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
