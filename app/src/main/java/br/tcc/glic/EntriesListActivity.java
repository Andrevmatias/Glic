package br.tcc.glic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.fragments.EntriesListFragment;

public class EntriesListActivity extends AppCompatActivity
    implements EntriesListFragment.OnListFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_list);
    }

    @Override
    public void onListFragmentInteraction(Glicemia item) {

    }
}
