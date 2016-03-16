package br.tcc.glic;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import br.tcc.glic.dialogs.EditEntryDialogListener;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.fragments.EntriesListFragment;
import br.tcc.glic.fragments.RegisterCarbohydratesFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;
import br.tcc.glic.fragments.RegisterHbA1cFragment;
import br.tcc.glic.fragments.RegisterInsulinFragment;

public class EntriesListActivity extends AppCompatActivity
    implements EntriesListFragment.OnListFragmentInteractionListener,
        EditEntryDialogListener
{

    private EntriesListFragment fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_list);

        initComponents();
    }

    private void initComponents() {
        fragmentList = (EntriesListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_entries_list);
    }

    @Override
    public void onListFragmentInteraction(Registro item) {
        DialogFragment dialog;

        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.entry_bundle_argument), item);

        if(item instanceof Glicemia) {
            dialog = new RegisterGlycemiaFragment();
            bundle.putBoolean(getString(R.string.show_date_bundle_argument), true);
        } else if(item instanceof CarboidratoIngerido) {
            dialog = new RegisterCarbohydratesFragment();
        } else if(item instanceof AplicacaoInsulina) {
            dialog = new RegisterInsulinFragment();
        } else if(item instanceof HemoglobinaGlicada) {
            dialog = new RegisterHbA1cFragment();
        } else {
            throw new RuntimeException(getString(R.string.invalid_entry_type));
        }

        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onApply(Registro registro) {
        new RegistrosService(this).update(registro);
        fragmentList.notifyItemChanged(registro);
    }

    @Override
    public void onDelete(Registro registro) {
        new RegistrosService(this).delete(registro);
        fragmentList.notifyItemRemoved(registro);
    }
}
