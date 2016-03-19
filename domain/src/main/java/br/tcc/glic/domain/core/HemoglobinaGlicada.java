package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.QualidadeRegistro;

/**
 * Registro de exame de hemoglobina glicada
 * Created by André on 26/01/2016.
 */
public class HemoglobinaGlicada extends Registro {

    private double valor;
    private int gme;
    private QualidadeRegistro qualidade;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getGme() {
        return gme;
    }

    public void setGme(int gme) {
        this.gme = gme;
    }

    public QualidadeRegistro getQualidade() {
        return qualidade;
    }

    public void setQualidade(QualidadeRegistro qualidade) {
        this.qualidade = qualidade;
    }
}
