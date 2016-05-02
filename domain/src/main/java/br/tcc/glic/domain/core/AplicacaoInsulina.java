package br.tcc.glic.domain.core;

/**
 * Registro de aplicação de insulina
 * Created by André on 26/01/2016.
 */
public class AplicacaoInsulina extends Registro {

    private double quantidade;
    private TipoInsulina tipo;

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

    @Override
    public boolean isValid() {
        return this.getQuantidade() >= 0.1 && this.getQuantidade() <= 999;
    }
}
