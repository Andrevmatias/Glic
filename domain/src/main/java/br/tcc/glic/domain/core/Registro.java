package br.tcc.glic.domain.core;

import java.util.Date;

/**
 * Created by André on 19/02/2016.
 */
public abstract class Registro {
    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    protected Date hora;
}
