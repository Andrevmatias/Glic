package br.tcc.glic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.tcc.glic.domain.enums.EstadoPersonagem;
import br.tcc.glic.domain.personagem.TipoPersonagem;
import br.tcc.glic.userconfiguration.ConfigUtils;
import br.tcc.glic.views.SpriteView;

public class EvolutionActivity extends AppCompatActivity implements SpriteView.AnimationFinishedListener {

    private Button btnOk;
    private SpriteView spriteEvolution;
    private TextView txtTitle;

    private boolean evolved;
    private TipoPersonagem characterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolution);

        initComponents();
    }

    private void initComponents() {
        spriteEvolution = (SpriteView) findViewById(R.id.sprite_evolution);
        characterType = ConfigUtils.getCharacterType(this);

        spriteEvolution.setSprite(characterType
                .getEvolutionSpriteSheet(this));

        txtTitle = (TextView) findViewById(R.id.txt_evolution_title);

        btnOk = (Button) findViewById(R.id.btn_ok_evolution);
    }

    public void nextStep(View view) {
        if(!evolved)
            triggerEvolution();
        else
            goToMain();
    }

    private void triggerEvolution() {
        btnOk.setVisibility(View.GONE);
        txtTitle.setVisibility(View.GONE);

        spriteEvolution.setAnimationFinishedListener(this);
        spriteEvolution.setLoop(false);
        spriteEvolution.resume();
    }

    private void goToMain() {
        finish();
    }

    @Override
    public void onAnimationFinished() {
        spriteEvolution.pause();
        spriteEvolution.setAnimationFinishedListener(null);
        spriteEvolution.setLoop(true);
        spriteEvolution.setSprite(characterType
                .getSpriteSheet(this,
                                EstadoPersonagem.MuitoBem,
                                TipoPersonagem.MAX_BABY_CHAR_LEVEL + 1));
        spriteEvolution.resume();
        txtTitle.setText(R.string.character_evolved_title);
        evolved = true;
        btnOk.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.VISIBLE);
    }
}
