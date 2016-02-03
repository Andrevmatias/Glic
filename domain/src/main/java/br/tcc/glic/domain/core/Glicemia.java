package br.tcc.glic.domain.core;

import java.util.Date;

/**
 * Created by André on 25/01/2016.
 */
public class Glicemia {
    private Long codigo;
    private int valor;
    private Date hora;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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
}
