package br.tcc.glic.domain.core;

/**
 * Created by André on 26/01/2016.
 */
public class TipoInsulina {
    private Long codigo;
    private String nome;
    private int inicio;
    private int pico;
    private int duracao;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getPico() {
        return pico;
    }

    public void setPico(int pico) {
        this.pico = pico;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
