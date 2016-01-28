package br.tcc.glic.domain.core;

import java.util.Collection;
import java.util.Date;

import br.tcc.glic.domain.enums.TipoTerapia;

public class Usuario {
    private String codigo;
    private Date nascimento;
    private TipoTerapia tipoTerapia;
    private int pontuacao;
    private Responsavel responsavel;
    private Collection<Indicador> indicadores;
    private Collection<Glicemia> glicemias;
    private Collection<PraticaExercicio> praticasExercicio;
    private Collection<CarboidratoIngerido> carboidratosIngerido;
    private Collection<AplicacaoInsulina> aplicacoesInsulina;
    private Collection<HemoglobinaGlicada> hba1cs;


    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public TipoTerapia getTipoTerapia() {
        return tipoTerapia;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getCodigo() {
        return codigo;
    }

    public Collection<Indicador> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(Collection<Indicador> indicadores) {
        this.indicadores = indicadores;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Collection<Glicemia> getGlicemias() {
        return glicemias;
    }

    public void setGlicemias(Collection<Glicemia> glicemias) {
        this.glicemias = glicemias;
    }

    public Collection<PraticaExercicio> getPraticasExercicio() {
        return praticasExercicio;
    }

    public void setPraticasExercicio(Collection<PraticaExercicio> praticasExercicio) {
        this.praticasExercicio = praticasExercicio;
    }

    public Collection<CarboidratoIngerido> getCarboidratosIngerido() {
        return carboidratosIngerido;
    }

    public void setCarboidratosIngerido(Collection<CarboidratoIngerido> carboidratosIngerido) {
        this.carboidratosIngerido = carboidratosIngerido;
    }

    public Collection<AplicacaoInsulina> getAplicacoesInsulina() {
        return aplicacoesInsulina;
    }

    public void setAplicacoesInsulina(Collection<AplicacaoInsulina> aplicacoesInsulina) {
        this.aplicacoesInsulina = aplicacoesInsulina;
    }

    public Collection<HemoglobinaGlicada> getHba1cs() {
        return hba1cs;
    }

    public void setHba1cs(Collection<HemoglobinaGlicada> hba1cs) {
        this.hba1cs = hba1cs;
    }

    public void setTipoTerapia(TipoTerapia tipoTerapia) {
        this.tipoTerapia = tipoTerapia;
    }
}
