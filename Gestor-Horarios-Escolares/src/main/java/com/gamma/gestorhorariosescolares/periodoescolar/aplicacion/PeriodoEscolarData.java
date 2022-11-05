package com.gamma.gestorhorariosescolares.periodoescolar.aplicacion;

import com.gamma.gestorhorariosescolares.periodoescolar.dominio.PeriodoEscolar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PeriodoEscolarData {
    private final Integer id;
    private final String clave;
    private final String nombre;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final Boolean estatus;

    public PeriodoEscolarData(Integer id, String clave, String nombre, Date fechaInicio, Date fechaFin, Boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.fechaFin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.estatus = estatus;
    }

    public static PeriodoEscolarData fromAggregate(PeriodoEscolar periodoEscolar) {
        return new PeriodoEscolarData(periodoEscolar.id(), periodoEscolar.clave(), periodoEscolar.nombre(),
                periodoEscolar.fechaInicio(), periodoEscolar.fechaFin(), periodoEscolar.estatus());
    }

    public Integer id() {
        return id;
    }

    public String clave() {
        return clave;
    }

    public String nombre() {
        return nombre;
    }

    public LocalDate fechaInicio() {
        return fechaInicio;
    }

    public String fechaInicioFormateada() {
        return fechaInicio.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate fechaFin() {
        return fechaFin;
    }

    public String fechaFinFormateada() {
        return fechaFin.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public Boolean estatus() {
        return estatus;
    }

}