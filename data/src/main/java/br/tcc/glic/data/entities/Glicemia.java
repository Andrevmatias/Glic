package br.tcc.glic.data.entities;

import com.orm.dsl.Table;

import java.util.Date;

/**
 * Created by André on 02/02/2016.
 */
@Table
public class Glicemia
        implements Entity, Comparable<Glicemia> {
    private Long id;
    private int valor;
    private Date hora;

    public Glicemia() {
    }

    public Glicemia(int valor, java.util.Date hora) {
        this.valor = valor;
        this.hora = hora;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(Glicemia another) {
        long result = another.getHora().getTime() - this.getHora().getTime();
        if(result == 0) return 0;
        return result > 1 ? 1 : -1;
    }
}
