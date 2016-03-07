package br.tcc.glic.domain.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;

/**
 * Serviço de identificação e listagem de lembretes
 * Created by André on 26/02/2016.
 */
public class LembretesService {
    private final Repository<Registro> repository;

    public LembretesService() {
        repository = RepositoryFactory.get(Registro.class);

        Calendar agora = Calendar.getInstance();
    }

    public List<Calendar> getHorasLembretes(){
        ArrayList<Calendar> lembretes = new ArrayList<>();
        Calendar agora = Calendar.getInstance();
        agora.add(Calendar.SECOND, 3);
        lembretes.add(agora);
        return lembretes;
    }
}
