package com.gamma.gestorhorariosescolares.planestudio.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudio;
import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudioRepositorio;

public class ActualizadorPlanEstudio implements ServicioActualizadorPlanEstudio {

    private final PlanEstudioRepositorio repositorio;

    public ActualizadorPlanEstudio(PlanEstudioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(PlanEstudio planEstudio) {
        //Validar datos aquí
        repositorio.actualizar(planEstudio);
    }

}