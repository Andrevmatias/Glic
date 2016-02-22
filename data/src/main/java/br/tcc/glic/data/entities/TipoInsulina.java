package br.tcc.glic.data.entities;

import com.orm.dsl.Table;

/**
 * Created by André on 19/02/2016.
 */
@Table
public class TipoInsulina
    implements Entity
{
    private Long id;
    private String nome;
    private int inicio;
    private int pico;
    private int duracao;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
