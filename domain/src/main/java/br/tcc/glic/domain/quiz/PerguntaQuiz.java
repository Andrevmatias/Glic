package br.tcc.glic.domain.quiz;

import java.util.List;

/**
 * Created by André on 27/01/2016.
 */
public class PerguntaQuiz {
    private int codigo;
    private String texto;
    private List<RespostaQuiz> respostas;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<RespostaQuiz> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaQuiz> respostas) {
        this.respostas = respostas;
    }
}
