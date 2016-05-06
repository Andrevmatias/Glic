package br.tcc.glic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.achievement.Achievements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import br.tcc.glic.adapters.FeedbackListAdapter;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Indicador;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.desafios.Desafio;
import br.tcc.glic.domain.enums.EstadoPersonagem;
import br.tcc.glic.domain.enums.QualidadeRegistro;
import br.tcc.glic.domain.personagem.TipoPersonagem;
import br.tcc.glic.domain.services.EstadoPersonagemService;
import br.tcc.glic.domain.services.PontuacaoService;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.fragments.CharacterStateDialogFragment;
import br.tcc.glic.fragments.IndicatorsFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;
import br.tcc.glic.fragments.SelfEvaluationFragment;
import br.tcc.glic.userconfiguration.ConfigUtils;
import br.tcc.glic.views.SpriteView;

public class MainActivity extends AchievementUnlockerActivity
        implements SelfEvaluationFragment.SelfEvaluationDismissListener {


    private static final int RC_DATA_REGISTERED = 2;
    private static final String LAST_MONTH_AVERAGE = "LAST_MONTH_AVERAGE";
    private static final String LAST_WEEK_AVERAGE = "LAST_WEEK_AVERAGE";

    private RegisterGlycemiaFragment fragmentGlycemia;
    private IndicatorsFragment fragmentIndicators;
    private RegistrosService registrarDadosService;
    private ImageButton btnAchievements;
    private TextView txtPoints;
    private TextView txtScoreUp;
    private Animation scoreUpAnimation;
    private SpriteView spriteCharacter;

    private Queue<Runnable> toRunWhenDismissFeedback = new LinkedBlockingQueue<>();

    private int lastMonthGlycemiaAverage = 0, lastWeekGlycemiaAverage = 0;
    private EstadoPersonagem characterState = EstadoPersonagem.Bem;
    private RoundCornerProgressBar pbrCharacterPoints;
    private TextView txtLevel;
    private TextView txtLvlUp;

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
                /*
                goToEvolutionActivity();*/

            }
        });

        btnAchievements = (ImageButton) findViewById(R.id.btn_achievements);
        btnAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAchievementsScreen();
            }
        });

        txtPoints = (TextView) findViewById(R.id.txt_points);
        txtScoreUp = (TextView) findViewById(R.id.txt_score_up);

        scoreUpAnimation = AnimationUtils.loadAnimation(this, R.anim.score_up);

        spriteCharacter = (SpriteView) findViewById(R.id.sprite_character);
        spriteCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCharacterStateDialog();
            }
        });

        reloadCharacterSprite(this);

        pbrCharacterPoints = (RoundCornerProgressBar) findViewById(R.id.pbr_character_points);
        int characterColor = getCharacterColor();
        pbrCharacterPoints.setProgressColor(characterColor);
        int currentLevel = ConfigUtils.getLevel(this);
        float pointsToPreviousLevel = getPointsToNextLevel(currentLevel - 1);
        pbrCharacterPoints.setMax(getPointsToNextLevel(currentLevel) - pointsToPreviousLevel);
        pbrCharacterPoints.setProgress(ConfigUtils.getScore(this) - pointsToPreviousLevel);

        txtLevel = (TextView) findViewById(R.id.txt_level_number);
        txtLevel.setText(String.valueOf(currentLevel));
        txtLevel.setTextColor(characterColor);
        ((TextView) findViewById(R.id.txt_level)).setTextColor(characterColor);
        txtLvlUp = (TextView) findViewById(R.id.txt_lvl_up);
        txtLvlUp.setTextColor(characterColor);
    }

    private float getPointsToNextLevel(int lvl) {
        if(lvl == 0)
            return 20;

        if(lvl < 0)
            return 0;

        return lvl * 20 + getPointsToNextLevel(lvl - 1);
    }

    private int getCharacterColor() {
        switch (ConfigUtils.getCharacterType(this)){
            case Alpha:
                return ContextCompat.getColor(this, R.color.colorAlpha);
            case Beta:
                return ContextCompat.getColor(this, R.color.colorBeta);
            case Gama:
                return ContextCompat.getColor(this, R.color.colorGama);
        }

        throw new RuntimeException("Character type not identified.");
    }

    private void showCharacterStateDialog() {
        DialogFragment dialog = new CharacterStateDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.indicators_argument), fragmentIndicators.getIndicators());
        args.putSerializable(getString(R.string.character_state_argument), characterState);
        dialog.setArguments(args);

        showAsCharacterSpeechBubble(dialog);
    }

    private void showAsCharacterSpeechBubble(DialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), null);
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
        recalcIndicators();

        if(txtPoints != null)
            txtPoints.setText(getPointsText());

        if(spriteCharacter != null)
            reloadCharacterSprite(this);
    }

    private String getPointsText() {
        float currentScore = ConfigUtils.getScore(this);
        int currentLevel = ConfigUtils.getLevel(this);
        float toNext = getPointsToNextLevel(currentLevel);
        float toPrevious = getPointsToNextLevel(currentLevel - 1);

        String currentScoreToShow = String.valueOf((int)(currentScore - toPrevious));
        String toNextToShow = String.valueOf((int)(toNext - toPrevious));

        return currentScoreToShow + "/" + toNextToShow;
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
                final ArrayList<Registro> registros =
                        (ArrayList<Registro>) intent.getSerializableExtra(getString(R.string.registered_entries_argument));

                boolean paused = false;
                if(ConfigUtils.isAutoAvaliationOn(this) && SelfEvaluationFragment.shouldShowFor(registros)) {
                    askForSelfEvaluation(registros);
                    paused = true;
                }
                else if(FeedbackListAdapter.suportsAny(registros)) {
                    showFeedback(registros);
                    paused = true;
                }

                Runnable checkEntriesRunnable = new Runnable() {
                    @Override
                    public void run() {
                        addPoints(registros);
                        checkEntriesToUnlockAchievements(registros);
                    }
                };
                if(paused)
                    toRunWhenDismissFeedback.add(checkEntriesRunnable);
                else
                    checkEntriesRunnable.run();

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
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
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
            .setAdapter(adapter, null)
            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    while (!toRunWhenDismissFeedback.isEmpty())
                        toRunWhenDismissFeedback.poll().run();
                }
            })
            .create()
            .show();
    }

    public void addGlycemia(View view) {
        Glicemia glicemia = fragmentGlycemia.getGlycemia();

        if(glicemia == null) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mandatory_field_message_title)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setMessage(R.string.mandatory_glycemia_message)
                    .create()
                    .show();

            return;
        } else if (!glicemia.isValid()){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.invalid_field_value_message_title)
                    .setMessage(R.string.invalid_glycemia_value_message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();

            return;
        }

        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        registrarDadosService.registrarGlicemia(glicemia);

        Toast.makeText(this, getString(R.string.result_saved), Toast.LENGTH_LONG).show();

        final ArrayList<Registro> registros = new ArrayList<>();
        registros.add(glicemia);

        if(ConfigUtils.isAutoAvaliationOn(this))
            askForSelfEvaluation(registros);
        else
            showFeedback(registros);

        Intent intent = new Intent(this, RemindersService.class);
        startService(intent);

        fragmentGlycemia.reset();

        recalcIndicators();

        toRunWhenDismissFeedback.add(new Runnable() {
            @Override
            public void run() {
                addPoints(registros);
                checkEntriesToUnlockAchievements(registros);
            }
        });
    }

    private void addPoints(List<Registro> registros) {
        int pontuacao = new PontuacaoService().calcularPontuacao(registros);
        int newScore = ConfigUtils.incrementScore(this, pontuacao);

        txtScoreUp.setVisibility(View.VISIBLE);
        txtScoreUp.setText("+" + String.valueOf(pontuacao));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                txtScoreUp.clearAnimation();
                txtScoreUp.setVisibility(View.GONE);
            }
        }, scoreUpAnimation.getDuration());

        txtPoints.setText(getPointsText());
        txtScoreUp.startAnimation(scoreUpAnimation);

        int currentLevel = ConfigUtils.getLevel(this);
        if(newScore >= getPointsToNextLevel(currentLevel)) {
            addLevel();
        } else {
            pbrCharacterPoints.setProgress(newScore - getPointsToNextLevel(currentLevel - 1));
        }
    }

    private void addLevel() {
        int newLevel = ConfigUtils.incrementLevel(this);

        txtLvlUp.setVisibility(View.VISIBLE);
        txtLvlUp.setText("+1");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                txtLvlUp.clearAnimation();
                txtLvlUp.setVisibility(View.GONE);
            }
        }, scoreUpAnimation.getDuration());

        txtLevel.setText(String.valueOf(newLevel));
        txtLvlUp.startAnimation(scoreUpAnimation);

        pbrCharacterPoints.setMax(getPointsToNextLevel(newLevel) - getPointsToNextLevel(newLevel - 1));
        pbrCharacterPoints.setProgress(0);

        txtPoints.setText(getPointsText());

        if(newLevel == TipoPersonagem.MAX_BABY_CHAR_LEVEL + 1)
            goToEvolutionActivity();
        else
            showLevelUpMessage();
    }

    private void goToEvolutionActivity() {
        Intent intent = new Intent(this, EvolutionActivity.class);
        startActivity(intent);
    }

    private void showLevelUpMessage() {
        final Context context = this;

        spriteCharacter.pause();
        spriteCharacter
                .setSprite(ConfigUtils.getCharacterType(this)
                        .getSpriteSheet(this, EstadoPersonagem.MuitoBem, ConfigUtils.getLevel(this)));
        spriteCharacter.resume();

        String message = ConfigUtils.getLevel(this) <= TipoPersonagem.MAX_BABY_CHAR_LEVEL ?
                String.format(getString(R.string.lvl_up_text_with_evolution_indication), TipoPersonagem.MAX_BABY_CHAR_LEVEL + 1)
                : getString(R.string.lvl_up_text);

        new AlertDialog.Builder(this)
                .setTitle(R.string.lvl_up_title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        reloadCharacterSprite(context);
                    }
                })
                .create()
                .show();
    }

    private void reloadCharacterSprite(Context context) {
        spriteCharacter.pause();
        spriteCharacter
                .setSprite(ConfigUtils.getCharacterType(context)
                        .getSpriteSheet(context, characterState,
                                ConfigUtils.getLevel(context)));
        spriteCharacter.resume();

        LinearLayout charContainer = (LinearLayout) findViewById(R.id.character_container);
        if(charContainer != null) {
            charContainer.removeView(spriteCharacter);
            charContainer.addView(spriteCharacter);
        }
    }

    private void recalcIndicators() {
        if(fragmentIndicators != null) {
            fragmentIndicators.calcIndicators();

            verifyAverageImprovement(lastWeekGlycemiaAverage, fragmentIndicators.getCurrentWeekAverageGlycemia(), Desafio.MELHORAR_MEDIA_SEMANAL);
            verifyAverageImprovement(lastMonthGlycemiaAverage, fragmentIndicators.getCurrentMonthAverageGlycemia(), Desafio.MELHORAR_MEDIA_MENSAL);

            lastWeekGlycemiaAverage = fragmentIndicators.getCurrentWeekAverageGlycemia();
            lastMonthGlycemiaAverage = fragmentIndicators.getCurrentMonthAverageGlycemia();

            List<Indicador> indicators = fragmentIndicators.getIndicators();

            EstadoPersonagem newCharacterState = new EstadoPersonagemService().getEstadoPersonagem(indicators);

            if(newCharacterState != characterState) {
                spriteCharacter.pause();
                spriteCharacter
                        .setSprite(ConfigUtils.getCharacterType(this)
                                .getSpriteSheet(this, newCharacterState, ConfigUtils.getLevel(this)));
                spriteCharacter.resume();
            }

            characterState = newCharacterState;
        }
    }

    @Override
    public void onDismissSelfEvaluation(final List<Registro> evaluatedEntries) {
        if(!ConfigUtils.isAutoAvaliationOn(this))
            new AlertDialog.Builder(this)
                    .setMessage(R.string.self_evaluation_desactivation)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
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
