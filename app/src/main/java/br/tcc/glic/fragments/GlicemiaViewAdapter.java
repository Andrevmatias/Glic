package br.tcc.glic.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.Registro;

public class GlicemiaViewAdapter extends RecyclerView.Adapter<GlicemiaViewAdapter.ViewHolder> {

    private final List<Registro> mValues;
    private final EntriesListFragment.OnListFragmentInteractionListener mListener;

    public GlicemiaViewAdapter(List<Registro> items, EntriesListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_glicemia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = (Glicemia) mValues.get(position);
        holder.mContentView.setText(String.valueOf(((Glicemia)mValues.get(position)).getValor()));
        if(mValues.get(position).getHora() != null)
            holder.mTimeView.setText(new SimpleDateFormat("dd/MM/yy HH:mm").format(mValues.get(position).getHora()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        private final TextView mTimeView;
        public Glicemia mItem;

        public ViewHolder(View view) {
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
}
