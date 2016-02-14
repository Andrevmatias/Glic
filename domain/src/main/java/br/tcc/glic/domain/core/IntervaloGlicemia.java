package br.tcc.glic.domain.core;

import br.tcc.glic.domain.enums.TipoGlicemia;

/**
 * Created by André on 14/02/2016.
 */
public class IntervaloGlicemia {
    private int limiteMinimo;
    private int limiteMaximo;

    public IntervaloGlicemia(int limiteMinimo, int limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
        this.limiteMinimo = limiteMinimo;
    }

    public static IntervaloGlicemia getByIdade(int idade, TipoGlicemia tipo){
        if(tipo == TipoGlicemia.PosPrandial)
            return getPosPrandialByIdade(idade);

        return getPrePrandialByIdade(idade);
    }

    private static IntervaloGlicemia getPrePrandialByIdade(int idade) {
        if(idade <= 6)
            return new IntervaloGlicemia(100, 180);
        if(idade <= 12)
            return new IntervaloGlicemia(90, 180);
        if(idade <= 19)
            return new IntervaloGlicemia(90, 130);

        return new IntervaloGlicemia(70, 130);
    }

    private static IntervaloGlicemia getPosPrandialByIdade(int idade) {
        if(idade <= 6)
            return new IntervaloGlicemia(110, 200);
        if(idade <= 12)
            return new IntervaloGlicemia(100, 180);
        if(idade <= 19)
            return new IntervaloGlicemia(90, 150);

        return new IntervaloGlicemia(70, 160);
    }

    public int getLimiteMaximo() {
        return limiteMaximo;
    }

    public int getLimiteMinimo() {
        return limiteMinimo;
    }
}
