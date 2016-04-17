package br.tcc.glic.fragments.configuration;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.tcc.glic.R;
import br.tcc.glic.domain.personagem.TipoPersonagem;
import br.tcc.glic.views.SpriteView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterConfigurationFragment extends Fragment {

    private OnConfigurationSelectedListener selectionListener;
    private TipoPersonagem characterType = TipoPersonagem.Alpha;


    public CharacterConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_character_configuration, container, false);

        initComponents(fragment);

        return fragment;
    }

    private void initComponents(final View fragment) {
        Button btnContinue = (Button) fragment.findViewById(R.id.btn_character_configuration_save);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifySelection();
            }
        });

        final SpriteView btnSelectAlpha = (SpriteView) fragment.findViewById(R.id.btn_select_alpha);
        final SpriteView btnSelectBeta = (SpriteView) fragment.findViewById(R.id.btn_select_beta);
        final SpriteView btnSelectGama = (SpriteView) fragment.findViewById(R.id.btn_select_gama);

        btnSelectAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSelectAlpha.resume();
                btnSelectBeta.pause();
                btnSelectGama.pause();
                characterType = TipoPersonagem.Alpha;
            }
        });

        btnSelectBeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSelectAlpha.pause();
                btnSelectBeta.resume();
                btnSelectGama.pause();
                characterType = TipoPersonagem.Beta;
            }
        });

        btnSelectGama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSelectAlpha.pause();
                btnSelectBeta.pause();
                btnSelectGama.resume();
                characterType = TipoPersonagem.Gama;
            }
        });
    }

    private void notifySelection() {
        if(selectionListener != null)
        {
            selectionListener.onConfigurationSelected(getString(R.string.character_config), characterType);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            selectionListener = (OnConfigurationSelectedListener) activity;
        } catch (ClassCastException e) {
            //Just ignore
        }
    }
}
