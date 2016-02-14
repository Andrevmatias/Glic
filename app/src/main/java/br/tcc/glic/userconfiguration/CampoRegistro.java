package br.tcc.glic.userconfiguration;

import br.tcc.glic.domain.enums.TipoTerapia;

/**
 * Created by André on 14/02/2016.
 */
public enum CampoRegistro {
    Glicemia,
    Carboidratos,
    HbA1c,
    InsulinaAplicada;

    public static CampoRegistro[] getByTipoTerapia(TipoTerapia tipoTerapia){
        switch (tipoTerapia){
            case Convencional:
                return new CampoRegistro[]{ Glicemia, InsulinaAplicada };
            case Intensiva:
                return  new CampoRegistro[]{ Glicemia, Carboidratos, InsulinaAplicada };
            default:
                return new CampoRegistro[] { Glicemia };
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case InsulinaAplicada:
                return "Insulina Aplicada";
            default:
                return super.toString();
        }
    }
}
