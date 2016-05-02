package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.QualidadeRegistro;

/**
 * Registro de glicemia
 * Created by André on 25/01/2016.
 */
public class Glicemia extends Registro {

    private int valor;
    private QualidadeRegistro qualidade;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setQualidade(QualidadeRegistro qualidade) {
        this.qualidade = qualidade;
    }

    public QualidadeRegistro getQualidade() {
        return qualidade;
    }

    @Override
    public boolean isValid() {
        return this.getValor() >= 20 && this.getValor() <= 900;
    }
}
