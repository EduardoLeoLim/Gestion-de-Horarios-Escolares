package com.gamma.gestorhorariosescolares.planestudio.aplicacion.eliminar;

import com.gamma.gestorhorariosescolares.planestudio.dominio.PlanEstudioRepositorio;

public class EliminadorPlanEstudio implements ServicioEliminadorPlanEstudio {

    private final PlanEstudioRepositorio repositorio;

    public EliminadorPlanEstudio(PlanEstudioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void eliminar(int idPlanEstudio) {
        repositorio.eliminar(idPlanEstudio);
    }

}