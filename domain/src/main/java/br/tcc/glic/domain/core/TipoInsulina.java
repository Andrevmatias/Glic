package br.tcc.glic.domain.core;

/**
 * Created by André on 26/01/2016.
 */
public class TipoInsulina {
    private int codigo;
    private String nome;
    private double inicio;
    private double pico;
    private double duracao;

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

    public double getInicio() {
        return inicio;
    }

    public void setInicio(double inicio) {
        this.inicio = inicio;
    }

    public double getPico() {
        return pico;
    }

    public void setPico(double pico) {
        this.pico = pico;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }
}
