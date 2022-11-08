package com.gamma.gestorhorariosescolares.planestudio.aplicacion;

import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

public class PlanEstudioData {

    private final Integer id;
    private final String clave;
    private final String nombre;
    private final Boolean estatus;

    public PlanEstudioData(Integer id, String clave, String nombre, Boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.estatus = estatus;
    }

    public static PlanEstudioData fromAggregate(PlanEstudio planEstudio) {
        return new PlanEstudioData(planEstudio.id(), planEstudio.clave(), planEstudio.nombre(), planEstudio.estatus());
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

    public Boolean estatus() {
        return estatus;
    }
}
