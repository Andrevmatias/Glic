package br.tcc.glic;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.Achievements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import br.tcc.glic.domain.desafios.Desafio;

/**
 * Classe base para atividades que desbloqueiam Achievements
 * Created by André on 12/04/2016.
 */
public abstract class AchievementUnlockerActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1, RC_ACHIEVEMENTS = 3;
    private HashMap<Desafio, String> achievementIds = new HashMap<>();
    private HashMap<String, Desafio> idsAchievements = new HashMap<>();
    private GoogleApiClient googleApiClient;
    private boolean resolvingConnectionFailure;
    private static HashMap<Desafio, Boolean> achievementsStatus;
    private Queue<Runnable> toRunWhenConnect = new LinkedList<>();


    public AchievementUnlockerActivity() {
        setAchievementIds();
    }

    protected void setAchievementIds(){
        achievementIds.put(Desafio.BEM_VINDO,                    "CgkIkv_fwZELEAIQAA");
        achievementIds.put(Desafio.PRIMEIRA_GLICEMIA,            "CgkIkv_fwZELEAIQAQ");
        achievementIds.put(Desafio.PRIMEIROS_CARBOIDRATOS,       "CgkIkv_fwZELEAIQAg");
        achievementIds.put(Desafio.PRIMEIRA_INSULINA,            "CgkIkv_fwZELEAIQAw");
        achievementIds.put(Desafio.REGISTRANDO_TUDO,             "CgkIkv_fwZELEAIQBA");
        achievementIds.put(Desafio.DEZ_GLICEMIAS_BOAS,           "CgkIkv_fwZELEAIQBw");
        achievementIds.put(Desafio.CINCO_AVALIACOES_CORRETAS,    "CgkIkv_fwZELEAIQCA");
        achievementIds.put(Desafio.UM_POR_DIA_EM_UMA_SEMANA,     "CgkIkv_fwZELEAIQCQ");
        achievementIds.put(Desafio.QUATRO_POR_DIA_EM_UMA_SEMANA, "CgkIkv_fwZELEAIQCg");
        achievementIds.put(Desafio.MELHORAR_MEDIA_SEMANAL,       "CgkIkv_fwZELEAIQCw");
        achievementIds.put(Desafio.MELHORAR_MEDIA_MENSAL,        "CgkIkv_fwZELEAIQDA");
        achievementIds.put(Desafio.SEIS_GLICEMIAS_EM_UM_DIA,     "CgkIkv_fwZELEAIQDQ");
        achievementIds.put(Desafio.VINTE_GLICEMIAS,              "CgkIkv_fwZELEAIQDg");

        for(Map.Entry<Desafio, String> entry : achievementIds.entrySet()){
            idsAchievements.put(entry.getValue(), entry.getKey());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGoogleApi();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            if(resultCode == RESULT_OK)
                googleApiClient.connect();
        }
    }

    private void initGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

    }

    protected void reconnectToGoogleApi(){
        if(googleApiClient != null)
            googleApiClient.reconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(googleApiClient != null)
            googleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    protected void openAchievementsScreen() {
        Intent intent = Games.Achievements.getAchievementsIntent(googleApiClient);
        startActivityForResult(intent, RC_ACHIEVEMENTS);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (resolvingConnectionFailure)
            return;

        resolvingConnectionFailure = true;

        try {
            connectionResult.startResolutionForResult(this, RC_SIGN_IN);
        } catch (IntentSender.SendIntentException e) {

            onDisconected();

            Toast.makeText(this, R.string.google_api_connect_error, Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }

    }

    public void unlockAchievement(Desafio desafio){
        Games.Achievements.unlock(googleApiClient, achievementIds.get(desafio));
    }

    public PendingResult<Achievements.UpdateAchievementResult> incrementAchievementProgress(Desafio desafio){
        return Games.Achievements.incrementImmediate(googleApiClient, achievementIds.get(desafio), 1);
    }

    public PendingResult<Achievements.UpdateAchievementResult> setAchievementProgress(Desafio desafio, int progress){
        return Games.Achievements.setStepsImmediate(googleApiClient, achievementIds.get(desafio), progress);
    }

    public void doWhenGoogleApiConnected(Runnable runnable){
        if(googleApiClient.isConnected()) {
            runnable.run();
        } else {
            googleApiClient.reconnect();
            toRunWhenConnect.add(runnable);
        }
    }

    public boolean isAchievementUnlocked(Desafio desafio){
        return achievementsStatus.get(desafio);
    }

    @Override
    public void onConnected(Bundle bundle) {
        configureAchievementsStatus();

        while (!toRunWhenConnect.isEmpty())
            toRunWhenConnect.poll().run();
    }

    private void configureAchievementsStatus() {
        Games.Achievements.load(googleApiClient,false).setResultCallback(new ResultCallback<Achievements.LoadAchievementsResult>() {
            @Override
            public void onResult(Achievements.LoadAchievementsResult result) {
                achievementsStatus = new HashMap<>(result.getAchievements().getCount());
                Iterator<Achievement> iterator = result.getAchievements().iterator();
                Achievement achievement;
                while (iterator.hasNext()) {
                    achievement = iterator.next();
                    achievementsStatus.put(idsAchievements.get(achievement.getAchievementId()),
                            achievement.getState() == Achievement.STATE_UNLOCKED);
                }
            }
        });
    }

    protected void onDisconected() {
        //To be overriden by subclass
    }

}
