package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.TipoExercicio;

/**
 * Created by André on 25/01/2016.
 */
public class PraticaExercicio extends Registro {
    private int codigo;
    private TipoExercicio tipo;
    private int duracao;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public TipoExercicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoExercicio tipo) {
        this.tipo = tipo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
