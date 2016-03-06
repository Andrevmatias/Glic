package br.tcc.glic.domain.core;

/**
 * Registro de quantidade de carboidratos ingeridos
 * Created by André on 26/01/2016.
 */
public class CarboidratoIngerido extends Registro{
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
