package br.tcc.glic.domain.services;

import java.util.ArrayList;
import java.util.List;

import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;
import br.tcc.glic.domain.core.TipoInsulina;
import br.tcc.glic.domain.services.defaultdata.TiposInsulinaPadrao;
import br.tcc.glic.domain.utils.Conversions;

/**
 * Serviço de listagem e registro de tipos de insulina
 * Created by André on 19/02/2016.
 */
public class TipoInsulinaService {
    public void adicionarTipoInsulina(TipoInsulina tipo){
        Repository<br.tcc.glic.data.entities.TipoInsulina> rep =
                RepositoryFactory.get(br.tcc.glic.data.entities.TipoInsulina.class);
        rep.save(Conversions.tipoInsulina(tipo));
    }

    public List<TipoInsulina> getTiposInsulina(){
        Repository<br.tcc.glic.data.entities.TipoInsulina> rep =
                RepositoryFactory.get(br.tcc.glic.data.entities.TipoInsulina.class);
        List<TipoInsulina> result = new ArrayList<>();
        for(br.tcc.glic.data.entities.TipoInsulina tipoBd
                : rep.toList())
            result.add(Conversions.tipoInsulina(tipoBd));
        return result;
    }

    public void registrarInsulinasPadrao(){
        for (TipoInsulina tipo :
                TiposInsulinaPadrao.getAll()) {
            adicionarTipoInsulina(tipo);
        }
    }
}
