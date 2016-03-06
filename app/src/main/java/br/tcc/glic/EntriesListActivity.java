package br.tcc.glic;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import br.tcc.glic.dialogs.EditEntryDialogListener;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.domain.services.RegistrosService;
import br.tcc.glic.fragments.EntriesListFragment;
import br.tcc.glic.fragments.RegisterGlycemiaFragment;

public class EntriesListActivity extends AppCompatActivity
    implements EntriesListFragment.OnListFragmentInteractionListener,
        EditEntryDialogListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_list);
    }

    @Override
    public void onListFragmentInteraction(Registro item) {
        DialogFragment dialog;
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.entry_bundle_argument), item);
        if(item instanceof Glicemia) {
            dialog = new RegisterGlycemiaFragment();
            bundle.putBoolean(getString(R.string.show_date_bundle_argument), true);
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    public void onApply(Registro registro) {
        new RegistrosService().update(registro);
    }

    @Override
    public void onDelete(Registro registro) {
        new RegistrosService().delete(registro);
    }
}
