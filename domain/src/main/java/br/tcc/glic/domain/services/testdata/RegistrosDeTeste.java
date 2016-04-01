package br.tcc.glic.domain.services.testdata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import br.tcc.glic.data.entities.Registro;
import br.tcc.glic.data.entities.TipoRegistro;

/**
 * Registros para teste
 * Created by André on 21/02/2016.
 */
public final class RegistrosDeTeste {
    private static List<Registro> registrosTeste;

    static {
        registrosTeste = new ArrayList<>();

        Calendar hora = Calendar.getInstance();

        long id = Long.MAX_VALUE;

        Registro registro1 = new Registro();
        registro1.setId(id--);
        registro1.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 55);
        registro1.setHora(hora.getTime());
        registro1.setValor(113);

        hora = (Calendar) hora.clone();
        Registro registro2 = new Registro();
        registro2.setId(id--);
        registro2.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 04);
        registro2.setHora(hora.getTime());
        registro2.setValor(124);

        hora = (Calendar) hora.clone();
        Registro registro3 = new Registro();
        registro3.setId(id--);
        registro3.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 03);
        registro3.setHora(hora.getTime());
        registro3.setValor(125);

        hora = (Calendar) hora.clone();
        Registro registro4 = new Registro();
        registro4.setId(id--);
        registro4.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 21);
        registro4.setHora(hora.getTime());
        registro4.setValor(97);
/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro5 = new Registro();
        registro5.setId(id--);
        registro5.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 23);
        hora.set(Calendar.MINUTE, 34);
        registro5.setHora(hora.getTime());
        registro5.setValor(145);

        hora = (Calendar) hora.clone();
        Registro registro6 = new Registro();
        registro6.setId(id--);
        registro6.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 03);
        registro6.setHora(hora.getTime());
        registro6.setValor(235);

        hora = (Calendar) hora.clone();
        Registro registro7 = new Registro();
        registro7.setId(id--);
        registro7.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 17);
        registro7.setHora(hora.getTime());
        registro7.setValor(123);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro8 = new Registro();
        registro8.setId(id--);
        registro8.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 2);
        registro8.setHora(hora.getTime());
        registro8.setValor(130);

        hora = (Calendar) hora.clone();
        Registro registro9 = new Registro();
        registro9.setId(id--);
        registro9.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 2);
        registro9.setHora(hora.getTime());
        registro9.setValor(115);

        hora = (Calendar) hora.clone();
        Registro registro10 = new Registro();
        registro10.setId(id--);
        registro10.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 17);
        registro10.setHora(hora.getTime());
        registro10.setValor(113);
/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro11 = new Registro();
        registro11.setId(id--);
        registro11.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 23);
        hora.set(Calendar.MINUTE, 46);
        registro11.setHora(hora.getTime());
        registro11.setValor(74);


