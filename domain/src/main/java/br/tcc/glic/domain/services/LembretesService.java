package br.tcc.glic.domain.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.repositories.Repository;
import br.tcc.glic.data.repositories.RepositoryFactory;
import br.tcc.glic.domain.core.Lembrete;

/**
 * Serviço de identificação e listagem de lembretes
 * Created by André on 26/02/2016.
 */
public class LembretesService {

    private static final int TOLERANCIA = 1800000; // 30 minutos
    private static final int DIAS_ESCOPO_ANALISE = 7;
    private static final int MINIMO_AMOSTRAS_ANALISE = 20;

    private final Repository<Registro> repository;

    public LembretesService() {
        repository = RepositoryFactory.get(Registro.class);
    }

    public List<Lembrete> getLembretes(){
        Calendar inicioEscopo = Calendar.getInstance();
        inicioEscopo.add(Calendar.DATE, -DIAS_ESCOPO_ANALISE);
        List<Registro> ultimosRegistros = repository.find("hora < ? and hora > ?",
                new String[]{
                        String.valueOf(Calendar.getInstance().getTimeInMillis()),
                        String.valueOf(inicioEscopo.getTimeInMillis())
                },
                null, "hora desc", null);

        if(ultimosRegistros.size() < MINIMO_AMOSTRAS_ANALISE)
            return new ArrayList<>(0);

        AnalizadorRegistros analizador = new AnalizadorRegistros(ultimosRegistros);

        List<Calendar> horariosComuns = analizador.identificarHorariosDeRegistroComuns();

        return gerarLembretes(horariosComuns);
    }

    public boolean deveDispararParaHorarioComum(Calendar horaComumDeRegistro) {
        List<Registro> registros = repository.find("hora < ?",
                new String[]{String.valueOf(Calendar.getInstance().getTimeInMillis())},
                null, "hora desc", "1");

        if(registros.isEmpty())
            return true;

        Registro registroMaisProximo = registros.get(0);
        Long distanciaHorarios = Math.abs(
                horaComumDeRegistro.getTimeInMillis()
                        - registroMaisProximo.getHora().getTime()
        );

        return distanciaHorarios > TOLERANCIA;
    }

    private List<Lembrete> gerarLembretes(List<Calendar> horariosComuns) {
        List<Lembrete> lembretes = new ArrayList<>();

        for (Calendar horario : horariosComuns) {
            Date horaRegistro = horario.getTime();
            horario.add(Calendar.MILLISECOND, TOLERANCIA);
            lembretes.add(new Lembrete(horaRegistro, horario.getTime()));
        }

        return lembretes;
    }
}
