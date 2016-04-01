package br.tcc.glic.domain.services;

import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.tcc.glic.data.entities.Registro;

/**
 * Classe para análises sobre Registros
 * Created by André on 09/03/2016.
 */
public class AnalizadorRegistros {

    private static SimpleDateFormat dateFormat;
    private int mediaExamesDia;
    private List<Instance> amostra;

    private AnalizadorRegistros(){
        if(dateFormat == null)
            dateFormat = new SimpleDateFormat("ddMMyy");
    }

    AnalizadorRegistros(List<Registro> registros) {
        this();

        amostra = new ArrayList<>(registros.size());
        transformarDados(registros);
    }

    public static AnalizadorRegistros getInstance(List<? extends br.tcc.glic.domain.core.Registro> registros){
        AnalizadorRegistros analizador = new AnalizadorRegistros();
        analizador.amostra = new ArrayList<>(registros.size());
        analizador.transformarDadosDomain(registros);

        return  analizador;
    }


    private void transformarDados(List<Registro> registros) {
        Collections.sort(registros);

        List<Integer> examesDia = new ArrayList<>(registros.size());

        String diaAtual = "";
        int contagemDia = 0;
        for (Registro registro : registros) {
            String diaString = dateFormat.format(registro.getHora());
            if(contagemDia != 0 && !diaAtual.equals(diaString)){
                examesDia.add(contagemDia);
                contagemDia = 0;
                diaAtual = diaString;
            }

            contagemDia++;

            adicionarAmostra(registro.getHora());
        }

        mediaExamesDia = calcularMedia(examesDia);
    }

    private void transformarDadosDomain(List<? extends br.tcc.glic.domain.core.Registro> registros) {
        Collections.sort(registros);

        List<Integer> examesDia = new ArrayList<>(registros.size());

        String diaAtual = "";
        int contagemDia = 0;
        for (br.tcc.glic.domain.core.Registro registro : registros) {
            String diaString = dateFormat.format(registro.getHora());
            if(contagemDia != 0 && !diaAtual.equals(diaString)){
                examesDia.add(contagemDia);
                contagemDia = 0;
                diaAtual = diaString;
            }

            contagemDia++;

            adicionarAmostra(registro.getHora());
        }

        if(contagemDia != 0)
            examesDia.add(contagemDia);

        mediaExamesDia = calcularMedia(examesDia);
    }

    private int calcularMedia(List<Integer> valores) {
        double acumulador =  0;
        for (int quantiadeDia : valores)
            acumulador += (double)quantiadeDia;

        return (int) Math.round(acumulador / (double)valores.size());
    }

    private int calcularMedia(Dataset dataset) {
        double acumulador =  0;
        for (Instance instance : dataset)
            acumulador += instance.value(0);

        return (int) Math.round(acumulador / (double)dataset.size());
    }

    private void adicionarAmostra(Date horaDate) {
        Calendar horaCalendar = Calendar.getInstance();
        horaCalendar.setTime(horaDate);

        Instance instance = new DenseInstance(new double[] { getComparavelHoraMinuto(horaCalendar) });

        amostra.add(instance);
    }

    private double getComparavelHoraMinuto(Calendar horaCalendar) {
        return horaCalendar.get(Calendar.HOUR_OF_DAY) * 60d + horaCalendar.get(Calendar.MINUTE);
    }

    public List<Calendar> identificarHorariosDeRegistroComuns() {
        List<Calendar> horariosComuns = new ArrayList<>(mediaExamesDia);

        Dataset dataset = new DefaultDataset(amostra);
        KMeans kMeans = new KMeans(mediaExamesDia);
        Dataset[] clusters = kMeans.cluster(dataset);

        Calendar agora = Calendar.getInstance();
        for (Dataset cluster : clusters) {
            int media = calcularMedia(cluster);
            Calendar lembrete = Calendar.getInstance();
            setHoraMinuto(lembrete, media);
            while(agora.compareTo(lembrete) > 0)
                lembrete.add(Calendar.DATE, 1);

            if(naoContem(horariosComuns, lembrete))
                horariosComuns.add(lembrete);
        }

        return  horariosComuns;
    }

    private boolean naoContem(List<Calendar> horariosComuns, Calendar lembrete) {
        for (Calendar horarioComum : horariosComuns) {
            if(horarioComum.equals(lembrete))
                return false;
        }
        return true;
    }

    private void setHoraMinuto(Calendar calendar, int minutosDia) {
        int minutos = minutosDia % 60;
        minutosDia -= minutos;
        int horas = minutosDia / 60;

        if(minutos < 15)
            minutos = 0;
        else if(minutos < 45)
            minutos = 30;
        else {
            minutos = 0;
            horas = (horas + 1) % 24;
        }

        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minutos);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
