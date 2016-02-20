package br.tcc.glic.domain.core;

/**
 * Created by André on 25/01/2016.
 */
public class Glicemia extends Registro {
    private Long codigo;
    private int valor;

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
}
