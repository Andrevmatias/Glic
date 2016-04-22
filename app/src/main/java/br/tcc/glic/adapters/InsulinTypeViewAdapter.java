package br.tcc.glic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.TipoInsulina;
import br.tcc.glic.fragments.InsulinTypeListFragment;

/**
 * ViewAdapter para listar tipos de insulina
 */
public class InsulinTypeViewAdapter extends RecyclerView.Adapter<InsulinTypeViewAdapter.ViewHolder> {

    private final List<TipoInsulina> mValues;
    private final InsulinTypeListFragment.OnListFragmentInteractionListener mListener;

    public InsulinTypeViewAdapter(List<TipoInsulina> items, InsulinTypeListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_insulin_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getNome());
        holder.mDurationView.setText(getDurationText(mValues.get(position)));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private String getDurationText(TipoInsulina tipoInsulina) {
        return new StringBuilder()
                .append("Início: ").append(tipoInsulina.getInicio()).append("m | ")
                .append("Pico: ").append(tipoInsulina.getPico()).append("m | ")
                .append("Duração: ").append(tipoInsulina.getDuracao()).append("m")
                .toString();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDurationView;
        public TipoInsulina mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.insulin_type_name);
            mDurationView = (TextView) view.findViewById(R.id.insulin_type_duration);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDurationView.getText() + "'";
        }
    }
}
