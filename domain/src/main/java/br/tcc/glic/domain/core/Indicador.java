package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.QualidadeRegistro;
import br.tcc.glic.domain.enums.TipoIndicador;

/**
 * Indicador derivado de uma certa quantidade de registros
 * Created by André on 25/01/2016.
 */
public class Indicador {

    private TipoIndicador tipo;
    private double valor;
    private QualidadeRegistro qualidade;

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

    public QualidadeRegistro getQualidade() {
        return qualidade;
    }

    public void setQualidade(QualidadeRegistro qualidade) {
        this.qualidade = qualidade;
    }
}
