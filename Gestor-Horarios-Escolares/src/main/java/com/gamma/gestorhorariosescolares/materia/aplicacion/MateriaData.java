package com.gamma.gestorhorariosescolares.materia.aplicacion;

import com.gamma.gestorhorariosescolares.grado.aplicacion.GradoData;
import com.gamma.gestorhorariosescolares.grado.dominio.Grado;
import com.gamma.gestorhorariosescolares.materia.dominio.Materia;
import com.gamma.gestorhorariosescolares.planestudio.aplicacion.PlanEstudioData;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;

public class MateriaData {
    private final Integer id;
    private final String clave;
    private final String nombre;
    private final Integer horasPracticas;
    private final Integer horasTeoricas;
    private final PlanEstudioData planEstudio;
    private final GradoData grado;
    private final Boolean estatus;

    public MateriaData(Integer id, String clave, String nombre, Integer horasPracticas, Integer horasTeoricas, PlanEstudioData planEstudio, GradoData grado, Boolean estatus) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.horasPracticas = horasPracticas;
        this.horasTeoricas = horasTeoricas;
        this.planEstudio = planEstudio;
        this.grado = grado;
        this.estatus = estatus;
    }

    public static MateriaData fromAggregate(Materia materia, PlanEstudio planEstudio, Grado grado) {
        return new MateriaData(materia.id(), materia.clave(), materia.nombre(), materia.horasPracticas(),
                materia.horasTeoricas(), PlanEstudioData.fromAggregate(planEstudio), GradoData.fromAggregate(grado),
                materia.estatus());
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

    public Integer horasPracticas() {
        return horasPracticas;
    }

    public Integer horasTeoricas() {
        return horasTeoricas;
    }

    public PlanEstudioData planEstudio() {
        return planEstudio;
    }

    public GradoData grado() {
        return grado;
    }

    public Boolean estatus() {
        return estatus;
    }
}
