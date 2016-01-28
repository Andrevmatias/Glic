package br.tcc.glic.domain.quiz;

import java.util.List;

import br.tcc.glic.domain.enums.Dificuldade;

/**
 * Created by André on 27/01/2016.
 */
public class Quiz {
    private int codigo;
    private String nome;
    private boolean liberado;
    private boolean concluido;
    private Dificuldade dificuldade;
    private List<PerguntaQuiz> perguntas;

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

    public boolean isLiberado() {
        return liberado;
    }

    public void setLiberado(boolean liberado) {
        this.liberado = liberado;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public List<PerguntaQuiz> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<PerguntaQuiz> perguntas) {
        this.perguntas = perguntas;
    }
}
