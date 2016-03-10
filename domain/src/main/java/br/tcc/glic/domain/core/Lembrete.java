package br.tcc.glic.domain.core;

import java.util.Date;

/**
 * Representação de lembrete de registro
 * Created by André on 07/03/2016.
 */
public class Lembrete {
    private Date horaRegistro;
    private Date horaLembrete;

    public Lembrete(Date horaRegistro, Date horaLembrete) {
        this.horaRegistro = horaRegistro;
        this.horaLembrete = horaLembrete;
    }

    public Date getHoraLembrete() {
        return horaLembrete;
    }

    public Date getHoraRegistro() {
        return horaRegistro;
    }
}
