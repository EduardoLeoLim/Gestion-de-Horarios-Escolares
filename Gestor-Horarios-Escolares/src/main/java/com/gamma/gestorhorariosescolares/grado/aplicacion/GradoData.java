package com.gamma.gestorhorariosescolares.grado.aplicacion;

import com.gamma.gestorhorariosescolares.grado.dominio.Grado;

public class GradoData {

    private final Integer id;
    private final String clave;
    private final String nombre;
    private final Integer idPlanEstudio;
    private final Boolean estatus;

    public GradoData(Integer id, String clave, String nombre, Integer idPlanEstudio, Boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.idPlanEstudio = idPlanEstudio;
        this.estatus = estatus;
    }

    public static GradoData fromAggregate(Grado grado) {
        return new GradoData(grado.id(), grado.clave(), grado.nombre(), grado.idPlanEstudio(), grado.estatus());
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

    public Integer idPlanEstudio() {
        return idPlanEstudio;
    }

    public Boolean estatus() {
        return estatus;
    }
}
