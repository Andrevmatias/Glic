package br.tcc.glic.domain.core;

/**
 * Created by André on 26/01/2016.
 */
public class HemoglobinaGlicada extends Registro {
    private Long codigo;
    private double valor;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
