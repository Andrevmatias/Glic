package br.tcc.glic.domain.core;

/**
 * Created by André on 26/01/2016.
 */
public class CarboidratoIngerido extends Registro{
    private Long codigo;
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
