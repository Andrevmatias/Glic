package br.tcc.glic.domain.core;

import java.util.Date;

/**
 * Created by André on 26/01/2016.
 */
public class HemoglobinaGlicada {
    private int codigo;
    private double valor;
    private Date dataExame;

    public Date getDataExame() {
        return dataExame;
    }

    public void setDataExame(Date dataExame) {
        this.dataExame = dataExame;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
