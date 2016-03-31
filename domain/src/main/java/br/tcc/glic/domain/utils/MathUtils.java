package br.tcc.glic.domain.utils;

import java.util.Collection;

/**
 * Classe de utilidades matemáticas
 * Created by André on 30/03/2016.
 */
public final class MathUtils {
    public static double calcularDesvioPadrao(Collection<Double> valores) {
        double media = calcularMedia(valores);
        double acumulador = 0l;

        for (double valor :
                valores) {
            acumulador += Math.pow(valor - media, 2);
        }

        return Math.sqrt(acumulador / (double)valores.size());
    }

    public static double calcularMedia(Collection<Double> valores) {
        double acumulador = 0l;
        for (double valor :
                valores) {
            acumulador += valor;
        }
        return acumulador / (double)valores.size();
    }

}
