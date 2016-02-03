package br.tcc.glic.data.entities;

import com.orm.dsl.Table;

import java.sql.Date;

/**
 * Created by André on 02/02/2016.
 */
@Table
public class Glicemia implements Entity {
    private Long id;
    private int valor;
    private Date hora;

    public Glicemia() {
    }

    public Glicemia(int valor, java.util.Date hora) {
        this.valor = valor;
        this.hora = new Date(hora.getTime());
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
}
