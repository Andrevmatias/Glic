package br.tcc.glic.domain.personagem;

import br.tcc.glic.domain.enums.EstadoPersonagem;

/**
 * Created by André on 27/01/2016.
 */
public class Personagem {
    private String nome;
    private EstadoPersonagem estado;
    private String[] causasEstado;
    private int nivel;
    private TipoPersonagem tipo;

    public EstadoPersonagem getEstado() {
        return estado;
    }

    public void setEstado(EstadoPersonagem estado) {
        this.estado = estado;
    }

    public String[] getCausasEstado() {
        return causasEstado;
    }

    public void setCausasEstado(String[] causasEstado) {
        this.causasEstado = causasEstado;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public TipoPersonagem getTipo() {
        return tipo;
    }

    public void setTipo(TipoPersonagem tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
