package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.TipoIndicador;

/**
 * Created by André on 25/01/2016.
 */
public class Indicador {

    private TipoIndicador tipo;
    private double valor;

    public TipoIndicador getTipo() {
        return tipo;
    }

    public void setTipo(TipoIndicador tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
