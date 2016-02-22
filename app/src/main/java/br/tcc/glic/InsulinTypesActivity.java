package br.tcc.glic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.tcc.glic.domain.core.TipoInsulina;
import br.tcc.glic.fragments.InsulinTypeListFragment;

public class InsulinTypesActivity extends AppCompatActivity
        implements InsulinTypeListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insulin_types);
    }

    @Override
    public void onListFragmentInteraction(TipoInsulina item) {

    }
}
