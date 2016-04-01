package br.tcc.glic.data.entities;

import com.orm.dsl.Table;

import java.util.Date;

/**
 * Created by André on 19/02/2016.
 */
@Table
public class Registro
        implements Entity, Comparable<Registro> {

    private Long id;
    private Date hora;
    private TipoRegistro tipo;
    private double valor;
    private TipoInsulina tipoInsulina;


    public Registro(TipoRegistro tipo){
        this();
        this.tipo = tipo;
    }

    public Registro(){
        tipoInsulina = new TipoInsulina();
    }

    public TipoInsulina getTipoInsulina() {
        return tipoInsulina;
    }

    public void setTipoInsulina(TipoInsulina tipoInsulina) {
        this.tipoInsulina = tipoInsulina;
    }

    public TipoRegistro getTipo() {
        return tipo;
    }

    public void setTipo(TipoRegistro tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Registro another) {
        if(another == null || another.getHora() == null)
            return 1;

        if(this.getHora() == null)
            return -1;

        long result = another.getHora().getTime() - this.getHora().getTime();
        if(result == 0) return 0;
        return result > 1 ? 1 : -1;
    }
}
