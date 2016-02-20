package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.TipoCarboidrato;

/**
 * Created by André on 26/01/2016.
 */
public class CarboidratoIngerido extends Registro{
    private int codigo;
    private int quantidade;
    private TipoCarboidrato tipo;

    public TipoCarboidrato getTipo() {
        return tipo;
    }

    public void setTipo(TipoCarboidrato tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
