package br.tcc.glic.domain.services;

import java.util.List;

import br.tcc.glic.domain.core.Glicemia;
import br.tcc.glic.domain.core.HemoglobinaGlicada;
import br.tcc.glic.domain.core.Registro;

/**
 * Created by André on 16/04/2016.
 */
public class PontuacaoService {
    public int calcularPontuacao(List<Registro> registros){
        int contador = 0;

        for(Registro registro : registros){
            if(registro instanceof Glicemia)
                contador += 10;
            else if(registro instanceof HemoglobinaGlicada)
                contador += 30;
            else
                contador += 15;
        }

        return contador;
    }
}
