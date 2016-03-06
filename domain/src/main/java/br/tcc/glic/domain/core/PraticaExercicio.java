package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.TipoExercicio;

/**
 * Registro de prática de exercício
 * Created by André on 25/01/2016.
 */
public class PraticaExercicio extends Registro {

    private TipoExercicio tipo;
    private int duracao;

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