/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        hora = (Calendar) hora.clone();
        Registro registro12 = new Registro();
        registro12.setId(id--);
        registro12.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 31);
        registro12.setHora(hora.getTime());
        registro12.setValor(92);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro13 = new Registro();
        registro13.setId(id--);
        registro13.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 23);
        registro13.setHora(hora.getTime());
        registro13.setValor(148);

        hora = (Calendar) hora.clone();
        Registro registro14 = new Registro();
        registro14.setId(id--);
        registro14.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 14);
        hora.set(Calendar.MINUTE, 16);
        registro14.setHora(hora.getTime());
        registro14.setValor(68);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro15 = new Registro();
        registro15.setId(id--);
        registro15.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 20);
        hora.set(Calendar.MINUTE, 16);
        registro15.setHora(hora.getTime());
        registro15.setValor(111);

        hora = (Calendar) hora.clone();
        Registro registro16 = new Registro();
        registro16.setId(id--);
        registro16.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 12);
        registro16.setHora(hora.getTime());
        registro16.setValor(164);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro17 = new Registro();
        registro17.setId(id--);
        registro17.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 02);
        registro17.setHora(hora.getTime());
        registro17.setValor(109);

        hora = (Calendar) hora.clone();
        Registro registro18 = new Registro();
        registro18.setId(id--);
        registro18.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 32);
        registro18.setHora(hora.getTime());
        registro18.setValor(148);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro19 = new Registro();
        registro19.setId(id--);
        registro19.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 22);
        hora.set(Calendar.MINUTE, 7);
        registro19.setHora(hora.getTime());
        registro19.setValor(116);

        hora = (Calendar) hora.clone();
        Registro registro20 = new Registro();
        registro20.setId(id--);
        registro20.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 29);
        registro20.setHora(hora.getTime());
        registro20.setValor(280);

        hora = (Calendar) hora.clone();
        Registro registro21 = new Registro();
        registro21.setId(id--);
        registro21.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 4);
        registro21.setHora(hora.getTime());
        registro21.setValor(166);

        hora = (Calendar) hora.clone();
        Registro registro22 = new Registro();
        registro22.setId(id--);
        registro22.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 23);
        registro22.setHora(hora.getTime());
        registro22.setValor(89);

        hora = (Calendar) hora.clone();
        Registro registro23 = new Registro();
        registro23.setId(id--);
        registro23.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 1);
        hora.set(Calendar.MINUTE, 25);
        registro23.setHora(hora.getTime());
        registro23.setValor(125);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        hora = (Calendar) hora.clone();
        Registro registro24 = new Registro();
        registro24.setId(id--);
        registro24.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 29);
        registro24.setHora(hora.getTime());
        registro24.setValor(143);

        hora = (Calendar) hora.clone();
        Registro registro25 = new Registro();
        registro25.setId(id--);
        registro25.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 03);
        registro25.setHora(hora.getTime());
        registro25.setValor(140);

        hora = (Calendar) hora.clone();
        Registro registro26 = new Registro();
        registro26.setId(id--);
        registro26.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 17);
        registro26.setHora(hora.getTime());
        registro26.setValor(87);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro27 = new Registro();
        registro27.setId(id--);
        registro27.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 11);
        registro27.setHora(hora.getTime());
        registro27.setValor(156);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro28 = new Registro();
        registro28.setId(id--);
        registro28.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 22);
        hora.set(Calendar.MINUTE, 54);
        registro28.setHora(hora.getTime());
        registro28.setValor(69);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro29 = new Registro();
        registro29.setId(id--);
        registro29.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 5);
        registro29.setHora(hora.getTime());
        registro29.setValor(240);

        hora = (Calendar) hora.clone();
        Registro registro30 = new Registro();
        registro30.setId(id--);
        registro30.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 4);
        registro30.setHora(hora.getTime());
        registro30.setValor(134);

        hora = (Calendar) hora.clone();
        Registro registro31 = new Registro();
        registro31.setId(id--);
        registro31.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 23);
        registro31.setHora(hora.getTime());
        registro31.setValor(59);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro32 = new Registro();
        registro32.setId(id--);
        registro32.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 22);
        hora.set(Calendar.MINUTE, 27);
        registro32.setHora(hora.getTime());
        registro32.setValor(241);

        hora = (Calendar) hora.clone();
        Registro registro33 = new Registro();
        registro33.setId(id--);
        registro33.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 4);
        registro33.setHora(hora.getTime());
        registro33.setValor(130);

        hora = (Calendar) hora.clone();
        Registro registro34 = new Registro();
        registro34.setId(id--);
        registro34.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 23);
        registro34.setHora(hora.getTime());
        registro34.setValor(64);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro35 = new Registro();
        registro35.setId(id--);
        registro35.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 22);
        hora.set(Calendar.MINUTE, 38);
        registro35.setHora(hora.getTime());
        registro35.setValor(112);

        hora = (Calendar) hora.clone();
        Registro registro36 = new Registro();
        registro36.setId(id--);
        registro36.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 11);
        registro36.setHora(hora.getTime());
        registro36.setValor(73);

        hora = (Calendar) hora.clone();
        Registro registro37 = new Registro();
        registro37.setId(id--);
        registro37.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 4);
        registro37.setHora(hora.getTime());
        registro37.setValor(185);

        hora = (Calendar) hora.clone();
        Registro registro38 = new Registro();
        registro38.setId(id--);
        registro38.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 0);
        hora.set(Calendar.MINUTE, 18);
        registro38.setHora(hora.getTime());
        registro38.setValor(145);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro39 = new Registro();
        registro39.setId(id--);
        registro39.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 3);
        registro39.setHora(hora.getTime());
        registro39.setValor(155);


        hora = (Calendar) hora.clone();
        Registro registro40 = new Registro();
        registro40.setId(id--);
        registro40.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 19);
        registro40.setHora(hora.getTime());
        registro40.setValor(93);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro41 = new Registro();
        registro41.setTipo(TipoRegistro.Glicemia);
        registro41.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 22);
        hora.set(Calendar.MINUTE, 46);
        registro41.setHora(hora.getTime());
        registro41.setValor(225);

        hora = (Calendar) hora.clone();
        Registro registro42 = new Registro();
        registro42.setTipo(TipoRegistro.Glicemia);
        registro42.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 39);
        registro42.setHora(hora.getTime());
        registro42.setValor(148);

        hora = (Calendar) hora.clone();
        Registro registro43 = new Registro();
        registro43.setTipo(TipoRegistro.Glicemia);
        registro43.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 2);
        registro43.setHora(hora.getTime());
        registro43.setValor(126);

        hora = (Calendar) hora.clone();
        Registro registro44 = new Registro();
        registro44.setTipo(TipoRegistro.Glicemia);
        registro44.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 20);
        registro44.setHora(hora.getTime());
        registro44.setValor(125);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro45 = new Registro();
        registro45.setTipo(TipoRegistro.Glicemia);
        registro45.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 23);
        hora.set(Calendar.MINUTE, 17);
        registro45.setHora(hora.getTime());
        registro45.setValor(205);

        hora = (Calendar) hora.clone();
        Registro registro46 = new Registro();
        registro46.setTipo(TipoRegistro.Glicemia);
        registro46.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 34);
        registro46.setHora(hora.getTime());
        registro46.setValor(270);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro47 = new Registro();
        registro47.setTipo(TipoRegistro.Glicemia);
        registro47.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 23);
        hora.set(Calendar.MINUTE, 36);
        registro47.setHora(hora.getTime());
        registro47.setValor(215);

        hora = (Calendar) hora.clone();
        Registro registro48 = new Registro();
        registro48.setTipo(TipoRegistro.Glicemia);
        registro48.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 32);
        registro48.setHora(hora.getTime());
        registro48.setValor(221);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro49 = new Registro();
        registro49.setId(id--);
        registro49.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 20);
        hora.set(Calendar.MINUTE, 30);
        registro49.setHora(hora.getTime());
        registro49.setValor(150);

        hora = (Calendar) hora.clone();
        Registro registro51 = new Registro();
        registro51.setId(id--);
        registro51.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 8);
        registro51.setHora(hora.getTime());
        registro51.setValor(137);

        hora = (Calendar) hora.clone();
        Registro registro52 = new Registro();
        registro52.setId(id--);
        registro52.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 23);
        registro52.setHora(hora.getTime());
        registro52.setValor(63);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro53 = new Registro();
        registro53.setId(id--);
        registro53.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 23);
        hora.set(Calendar.MINUTE, 46);
        registro53.setHora(hora.getTime());
        registro53.setValor(72);

        hora = (Calendar) hora.clone();
        Registro registro54 = new Registro();
        registro54.setId(id--);
        registro54.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 39);
        registro54.setHora(hora.getTime());
        registro54.setValor(217);

        hora = (Calendar) hora.clone();
        Registro registro55 = new Registro();
        registro55.setId(id--);
        registro55.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 4);
        registro55.setHora(hora.getTime());
        registro55.setValor(140);

        hora = (Calendar) hora.clone();
        Registro registro50 = new Registro();
        registro50.setId(id--);
        registro50.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 22);
        registro50.setHora(hora.getTime());
        registro50.setValor(96);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro56 = new Registro();
        registro56.setId(id--);
        registro56.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 22);
        hora.set(Calendar.MINUTE, 21);
        registro56.setHora(hora.getTime());
        registro56.setValor(111);

        hora = (Calendar) hora.clone();
        Registro registro57 = new Registro();
        registro57.setId(id--);
        registro57.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 12);
        registro57.setHora(hora.getTime());
        registro57.setValor(148);

        hora = (Calendar) hora.clone();
        Registro registro58 = new Registro();
        registro58.setId(id--);
        registro58.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 2);
        registro58.setHora(hora.getTime());
        registro58.setValor(143);

        Registro registro59 = new Registro();
        registro59.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 0);
        hora.set(Calendar.MINUTE, 5);
        registro59.setHora(hora.getTime());
        registro59.setValor(82);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro60 = new Registro();
        registro60.setId(id--);
        registro60.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 6);
        registro60.setHora(hora.getTime());
        registro60.setValor(109);

        hora = (Calendar) hora.clone();
        Registro registro61 = new Registro();
        registro61.setId(id--);
        registro61.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 4);
        registro61.setHora(hora.getTime());
        registro61.setValor(163);

        hora = (Calendar) hora.clone();
        Registro registro62 = new Registro();
        registro62.setId(id--);
        registro62.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 24);
        registro62.setHora(hora.getTime());
        registro62.setValor(74);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro63 = new Registro();
        registro63.setId(id--);
        registro63.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 15);
        registro63.setHora(hora.getTime());
        registro63.setValor(145);

        hora = (Calendar) hora.clone();
        Registro registro64 = new Registro();
        registro64.setId(id--);
        registro64.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 6);
        registro64.setHora(hora.getTime());
        registro64.setValor(106);

        hora = (Calendar) hora.clone();
        Registro registro65 = new Registro();
        registro65.setId(id--);
        registro65.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 20);
        registro65.setHora(hora.getTime());
        registro65.setValor(51);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro66 = new Registro();
        registro66.setId(id--);
        registro66.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 21);
        registro66.setHora(hora.getTime());
        registro66.setValor(238);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro67 = new Registro();
        registro67.setId(id--);
        registro67.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 23);
        hora.set(Calendar.MINUTE, 48);
        registro67.setHora(hora.getTime());
        registro67.setValor(166);

        hora = (Calendar) hora.clone();
        Registro registro68 = new Registro();
        registro68.setId(id--);
        registro68.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 18);
        hora.set(Calendar.MINUTE, 58);
        registro68.setHora(hora.getTime());
        registro68.setValor(116);

        hora = (Calendar) hora.clone();
        Registro registro69 = new Registro();
        registro69.setId(id--);
        registro69.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 9);
        hora.set(Calendar.MINUTE, 51);
        registro69.setHora(hora.getTime());
        registro69.setValor(72);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro70 = new Registro();
        registro70.setId(id--);
        registro70.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 5);
        registro70.setHora(hora.getTime());
        registro70.setValor(154);

        hora = (Calendar) hora.clone();
        Registro registro71 = new Registro();
        registro71.setId(id--);
        registro71.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 21);
        registro71.setHora(hora.getTime());
        registro71.setValor(76);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro72 = new Registro();
        registro72.setId(id--);
        registro72.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 0);
        registro72.setHora(hora.getTime());
        registro72.setValor(156);

        hora = (Calendar) hora.clone();
        Registro registro73 = new Registro();
        registro73.setId(id--);
        registro73.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 4);
        registro73.setHora(hora.getTime());
        registro73.setValor(148);

        hora = (Calendar) hora.clone();
        Registro registro74 = new Registro();
        registro74.setId(id--);
        registro74.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 20);
        registro74.setHora(hora.getTime());
        registro74.setValor(136);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro75 = new Registro();
        registro75.setId(id--);
        registro75.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 12);
        hora.set(Calendar.MINUTE, 3);
        registro75.setHora(hora.getTime());
        registro75.setValor(175);

        hora = (Calendar) hora.clone();
        Registro registro76 = new Registro();
        registro76.setId(id--);
        registro76.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 20);
        registro76.setHora(hora.getTime());
        registro76.setValor(234);

