package br.tcc.glic.domain.core;

/**
 * Created by André on 27/01/2016.
 */
public class Funcao {
    private int codigo;
    private String nome;
    private boolean liberada;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isLiberada() {
        return liberada;
    }

    public void setLiberada(boolean liberada) {
        this.liberada = liberada;
    }
}
