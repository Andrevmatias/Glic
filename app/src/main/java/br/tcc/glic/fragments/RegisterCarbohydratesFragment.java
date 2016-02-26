package br.tcc.glic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.CarboidratoIngerido;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCarbohydratesFragment extends Fragment
{
    private EditText edtCarbohydrates;
    private DateTimeFragment fragmentDateTime;

    public RegisterCarbohydratesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_carbohydrates, container, false);

        initComponents(view);

        return view;
    }

    private void initComponents(View view) {
        edtCarbohydrates = (EditText) view.findViewById(R.id.edt_carbohydrates);

        fragmentDateTime = (DateTimeFragment) getChildFragmentManager()
                .findFragmentById(R.id.date_time_carbohydrates);
    }

    public CarboidratoIngerido getCarbohydrate(){
        String quantidade = edtCarbohydrates.getText().toString();
        if(quantidade.length() == 0)
            return null;

        CarboidratoIngerido carboidratoIngerido = new CarboidratoIngerido();
        carboidratoIngerido.setQuantidade(Integer.parseInt(quantidade));
        carboidratoIngerido.setHora(fragmentDateTime.getDateTime().getTime());

        return carboidratoIngerido;
    }

    public void reset(){
        fragmentDateTime.reset();
        edtCarbohydrates.setText("");
    }
}
