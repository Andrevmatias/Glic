package br.tcc.glic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.achievement.Achievements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.tcc.glic.adapters.FeedbackListAdapter;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.desafios.Desafio;
import br.tcc.glic.domain.enums.QualidadeRegistro;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.fragments.IndicatorsFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;
import br.tcc.glic.fragments.SelfEvaluationFragment;
import br.tcc.glic.userconfiguration.ConfigUtils;

public class MainActivity extends AchievementUnlockerActivity
        implements SelfEvaluationFragment.SelfEvaluationDismissListener {


    private static final int RC_DATA_REGISTERED = 2;
    private static final String LAST_MONTH_AVERAGE = "LAST_MONTH_AVERAGE";
    private static final String LAST_WEEK_AVERAGE = "LAST_WEEK_AVERAGE";

    private RegisterGlycemiaFragment fragmentGlycemia;
    private IndicatorsFragment fragmentIndicators;
    private RegistrosService registrarDadosService;
    private ImageButton btnAchievements;

    private int lastMonthGlycemiaAverage = 0, lastWeekGlycemiaAverage = 0;

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

        if(savedInstanceState != null) {
            lastMonthGlycemiaAverage = savedInstanceState.getInt(LAST_MONTH_AVERAGE, 0);
            lastWeekGlycemiaAverage = savedInstanceState.getInt(LAST_WEEK_AVERAGE, 0);
        }

        initComponents();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if(fragmentIndicators != null) {
            savedInstanceState.putInt(LAST_MONTH_AVERAGE, fragmentIndicators.getCurrentMonthAverageGlycemia());
            savedInstanceState.putInt(LAST_WEEK_AVERAGE, fragmentIndicators.getCurrentWeekAverageGlycemia());
        }

        super.onSaveInstanceState(savedInstanceState);
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

        ImageButton btnTestData = (ImageButton) findViewById(R.id.btn_test_data);
        btnTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTestData();
                Toast.makeText(view.getContext(), "Dados de teste adicionados", Toast.LENGTH_SHORT).show();
            }
        });

        btnAchievements = (ImageButton) findViewById(R.id.btn_achievements);
        btnAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAchievementsScreen();
            }
        });


    }

    private void addTestData() {
        new RegistrosService(this).registrarDadosDeTeste();
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
        if(fragmentIndicators != null) {
            fragmentIndicators.calcIndicators();

            verifyAverageImprovement(lastWeekGlycemiaAverage, fragmentIndicators.getCurrentWeekAverageGlycemia(), Desafio.MELHORAR_MEDIA_SEMANAL);
            verifyAverageImprovement(lastMonthGlycemiaAverage, fragmentIndicators.getCurrentMonthAverageGlycemia(), Desafio.MELHORAR_MEDIA_MENSAL);

            lastMonthGlycemiaAverage = fragmentIndicators.getCurrentMonthAverageGlycemia();
            lastWeekGlycemiaAverage = fragmentIndicators.getCurrentWeekAverageGlycemia();
        }
    }

    private void verifyAverageImprovement(int lastGlycemiaAverage, int currentAverageGlycemia, final Desafio achievement) {
        if(lastGlycemiaAverage == 0)
            return;

        if(QualidadeRegistro.getQualidadeGlicemia(lastGlycemiaAverage, this) != QualidadeRegistro.Bom
                && QualidadeRegistro.getQualidadeGlicemia(currentAverageGlycemia, this) == QualidadeRegistro.Bom){
            doWhenGoogleApiConnected(new Runnable() {
                @Override
                public void run() {
                    unlockAchievement(achievement);
                }
            });
        }
    }


    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == RC_DATA_REGISTERED) {
            if(resultCode == RESULT_OK) {
                ArrayList<Registro> registros =
                        (ArrayList<Registro>) intent.getSerializableExtra(getString(R.string.registered_entries_argument));

                checkEntriesToUnlockAchievements(registros);

                if(ConfigUtils.isAutoAvaliationOn(this) && SelfEvaluationFragment.shouldShowFor(registros))
                    askForSelfEvaluation(registros);
                else if(FeedbackListAdapter.suportsAny(registros))
                    showFeedback(registros);
            }
        }
    }

    private void checkEntriesToUnlockAchievements(final ArrayList<Registro> registros) {
        doWhenGoogleApiConnected(new Runnable() {
            @Override
            public void run() {
                boolean anyIsHbA1c = false;

                for(Registro registro : registros){
                    if(registro instanceof Glicemia) {
                        treatGlycemiaAchievements((Glicemia) registro);
                    } else if(registro instanceof CarboidratoIngerido){
                        unlockAchievement(Desafio.PRIMEIROS_CARBOIDRATOS);
                    } else if(registro instanceof AplicacaoInsulina){
                        unlockAchievement(Desafio.PRIMEIRA_INSULINA);
                    } else if(registro instanceof HemoglobinaGlicada) {
                        anyIsHbA1c = true;
                    }
                }

                if(anyIsHbA1c
                        && !isAchievementUnlocked(Desafio.REGISTRANDO_TUDO)
                        && isAchievementUnlocked(Desafio.PRIMEIRA_GLICEMIA)
                        && isAchievementUnlocked(Desafio.PRIMEIROS_CARBOIDRATOS)
                        && isAchievementUnlocked(Desafio.PRIMEIRA_INSULINA)){
                    unlockAchievement(Desafio.REGISTRANDO_TUDO);
                }
            }
        });
    }

    private void treatGlycemiaAchievements(Glicemia registro) {
        unlockAchievement(Desafio.PRIMEIRA_GLICEMIA);
        if(!isAchievementUnlocked(Desafio.VINTE_GLICEMIAS)) {
            incrementAchievementProgress(Desafio.VINTE_GLICEMIAS)
                    .setResultCallback(new ResultCallback<Achievements.UpdateAchievementResult>() {
                        @Override
                        public void onResult(final Achievements.UpdateAchievementResult updateAchievementResult) {
                            if (updateAchievementResult.getStatus().getStatusCode()
                                    == GamesStatusCodes.STATUS_CLIENT_RECONNECT_REQUIRED)
                                reconnectToGoogleApi();

                            doWhenGoogleApiConnected(new Runnable() {
                                @Override
                                public void run() {
                                    if (isAchievementUnlocked(Desafio.VINTE_GLICEMIAS))
                                        unlockReminders();
                                }
                            });

                        }
                    });
        }
        if(registro.getQualidade() == QualidadeRegistro.Bom)
            incrementAchievementProgress(Desafio.DEZ_GLICEMIAS_BOAS);

        int todayGlycemiasCount;
        if(!isAchievementUnlocked(Desafio.SEIS_GLICEMIAS_EM_UM_DIA)
                && (todayGlycemiasCount = getTodayGlycemiasCount()) != 0)
            setAchievementProgress(Desafio.SEIS_GLICEMIAS_EM_UM_DIA, todayGlycemiasCount);

        int lastDaysWithAtLeastOne;
        if(!isAchievementUnlocked(Desafio.UM_POR_DIA_EM_UMA_SEMANA)
                && (lastDaysWithAtLeastOne = getLastDaysWithAtLeast(1, 7)) != 0)
            setAchievementProgress(Desafio.UM_POR_DIA_EM_UMA_SEMANA, lastDaysWithAtLeastOne);

        int lastDaysWithAtLeastFour;
        if(!isAchievementUnlocked(Desafio.QUATRO_POR_DIA_EM_UMA_SEMANA)
                && (lastDaysWithAtLeastFour = getLastDaysWithAtLeast(4, 7)) != 0)
            setAchievementProgress(Desafio.QUATRO_POR_DIA_EM_UMA_SEMANA,  lastDaysWithAtLeastFour);
    }

    private int getLastDaysWithAtLeast(int minimumEntriesPerDay, int maximumCount) {
        int count = 0;

        RegistrosService service = new RegistrosService(this);

        Calendar dayStart = Calendar.getInstance();
        dayStart.set(Calendar.HOUR_OF_DAY, 0);
        dayStart.set(Calendar.MINUTE, 0);
        dayStart.set(Calendar.SECOND, 0);
        dayStart.set(Calendar.MILLISECOND, 0);

        Calendar dayEnd = Calendar.getInstance();
        dayEnd.set(Calendar.HOUR_OF_DAY, 23);
        dayEnd.set(Calendar.MINUTE, 59);
        dayEnd.set(Calendar.SECOND, 59);
        dayEnd.set(Calendar.MILLISECOND, 999);

        while (count < maximumCount){
            int dayEntries = service.listGlicemias(dayStart.getTime(), dayEnd.getTime()).size();
            if(dayEntries >= minimumEntriesPerDay)
                count++;
            else
                return count;

            dayStart.add(Calendar.DATE, -1);
            dayEnd.add(Calendar.DATE, -1);
        }

        return count;
    }

    private int getTodayGlycemiasCount() {
        RegistrosService service = new RegistrosService(this);

        Calendar dayStart = Calendar.getInstance();
        dayStart.set(Calendar.HOUR_OF_DAY, 0);
        dayStart.set(Calendar.MINUTE, 0);
        dayStart.set(Calendar.SECOND, 0);
        dayStart.set(Calendar.MILLISECOND, 0);

        Calendar dayEnd = Calendar.getInstance();
        dayEnd.set(Calendar.HOUR_OF_DAY, 23);
        dayEnd.set(Calendar.MINUTE, 59);
        dayEnd.set(Calendar.SECOND, 59);
        dayEnd.set(Calendar.MILLISECOND, 999);

        return service.listGlicemias(dayStart.getTime(), dayEnd.getTime()).size();
    }

    private void unlockReminders() {
        ConfigUtils.unlockReminders(this);

        new AlertDialog.Builder(this)
                .setTitle(R.string.reminder_notification_activation_title)
                .setMessage(R.string.reminder_notification_activation_text)
                .create()
                .show();
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

        checkEntriesToUnlockAchievements(registros);

        Intent intent = new Intent(this, RemindersService.class);
        startService(intent);

        fragmentGlycemia.reset();

        fragmentIndicators.calcIndicators();
        verifyAverageImprovement(lastWeekGlycemiaAverage, fragmentIndicators.getCurrentWeekAverageGlycemia(), Desafio.MELHORAR_MEDIA_SEMANAL);
        verifyAverageImprovement(lastMonthGlycemiaAverage, fragmentIndicators.getCurrentMonthAverageGlycemia(), Desafio.MELHORAR_MEDIA_MENSAL);
        lastWeekGlycemiaAverage = fragmentIndicators.getCurrentWeekAverageGlycemia();
        lastMonthGlycemiaAverage = fragmentIndicators.getCurrentMonthAverageGlycemia();
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

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);
        if(btnAchievements != null)
            btnAchievements.setVisibility(View.VISIBLE);

        unlockAchievement(Desafio.BEM_VINDO);
    }

    @Override
    protected void onDisconected() {
        super.onDisconected();

        if(btnAchievements != null)
            btnAchievements.setVisibility(View.GONE);
    }
}
