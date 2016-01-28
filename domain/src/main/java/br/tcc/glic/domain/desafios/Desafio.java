package br.tcc.glic.domain.desafios;

import java.util.Collection;

import br.tcc.glic.domain.core.Funcao;
import br.tcc.glic.domain.enums.Dificuldade;
import br.tcc.glic.domain.quiz.Quiz;

/**
 * Created by André on 27/01/2016.
 */
public class Desafio {
    private int codigo;
    private String nome;
    private byte[] figura;
    private int progresso;
    private int total;
    private boolean completo;
    private boolean liberado;
    private Dificuldade dificuldade;
    private Collection<Desafio> desafiosLiberadosAoConcluir;
    private Collection<Funcao> funcoesLiberadasAoConcluir;
    private Collection<Quiz> quizzesLiberadosAoConcluir;

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

    public byte[] getFigura() {
        return figura;
    }

    public void setFigura(byte[] figura) {
        this.figura = figura;
    }

    public int getProgresso() {
        return progresso;
    }

    public void setProgresso(int progresso) {
        this.progresso = progresso;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public boolean isLiberado() {
        return liberado;
    }

    public void setLiberado(boolean liberado) {
        this.liberado = liberado;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Collection<Desafio> getDesafiosLiberadosAoConcluir() {
        return desafiosLiberadosAoConcluir;
    }

    public void setDesafiosLiberadosAoConcluir(Collection<Desafio> desafiosLiberadosAoConcluir) {
        this.desafiosLiberadosAoConcluir = desafiosLiberadosAoConcluir;
    }

    public Collection<Funcao> getFuncoesLiberadasAoConcluir() {
        return funcoesLiberadasAoConcluir;
    }

    public void setFuncoesLiberadasAoConcluir(Collection<Funcao> funcoesLiberadasAoConcluir) {
        this.funcoesLiberadasAoConcluir = funcoesLiberadasAoConcluir;
    }

    public Collection<Quiz> getQuizzesLiberadosAoConcluir() {
        return quizzesLiberadosAoConcluir;
    }

    public void setQuizzesLiberadosAoConcluir(Collection<Quiz> quizzesLiberadosAoConcluir) {
        this.quizzesLiberadosAoConcluir = quizzesLiberadosAoConcluir;
    }
}
