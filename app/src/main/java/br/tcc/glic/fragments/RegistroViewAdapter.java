package br.tcc.glic.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Registro;

public class RegistroViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int GLYCEMIA = 0, CARBOHYDRATE = 1, INSULIN = 2, HBA1C = 3;

    private final List<Registro> items;
    private final Map<Long, Integer> idPositionMapping;
    private final EntriesListFragment.OnListFragmentInteractionListener listener;

    public RegistroViewAdapter(List<Registro> items, EntriesListFragment.OnListFragmentInteractionListener listener) {
        this.idPositionMapping = new HashMap<>();
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

        if(viewType == INSULIN)
            return new AplicacaoInsulinaViewHolder(view);

        if(viewType == HBA1C)
            return new HemoglobinaGlicadaViewHolder(view);

        throw new IllegalArgumentException("Entry type not found.");
    }

    private int getLayout(int viewType) {
        if(viewType == GLYCEMIA)
            return R.layout.fragment_list_item_glicemia;

        if(viewType == CARBOHYDRATE)
            return R.layout.fragment_list_item_carboidrato_ingerido;

        if(viewType == INSULIN)
            return R.layout.fragment_list_item_aplicacao_insulina;

        if(viewType == HBA1C)
            return R.layout.fragment_list_item_hemoglobina_glicada;

        throw new IllegalArgumentException("Entry type not found.");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Registro registro = items.get(position);
        idPositionMapping.put(registro.getCodigo(), position);

        switch (holder.getItemViewType()){
            case GLYCEMIA:
                configureGlicemiaViewHolder((GlicemiaViewHolder) holder, (Glicemia) registro);
                break;
            case CARBOHYDRATE:
                configureCarboidratoIngeridoViewHolder((CarboidratoIngeridoViewHolder) holder,
                        (CarboidratoIngerido) registro);
                break;
            case INSULIN:
                configureAplicacaoInsulinaViewHolder((AplicacaoInsulinaViewHolder) holder,
                        (AplicacaoInsulina) registro);
                break;
            case HBA1C:
                configureHemoglobinaGlicadaViewHolder((HemoglobinaGlicadaViewHolder) holder,
                        (HemoglobinaGlicada) registro);
                break;
        }
    }

    private void configureGlicemiaViewHolder(final GlicemiaViewHolder holder, Glicemia glicemia) {
        holder.item = glicemia;
        holder.contentView.setText(String.valueOf(glicemia.getValor()));
        holder.timeView.setText(new SimpleDateFormat("dd/MM/yy HH:mm").format(glicemia.getHora()));
        holder.qualityImage.setImageDrawable(getQualityDrawable(glicemia, holder.view.getContext()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    private Drawable getQualityDrawable(Glicemia glicemia, Context context) {
        switch (glicemia.getQualidade())
        {
            case Baixo:
                return ContextCompat.getDrawable(context, R.drawable.ic_low);
            case Bom:
                return ContextCompat.getDrawable(context, R.drawable.ic_good);
            case Alto:
                return ContextCompat.getDrawable(context, R.drawable.ic_high);
        }

        throw new RuntimeException("QualidadeRegistro not recognized");
    }

    private void configureCarboidratoIngeridoViewHolder(final CarboidratoIngeridoViewHolder holder,
                                                        CarboidratoIngerido carboidrato) {
        holder.item = carboidrato;
        holder.contentView.setText(String.valueOf(carboidrato.getQuantidade()));
        holder.timeView.setText(new SimpleDateFormat("dd/MM/yy HH:mm").format(carboidrato.getHora()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    private void configureAplicacaoInsulinaViewHolder(final AplicacaoInsulinaViewHolder holder,
                                                       AplicacaoInsulina aplicacaoInsulina) {
        holder.item = aplicacaoInsulina;
        holder.contentView.setText(String.valueOf(aplicacaoInsulina.getQuantidade()));
        holder.timeView.setText(new SimpleDateFormat("dd/MM/yy HH:mm").format(aplicacaoInsulina.getHora()));
        holder.typeView.setText(aplicacaoInsulina.getTipo().getNome());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    private void configureHemoglobinaGlicadaViewHolder(final HemoglobinaGlicadaViewHolder holder,
                                                       HemoglobinaGlicada hbA1c) {
        holder.item = hbA1c;
        holder.contentView.setText(String.valueOf(hbA1c.getValor()));
        holder.timeView.setText(new SimpleDateFormat("dd/MM/yy").format(hbA1c.getHora()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.item);
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

        if(items.get(position) instanceof AplicacaoInsulina)
            return INSULIN;

        if(items.get(position) instanceof HemoglobinaGlicada)
            return HBA1C;

        return -1;
    }

    public void notifyItemChanged(Registro item) {
        int position = idPositionMapping.get(item.getCodigo());
        this.items.set(position, item);
        this.notifyItemChanged(position, item);
    }

    public void notifyItemRemoved(Registro item) {
        int position = idPositionMapping.get(item.getCodigo());
        this.items.remove(position);
        this.notifyItemRemoved(position);
    }

    public class GlicemiaViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView contentView;
        private final TextView timeView;
        private final ImageView qualityImage;
        public Glicemia item;

        public GlicemiaViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = (TextView) view.findViewById(R.id.valor_glicemia);
            timeView = (TextView) view.findViewById(R.id.hora_glicemia);
            qualityImage = (ImageView) view.findViewById(R.id.img_quality_glycemia_list);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }

    public class CarboidratoIngeridoViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView contentView;
        private final TextView timeView;
        public CarboidratoIngerido item;

        public CarboidratoIngeridoViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = (TextView) view.findViewById(R.id.quantidade_carboidrato);
            timeView = (TextView) view.findViewById(R.id.hora_carboidrato);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }

    public class AplicacaoInsulinaViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView contentView;
        private final TextView timeView;
        private final TextView typeView;
        public AplicacaoInsulina item;

        public AplicacaoInsulinaViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = (TextView) view.findViewById(R.id.quantidade_insulina);
            timeView = (TextView) view.findViewById(R.id.hora_insulina);
            typeView = (TextView) view.findViewById(R.id.tipo_insulina);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }

    public class HemoglobinaGlicadaViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView contentView;
        private final TextView timeView;
        private HemoglobinaGlicada item;


        public HemoglobinaGlicadaViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = (TextView) view.findViewById(R.id.valor_hemoglobina);
            timeView = (TextView) view.findViewById(R.id.hora_hemoglobina);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }
}
