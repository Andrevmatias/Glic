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
        else if(proporcao == 1)
            return EstadoPersonagem.MuitoBem;
        else if(proporcao < 0.25)
            return EstadoPersonagem.Mal;
        else if(proporcao > 0.75)
            return EstadoPersonagem.Bem;
        else
            return EstadoPersonagem.Normal;

    }

    public int getNumeroIndicadoresParaMelhorar(List<Indicador> indicadores) {
        if(indicadores.size() == 0)
            return 0;

        float countBons = 0;

        for (Indicador indicador :
                indicadores) {
            if (indicador.getQualidade() == QualidadeRegistro.Bom)
                countBons++;
        }

        float proporcao = countBons / (float) indicadores.size();

        if(proporcao == 0)
            return 1;
        else if(proporcao == 1)
            return 0;
        else if(proporcao < 0.25)
            return (int)Math.ceil((countBons / 4f / proporcao) - countBons);
        else if(proporcao > 0.75)
            return (int)Math.ceil((countBons / proporcao) - countBons);
        else
            return (int)Math.ceil((countBons * 0.75 / proporcao) - countBons);

    }
}
