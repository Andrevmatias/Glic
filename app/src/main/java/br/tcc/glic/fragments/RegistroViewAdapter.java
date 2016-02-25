package br.tcc.glic.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.Registro;

public class RegistroViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int GLYCEMIA = 0, CARBOHYDRATE = 1, INSULIN = 2, HBA1C = 3;

    private final List<Registro> items;
    private final EntriesListFragment.OnListFragmentInteractionListener listener;

    public RegistroViewAdapter(List<Registro> items, EntriesListFragment.OnListFragmentInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getLayout(viewType), parent, false);
        return getViewHolder(viewType, view);
    }

    private RecyclerView.ViewHolder getViewHolder(int viewType, View view) {
        if(viewType == GLYCEMIA)
            return new GlicemiaViewHolder(view);

        if(viewType == CARBOHYDRATE)
            return new CarboidratoIngeridoViewHolder(view);

        throw new IllegalArgumentException("Entry type not found.");
    }

    private int getLayout(int viewType) {
        if(viewType == GLYCEMIA)
            return R.layout.fragment_list_item_glicemia;

        if(viewType == CARBOHYDRATE)
            return R.layout.fragment_list_item_carboidrato_ingerido;

        throw new IllegalArgumentException("Entry type not found.");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case GLYCEMIA:
                configureGlicemiaViewHolder((GlicemiaViewHolder) holder,
                        (Glicemia) items.get(position));
                break;
            case CARBOHYDRATE:
                configureCarboidratoIngeridoViewHolder((CarboidratoIngeridoViewHolder) holder,
                        (CarboidratoIngerido) items.get(position));
                break;
        }
    }

    private void configureGlicemiaViewHolder(final GlicemiaViewHolder holder, Glicemia glicemia) {
        holder.mContentView.setText(String.valueOf(glicemia.getValor()));
        holder.mTimeView.setText(new SimpleDateFormat("dd/MM/yy HH:mm").format(glicemia.getHora()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private void configureCarboidratoIngeridoViewHolder(final CarboidratoIngeridoViewHolder holder,
                                                        CarboidratoIngerido carboidrato) {
        holder.mContentView.setText(String.valueOf(carboidrato.getQuantidade()));
        holder.mTimeView.setText(new SimpleDateFormat("dd/MM/yy HH:mm").format(carboidrato.getHora()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Glicemia)
            return GLYCEMIA;

        if(items.get(position) instanceof CarboidratoIngerido)
            return CARBOHYDRATE;

        return -1;
    }

    public class GlicemiaViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        private final TextView mTimeView;
        public Glicemia mItem;

        public GlicemiaViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.valor_glicemia);
            mTimeView = (TextView) view.findViewById(R.id.hora_glicemia);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public class CarboidratoIngeridoViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        private final TextView mTimeView;
        public Glicemia mItem;

        public CarboidratoIngeridoViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.quantidade_carboidrato);
            mTimeView = (TextView) view.findViewById(R.id.hora_carboidrato);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
