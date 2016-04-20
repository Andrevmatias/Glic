package br.tcc.glic.domain.services;

import java.util.List;

import br.tcc.glic.domain.core.Indicador;
import br.tcc.glic.domain.enums.EstadoPersonagem;
import br.tcc.glic.domain.enums.QualidadeRegistro;

/**
 * Created by André on 19/04/2016.
 */
public class EstadoPersonagemService {

    public EstadoPersonagem getEstadoPersonagem(List<Indicador> indicadores) {
        if(indicadores.size() == 0)
            return EstadoPersonagem.Normal;

        float countBons = 0;

        for (Indicador indicador :
                indicadores) {
            if (indicador.getQualidade() == QualidadeRegistro.Bom)
                countBons++;
        }

        float proporcao  = countBons / (float) indicadores.size();

        if(proporcao == 0)
            return EstadoPersonagem.MuitoMal;
        else if(proporcao < 0.25)
            return EstadoPersonagem.Mal;
        else if(proporcao > 0.75)
            return EstadoPersonagem.Bem;
        else if(proporcao == 1)
            return EstadoPersonagem.MuitoBem;
        else
            return EstadoPersonagem.Normal;

    }
}
