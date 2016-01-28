package br.tcc.glic.domain.quiz;

/**
 * Created by André on 27/01/2016.
 */
public class RespostaQuiz {
    private int codigo;
    private String texto;
    private boolean correta;

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

    public boolean isCorreta() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }
}