/*------------------------------------------------------*/

        hora = (Calendar) hora.clone();
        hora.add(Calendar.DATE, -1);

        Registro registro77 = new Registro();
        registro77.setTipo(TipoRegistro.Glicemia);
        registro77.setId(id--);
        hora.set(Calendar.HOUR_OF_DAY, 23);
        hora.set(Calendar.MINUTE, 53);
        registro77.setHora(hora.getTime());
        registro77.setValor(63);

        hora = (Calendar) hora.clone();
        Registro registro78 = new Registro();
        registro78.setId(id--);
        registro78.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 19);
        hora.set(Calendar.MINUTE, 4);
        registro78.setHora(hora.getTime());
        registro78.setValor(211);

        hora = (Calendar) hora.clone();
        Registro registro79 = new Registro();
        registro79.setId(id--);
        registro79.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 20);
        registro79.setHora(hora.getTime());
        registro79.setValor(234);

        hora = (Calendar) hora.clone();
        Registro registro80 = new Registro();
        registro80.setId(id--);
        registro80.setTipo(TipoRegistro.Glicemia);
        hora.set(Calendar.HOUR_OF_DAY, 7);
        hora.set(Calendar.MINUTE, 19);
        registro80.setHora(hora.getTime());
        registro80.setValor(184);

/*------------------------------------------------------*/

        Collections.addAll(registrosTeste,
                registro80, registro1,  registro2,  registro3,  registro4,  registro5,  registro6,  registro7,  registro8, registro9,
                registro10, registro11, registro12, registro13, registro14, registro15, registro16, registro17, registro18, registro19,
                registro20, registro21, registro22, registro23, registro24, registro25, registro26, registro27, registro28, registro29,
                registro30, registro31, registro32, registro33, registro34, registro35, registro36, registro37, registro38, registro39,
                registro40, registro41, registro42, registro43, registro44, registro45, registro46, registro47, registro48, registro49,
                registro50, registro51, registro52, registro53, registro54, registro55, registro56, registro57, registro58, registro59,
                registro60, registro61, registro62, registro63, registro64, registro65, registro66, registro67, registro68, registro69,
                registro70, registro71, registro72, registro73, registro74, registro75, registro76, registro77, registro78, registro79);
    }

    public static List<Registro> getAll() {
        return registrosTeste;
    }
}
