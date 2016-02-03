package br.tcc.glic.domain.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.tcc.glic.data.entities.Glicemia;
import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;

/**
 * Created by André on 02/02/2016.
 */
public class RegistrarDadosService {
    public void registrarGlicemia(int valor, Date hora){
        Repository<Glicemia> rep = RepositoryFactory.get(br.tcc.glic.data.entities.Glicemia.class);
        br.tcc.glic.data.entities.Glicemia glicemia = new br.tcc.glic.data.entities.Glicemia(valor, hora);
        rep.save(glicemia);
    }

    public List<br.tcc.glic.domain.core.Glicemia> listGlicemias() {
        Repository<Glicemia> rep = RepositoryFactory.get(br.tcc.glic.data.entities.Glicemia.class);
        List<br.tcc.glic.data.entities.Glicemia> glicemias = rep.toList();
        List<br.tcc.glic.domain.core.Glicemia> glicemiasRet = new ArrayList<>();
        for (Glicemia glicemia :
                glicemias) {
            br.tcc.glic.domain.core.Glicemia  novaGlicemia = new br.tcc.glic.domain.core.Glicemia();
            novaGlicemia.setCodigo(glicemia.getId());
            novaGlicemia.setHora(glicemia.getHora());
            novaGlicemia.setValor(glicemia.getValor());
            glicemiasRet.add(novaGlicemia);
        }

        return  glicemiasRet;
    }
}
