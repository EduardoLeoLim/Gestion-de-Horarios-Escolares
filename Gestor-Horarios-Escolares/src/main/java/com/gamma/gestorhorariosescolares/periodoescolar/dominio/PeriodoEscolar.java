package com.gamma.gestorhorariosescolares.periodoescolar.dominio;

import java.util.Date;

public class PeriodoEscolar {
    private final int id;
    private final String clave;
    private final String nombre;
    private final Date fechaInicio;
    private final Date fechaFin;
    private boolean estatus;

    public PeriodoEscolar(int id, String clave, String nombre, Date fechaInicio, Date fechaFin, boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estatus = estatus;
    }

    public PeriodoEscolar( String clave, String nombre, Date fechaInicio, Date fechaFin) {
        this.id = 0;
        this.estatus = true;
        this.clave = clave;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int id() {
        return id;
    }

    public String clave() {
        return clave;
    }

    public String nombre() {
        return nombre;
    }

    public Date fechaInicio() {
        return fechaInicio;
    }

    public Date fechaFin() {
        return fechaFin;
    }

    public void habilitar() {
        estatus = true;
    }

    public void deshabilitar() {
        estatus = false;
    }

    public boolean estatus() {
        return estatus;
    }

}
