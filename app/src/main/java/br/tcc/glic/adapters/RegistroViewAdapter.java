package br.tcc.glic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.tcc.glic.R;
import br.tcc.glic.domain.core.AplicacaoInsulina;
import br.tcc.glic.domain.core.CarboidratoIngerido;
import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Registro;
import br.tcc.glic.fragments.EntriesListFragment;

public class RegistroViewAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private final int GLYCEMIA = 1, CARBOHYDRATE = 2, INSULIN = 3, HBA1C = 4;

    private final List<Registro> items;
    private final List<String> sections;
    private final List<Integer> sectionCounts;
    private final Map<Long, Integer> idPositionMapping;
    private final EntriesListFragment.OnListFragmentInteractionListener listener;
    private static final DateFormat sectionDateFormat = new SimpleDateFormat("dd/MM/yy (EEE)");
    private final SimpleDateFormat itemDateFormat = new SimpleDateFormat("HH:mm");

    public RegistroViewAdapter(List<Registro> items, EntriesListFragment.OnListFragmentInteractionListener listener) {
        this.idPositionMapping = new HashMap<>();
        this.items = items;
        this.listener = listener;
        this.sections = new ArrayList<>();
        this.sectionCounts = new ArrayList<>();

        int count = 0;
        String currentDate = null;
        for(Registro entry : items) {
            String date = sectionDateFormat.format(entry.getHora());
            if(currentDate == null)
                currentDate = date;

            if(!date.equals(currentDate)) {
                sections.add(currentDate);
                sectionCounts.add(count);
                count = 0;
                currentDate = date;
            }

            count++;
        }

        sections.add(currentDate);
        sectionCounts.add(count);
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

        if(viewType == VIEW_TYPE_HEADER)
            return new HeaderViewHolder(view);

        throw new IllegalArgumentException("Entry type not found.");
    }

    private int getLayout(int viewType) {
        if(viewType == VIEW_TYPE_HEADER)
            return R.layout.list_section_header;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        Registro registro = items.get(absolutePosition);
        idPositionMapping.put(registro.getCodigo(), absolutePosition);

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
        holder.timeView.setText(itemDateFormat.format(glicemia.getHora()));
        holder.qualityImage.setImageDrawable(glicemia.getQualidade().getDrawable(holder.view.getContext()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    private void configureCarboidratoIngeridoViewHolder(final CarboidratoIngeridoViewHolder holder,
                                                        CarboidratoIngerido carboidrato) {
        holder.item = carboidrato;
        holder.contentView.setText(String.valueOf(carboidrato.getQuantidade()));
        holder.timeView.setText(itemDateFormat.format(carboidrato.getHora()));

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
        holder.timeView.setText(itemDateFormat.format(aplicacaoInsulina.getHora()));
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
    public int getSectionCount() {
        return sections.size();
    }

    @Override
    public int getItemCount(int section) {
        return sectionCounts.get(section);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int section) {
        TextView txtTitle = ((HeaderViewHolder)viewHolder).titleView;

        txtTitle.setText(sections.get(section));
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        if (items.get(absolutePosition) instanceof Glicemia)
            return GLYCEMIA;

        if(items.get(absolutePosition) instanceof CarboidratoIngerido)
            return CARBOHYDRATE;

        if(items.get(absolutePosition) instanceof AplicacaoInsulina)
            return INSULIN;

        if(items.get(absolutePosition) instanceof HemoglobinaGlicada)
            return HBA1C;

        return 0;
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
        private HemoglobinaGlicada item;


        public HemoglobinaGlicadaViewHolder(View view) {
            super(view);
            this.view = view;
            contentView = (TextView) view.findViewById(R.id.valor_hemoglobina);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView titleView;

        public HeaderViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = (TextView) view.findViewById(R.id.section_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }
    }
}
