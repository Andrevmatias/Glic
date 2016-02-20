package br.tcc.glic.domain.core;

/**
 * Created by André on 26/01/2016.
 */
public class AplicacaoInsulina extends Registro {
    private int codigo;
    private double quantidade;
    private TipoInsulina tipo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public TipoInsulina getTipo() {
        return tipo;
    }

    public void setTipo(TipoInsulina tipo) {
        this.tipo = tipo;
    }
}
