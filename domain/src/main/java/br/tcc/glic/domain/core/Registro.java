package br.tcc.glic.domain.core;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe base de registros
 * Created by André on 19/02/2016.
 */
public abstract class Registro implements Serializable, Comparable<Registro> {

    protected Date hora;
    protected Long codigo;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public abstract boolean isValid();

    @Override
    public int compareTo(Registro another) {
        long result = another.getHora().getTime() - this.getHora().getTime();
        if(result == 0) return 0;
        return result > 1 ? 1 : -1;
    }
}
