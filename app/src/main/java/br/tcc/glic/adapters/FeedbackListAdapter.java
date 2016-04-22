package br.tcc.glic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Registro;

/**
 * Adaptador para feedbacks após registro
 * Created by André on 16/03/2016.
 */
public class FeedbackListAdapter extends BaseAdapter {

    private Context context;

    private List<Registro> registros;


    public FeedbackListAdapter(Context context, List<Registro> registros) {
        this.context = context;
        this.registros = new ArrayList<>(registros.size());
        for (Registro registro : registros) {
            if(registro instanceof Glicemia || registro instanceof HemoglobinaGlicada)
                this.registros.add(registro);
        }
    }


    @Override

    public int getCount() {

        return registros.size();

    }


    @Override

    public Object getItem(int position) {

        return registros.get(position);

    }


    @Override

    public long getItemId(int position) {

        return position;

    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        Registro registro = registros.get(position);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(registro instanceof Glicemia)
            return getView(inflater, (Glicemia) registro);
        else if(registro instanceof HemoglobinaGlicada)
            return getView(inflater, (HemoglobinaGlicada) registro);
        else
            throw new RuntimeException("Tipo de registro sem feedback implementado");
    }

    private View getView(LayoutInflater inflater, Glicemia glicemia) {
        View view = inflater.inflate(R.layout.listview_feedback_glycemia, null);
        TextView feedbackValue = (TextView) view.findViewById(R.id.feedback_value);
        ImageView imageQuality = (ImageView) view.findViewById(R.id.img_quality);
        TextView txtFeedback = (TextView) view.findViewById(R.id.txt_feedback);

        feedbackValue.setText(String.valueOf(glicemia.getValor()));
        imageQuality.setImageDrawable(glicemia.getQualidade().getDrawable(context));
        txtFeedback.setText(glicemia.getQualidade().toString(context));

        return view;
    }

    private View getView(LayoutInflater inflater, HemoglobinaGlicada hba1c) {
        View view = inflater.inflate(R.layout.listview_feedback_hba1c, null);
        TextView feedbackValue = (TextView) view.findViewById(R.id.feedback_value);
        TextView glycemiaValue = (TextView) view.findViewById(R.id.glycemia_value);
        ImageView imageQuality = (ImageView) view.findViewById(R.id.img_quality);
        TextView txtFeedback = (TextView) view.findViewById(R.id.txt_feedback);

        feedbackValue.setText(String.valueOf(hba1c.getValor()));
        glycemiaValue.setText(String.valueOf(hba1c.getGme()));
        imageQuality.setImageDrawable(hba1c.getQualidade().getDrawable(context));
        txtFeedback.setText(hba1c.getQualidade().toString(context));

        return view;
    }

    public static boolean suportsAny(ArrayList<Registro> registros) {
        for (Registro registro : registros) {
            if (registro instanceof Glicemia || registro instanceof HemoglobinaGlicada)
                return true;
        }

        return false;
    }
}
