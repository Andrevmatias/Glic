package br.tcc.glic.domain.core;

import java.util.Date;

import br.tcc.glic.domain.enums.TipoCarboidrato;

/**
 * Created by André on 26/01/2016.
 */
public class CarboidratoIngerido {
    private int codigo;
    private int quantidade;
    private Date hora;
    private TipoCarboidrato tipo;

    public TipoCarboidrato getTipo() {
        return tipo;
    }

    public void setTipo(TipoCarboidrato tipo) {
        this.tipo = tipo;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
